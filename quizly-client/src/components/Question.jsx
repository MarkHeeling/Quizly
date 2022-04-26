import React, { useContext, useEffect, useState } from "react";
import { QuizContext } from "../context/QuizContext";
import InfoBar from "./InfoBar/InfoBar";
import ProgressBar from "./ProgressBar";

export default function Question({ questions, correctAnswers }) {
  const {
    endGame,
    timePerQuestion,
    totalCorrectAnswers,
    increaseCorrectAnswers,
    players,
  } = useContext(QuizContext);

  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [disableQuestions, setDisableQuestions] = useState(false);
  const [wrongAnswer, setWrongAnswer] = useState(false);
  const [answerStyling, setAnswerStyling] = useState(null);
  const [timerPercentage, setTimerPercentage] = useState(100);
  const [disableTimer, setDisableTimer] = useState(false);
  const [currentPlayer, setCurrentPlayer] = useState("Speler");

  useEffect(() => {
    setCurrentPlayer(players[0].name);
  }, []);

  useEffect(() => {
    const nextQuestion = currentQuestion + 1;

    if (timerPercentage > 0 && !disableTimer) {
      setTimeout(
        () => setTimerPercentage(timerPercentage - 1),
        timePerQuestion * 10
      );
    } else if (!disableTimer && timerPercentage === 0) {
      setWrongAnswer(true);
      setDisableQuestions(true);

      if (nextQuestion === questions.length) {
        setTimeout(() => {
          endGame();
        }, 1000);
      } else {
        setTimeout(() => {
          setCurrentQuestion(nextQuestion);
          setTimerPercentage(100);
          setWrongAnswer(false);
          setDisableQuestions(false);
        }, 1000);
      }
    }
  }, [timerPercentage]);

  const handleAnswer = (isCorrect, id) => {
    const nextQuestion = currentQuestion + 1;
    setDisableTimer(true);
    setDisableQuestions(true);
    setAnswerStyling(id);

    if (isCorrect === true) {
      increaseCorrectAnswers();
    } else {
      setWrongAnswer(true);
    }

    if (nextQuestion < questions.length) {
      setTimeout(() => {
        setCurrentQuestion(nextQuestion);
        setDisableQuestions(false);
        setTimerPercentage(100);
        setDisableTimer(false);
        setWrongAnswer(false);
      }, 1000);
      return;
    }

    if (nextQuestion === questions.length) {
      setTimeout(() => {
        endGame();
      }, 1000);
    }
  };

  return (
    <>
      <InfoBar
        playerName={currentPlayer}
        correctAnswers={totalCorrectAnswers}
      />
      <ProgressBar timerPercentage={timerPercentage} />
      <div className="game-container">
        <div className="subject">{questions[currentQuestion].category}</div>
        <div className="question">{questions[currentQuestion].question}</div>
        <div className="answers">
          {questions[currentQuestion].answers?.map((answer) => {
            const answerStyle =
              answerStyling === answer.id
                ? answer.isCorrect
                  ? "answer correct"
                  : "answer wrong"
                : "answer";
            const correctAnswer =
              correctAnswers[currentQuestion] === answer.name && wrongAnswer
                ? "correct"
                : "";

            return (
              <div
                onClick={() => handleAnswer(answer.isCorrect, answer.id)}
                className={`${answerStyle} ${correctAnswer}`}
                key={answer.id}
                disabled={disableQuestions}
              >
                {answer.name}
              </div>
            );
          })}
        </div>
      </div>
    </>
  );
}
