import React from "react";
import "./Lobby.css";
import GameOverview from "../../components/gameoverview/GameOverview"

export default function Lobby() {

  return (
    <>
      <div>
        <h1 className="title-center">Lobby</h1>
        <div className="lobby-container">
          <div className="game-settings">
            <GameOverview />
          </div>
        </div>
      </div>
    </>
  );
}
