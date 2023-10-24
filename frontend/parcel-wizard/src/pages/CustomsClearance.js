import React, { useState } from 'react';
import axios from 'axios';
import '../styles/styles.css';

const CustomsClearance = () => {
  const [inputs, setInputs] = useState({
    selectedYear: new Date().getFullYear(),
    houseBlNo: '',
  });

  const { selectedYear, houseBlNo } = inputs;
  const [customsClearanceTrackingResult, setCustomsClearanceTrackingResult] =
    useState(null);

  const onChangeValue = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const params = {
      year: selectedYear,
      houseBlNo: houseBlNo,
    };

    axios
      .get('http://localhost:8080/api/v1/customs-clearances/tracking', {
        params,
      })
      .then((resposne) => {
        setCustomsClearanceTrackingResult(resposne.data);
      })
      .catch((e) => {
        setCustomsClearanceTrackingResult(null);
        alert(e.response.data.reason);
      });
  };

  const CustomsClearanceTrackingDetail = () => {
    return (
      <div className="customs-clearance-tracking-detail">
        <div>
          <h2>기본정보</h2>
        </div>
        <table>
          <thead>
            <tr>
              <th>House B/L No</th>
              <th>물품명</th>
              <th>무게</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{customsClearanceTrackingResult.houseBlNo}</td>
              <td>{customsClearanceTrackingResult.itemName}</td>
              <td>
                {customsClearanceTrackingResult.weight}
                {customsClearanceTrackingResult.weightUnit}
              </td>
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
              <th>상태</th>
              <th>상세정보</th>
            </tr>
          </thead>
          <tbody>
            {customsClearanceTrackingResult.customsClearanceProgressTrackingResources.map(
              (customsClearanceProgress) => {
                return (
                  <tr key={customsClearanceProgress.processingDateTime}>
                    <td>{customsClearanceProgress.processingDateTime}</td>
                    <td>{customsClearanceProgress.status}</td>
                    <td>{customsClearanceProgress.detailStatus}</td>
                  </tr>
                );
              }
            )}
          </tbody>
        </table>
      </div>
    );
  };

  const currentYear = new Date().getFullYear();
  const recentYears = Array.from(
    { length: 5 },
    (_, index) => currentYear - index
  );

  return (
    <div className="customs-clearance-print">
      <form onSubmit={handleSubmit}>
        <select id="year" name="year" onChange={onChangeValue} required>
          <option value="" disabled>
            년도를 선택하세요
          </option>
          {recentYears.map((year) => (
            <option key={year} value={year}>
              {year}
            </option>
          ))}
        </select>
        <input
          type="text"
          id="house-bl-no"
          name="houseBlNo"
          onChange={onChangeValue}
          placeholder="House B/L No"
          required
        />
        <button type="submit" id="search-button">
          조회
        </button>
      </form>
      {customsClearanceTrackingResult && <CustomsClearanceTrackingDetail />}
    </div>
  );
};

export default CustomsClearance;
