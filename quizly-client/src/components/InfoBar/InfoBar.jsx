import { ImArrowLeft2 } from "react-icons/im";
import { BsFillCheckCircleFill } from "react-icons/bs"
import React from "react";
import "./InfoBar.css"

export default function InfoBar({playerName, correctAnswers}) {
  return (
    <div className="infobar">
      <div className="infobar-items return-arrow"><ImArrowLeft2 size={30}/></div>
      <div className="infobar-items playername">{playerName}</div>
      <div className="infobar-items correct-answers"><div><BsFillCheckCircleFill size={18}/> {correctAnswers}</div></div>
    </div>
  );
}
