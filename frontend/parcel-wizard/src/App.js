import React from "react";
import { Routes, Route, Link, useNavigate } from "react-router-dom";

import Courier from "./pages/Courier";
import CustomsClearance from "./pages/CustomsClearance";
import Home from "./pages/Home";
import headerLogo from "./assets/header-icon.png";

const NavigationBar = () => {
  return (
    <nav className="nav">
      <Link to="/clearance-clearances">통관조회</Link> |{" "}
      <Link to="/couriers">배송조회</Link>
    </nav>
  );
};

const App = () => {
  const navigate = useNavigate();

  const handleImageClick = () => {
    navigate("/");
  };

  return (
    <div className="App">
      <div id="home-header">
        <img
          id="header-logo"
          alt="header-logo"
          src={headerLogo}
          onClick={handleImageClick}
        />
      </div>
      <NavigationBar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/couriers" element={<Courier />} />
        <Route path="/clearance-clearances" element={<CustomsClearance />} />
      </Routes>
    </div>
  );
};

export default App;
