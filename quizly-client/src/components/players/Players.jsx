import React, { useState } from "react";
import { XCircleIcon } from "@heroicons/react/solid";
import "./Player.css";
import { useContext } from "react";
import { QuizContext } from "../../context/QuizContext";

export default function Players() {
  const { addPlayers } = useContext(QuizContext);
  const [players, setPlayers] = useState([]);
  const [totalPlayers, setTotalPlayers] = useState(null);
  const [playerName, setPlayerName] = useState("");
  const [playerAdded, setPlayerAdded] = useState(true);

  const addPlayer = (e) => {
    e.preventDefault();

    if (playerName !== "") {
      setPlayers([
        ...players,
        {
          id: totalPlayers + 1,
          name: playerName,
          correctAnswers: 0,
        },
      ]);
      setTotalPlayers(totalPlayers + 1);
      setPlayerName("");
    }

    if(totalPlayers < 1) {
      setPlayerAdded(false)
    }

    if(totalPlayers > 1) {
      setPlayerAdded(true)
    }
  };

  const deletePlayer = (id) => {
    const array = [...players];
    const newPlayers = array.filter((player) => player.id !== id);
    setPlayers(newPlayers);
    setTotalPlayers(totalPlayers - 1);
    if(totalPlayers < 1) {
      setPlayerAdded(false)
    }

    if(totalPlayers > 1) {
      setPlayerAdded(true)
    }
  };

  return (
    <>
      <div>
        <div className="add-player input">
          <form>
            <input
              type="text"
              placeholder="Voeg minimaal 1 speler toe"
              value={playerName}
              onChange={(e) => setPlayerName(e.target.value)}
            />
            <button type="submit" className="add-button" onClick={addPlayer}>
              +
            </button>
          </form>
        </div>
        <div className="player-wrapper">
          {players.map((player) => (
            <div className="player" key={player.id}>
              <div className="player-name">{player.name}</div>
              <button
                type="button"
                className="delete-button"
                onClick={() => deletePlayer(player.id)}
              >
                <XCircleIcon className="delete-icon" />
              </button>
            </div>
          ))}
        </div>
      </div>
      <div className="start-round">
        <button
          className="button button-start"
          onClick={() => addPlayers(players)}
          disabled={playerAdded}
        >
          Volgende
        </button>
      </div>
    </>
  );
}
