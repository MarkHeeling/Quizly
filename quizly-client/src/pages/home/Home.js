import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import "./Home.css";

export default function Home() {
  const { isAuth } = useContext(AuthContext);
  return (
    <div className="hero-section">
      <div className="hero-text">
        De Quiz van
        <br /> het jaar!
      </div>
      <div>
        {isAuth ? (
          <Link to="/lobby" className="button cta-button">
            Start
          </Link>
        ) : (
          <Link to="/inloggen" className="button cta-button">
            Start
          </Link>
        )}
      </div>
    </div>
  );
}
