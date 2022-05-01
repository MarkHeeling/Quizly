import React from "react";
import { useState } from "react";
import { useContext } from "react";
import { QuizContext } from "../context/QuizContext";

export default function QuizSettings() {
  const { startQuiz } = useContext(QuizContext);
  const [seconds, setSeconds] = useState(10);

  return (
    <>
      <h2 className="title-center">Instellingen</h2>
      <div className="settings">
        <div>
          <label id="seconds-per-round">Aantal seconde per ronde </label>
          <input
            type="range"
            id="seconds-per-round"
            name="seconds-per-round"
            min="5"
            max="15"
            step="1"
            value={seconds}
            onChange={(e) => setSeconds(e.target.value)}
          />
          {seconds}
        </div>
      </div>
      <div className="start-round">
        <button
          className="button button-start"
          onClick={() => startQuiz(seconds)}
        >
          Start Quiz
        </button>
      </div>
    </>
  );
}
