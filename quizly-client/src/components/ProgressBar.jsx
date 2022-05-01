import React from "react";

export default function ProgressBar({ timerPercentage }) {
  return (
    <div>
      <div className="countdown">
        <div
          className="countdown-progress"
          style={{ width: `${timerPercentage}%` }}
        ></div>
      </div>
    </div>
  );
}
