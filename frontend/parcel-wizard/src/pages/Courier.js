import React, { useState } from 'react';
import axios from 'axios';
import '../styles/styles.css';

const Courier = () => {
  const [trackingResult, setTrackingResult] = useState(null);

  const [inputs, setInputs] = useState({
    courierCompany: 'KOREA_POST',
    trackingNo: '',
  });

  const { courierCompany, trackingNo } = inputs;

  const onChangeValue = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const CourierTrackingDetail = () => {
    return (
      <div className="tracking-result">
        <div>
          <h2>기본정보</h2>
        </div>
        <table>
          <thead>
            <tr>
              <th>운송장번호</th>
              <th>보내는 분</th>
              <th>받는 분</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{trackingResult.trackingNo}</td>
              <td>{trackingResult.senderName}</td>
              <td>{trackingResult.recipientName}</td>
            </tr>
          </tbody>
        </table>
        <div>
          <h2>진행상황</h2>
        </div>
        <table>
          <thead>
            <tr>
              <th>처리일시</th>
              <th>위치</th>
              <th>상태</th>
            </tr>
          </thead>
          <tbody>
            {trackingResult.shipmentProgressTrackingResources.map(
              (shipmentProgress) => {
                return (
                  <tr key={shipmentProgress.processingDateTime}>
                    <td>{shipmentProgress.processingDateTime}</td>
                    <td>{shipmentProgress.location}</td>
                    <td>{shipmentProgress.detailStatus}</td>
                  </tr>
                );
              }
            )}
          </tbody>
        </table>
      </div>
    );
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();

    const params = {
      trackingNo: trackingNo,
      courierCompany: courierCompany,
    };

    axios
      .get('http://localhost:8080/api/v1/shipments/tracking', { params })
      .then((resposne) => {
        setTrackingResult(resposne.data);
      })
      .catch((e) => {
        setTrackingResult(null);
        alert(e.response.data.reason);
      });
  };

  return (
    <div className="courier-print">
      <form onSubmit={handleFormSubmit}>
        <label>
          <select
            id="courier-company"
            name="courierCompany"
            onChange={onChangeValue}
          >
            <option value="KOREA_POST">우체국택배</option>
            <option value="CJ_LOGISTICS">CJ대한통운</option>
            <option value="WINION_LOGIS">위니온로지스</option>
          </select>
        </label>
        <label>
          <input
            type="text"
            id="tracking-no"
            name="trackingNo"
            onChange={onChangeValue}
            placeholder="운송장 번호를 입력해주세요."
          />
        </label>
        <button type="submit" id="search-button">
          조회
        </button>
      </form>
      {trackingResult && <CourierTrackingDetail />}
    </div>
  );
};

export default Courier;
