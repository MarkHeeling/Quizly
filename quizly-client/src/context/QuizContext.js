import React, { createContext, useState } from "react";
import { useNavigate } from "react-router-dom";

export const QuizContext = createContext({});

function QuizProvider({ children }) {
  const navigate = useNavigate();
  const [totalCorrectAnswers, setTotalCorrectAnswers] = useState(0);
  const [tabIndex, setTabIndex] = useState(0);


  const [quizData, setQuizData] = useState({
    gameState: "quiz",
    players: [],
    timePerQuestion: 5,
    selectedCategories: [],
  });

  function addPlayers(players) {
    setQuizData({
      ...quizData,
      players: players,
    })
    setTabIndex(tabIndex + 1)
  }

  function addCategories(categories) {
    setQuizData({
      ...quizData,
      selectedCategories: categories,
    })
    setTabIndex(tabIndex + 1)
  }

  function startQuiz(settings) {
    setQuizData({
      ...quizData,
      timePerQuestion: settings,
      gameState: "quiz",
    });
    console.log(quizData)
    navigate("/quiz");
  }

  function endGame() {
    setQuizData({
      ...quizData,
      gameState: "end",
    });
  }

  function increaseCorrectAnswers() {
    setTotalCorrectAnswers(totalCorrectAnswers + 1);
  }

  const contextData = {
    gameState: quizData.gameState,
    timePerQuestion: quizData.timePerQuestion,
    selectedCategories: quizData.selectedCategories,
    players: quizData.players,
    totalCorrectAnswers,
    tabIndex,
    setTabIndex,
    addPlayers,
    addCategories,
    startQuiz,
    increaseCorrectAnswers,
    endGame,
  };

  return (
    <QuizContext.Provider value={contextData}>{children}</QuizContext.Provider>
  );
}

export { QuizProvider };
