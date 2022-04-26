import React from "react";
import { Link } from "react-router-dom";

export default function ShowResults({ totalCorrectAnswers }) {
  return (
    <div className="result-wrapper">
      <div className="container">
        <div className="title-center">
          Einde quiz <br /> Je had {totalCorrectAnswers}{" "}
          {totalCorrectAnswers > 1 ? "goede antwoorden" : "goed antwoord"}
        </div>
        <Link to="/lobby" className="button">
          Opnieuw
        </Link>
      </div>
    </div>
  );
}
