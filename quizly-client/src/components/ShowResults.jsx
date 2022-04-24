import React from "react";

export default function ShowResults({ totalCorrectAnswers }) {
  return (
    <div className="container">
      <div className="title-center">
        Einde quiz <br /> Je had {totalCorrectAnswers} {totalCorrectAnswers > 1 ? "goede antwoorden" : "goed antwoord"}
      </div>
    </div>
  );
}
