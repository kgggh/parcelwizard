import React from "react";
import '../styles/styles.css';

const Home = () => {
  return (
    <div className="home-page">
      <div id="home-title">
        <h1>서비스 이용전 안내</h1>
      </div>
      <div id="home-contents">
        <p>1. 택배사 및 관세청의 문제로 데이터 출력이 안될 수 있습니다.</p>
        <p>
          2. 알리익스프레스에서 주로 사용 중인 화물(택배)사만 조회할 수
          있습니다.(추가 예정)
        </p>
      </div>
    </div>
  );
};

export default Home;
