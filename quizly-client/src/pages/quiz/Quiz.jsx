import React, { useContext, useEffect, useState } from "react";
import { getQuestions } from "../../network/trivia";
import { nanoid } from "nanoid";
import Question from "../../components/Question";
import { QuizContext } from "../../context/QuizContext";
import ShowResults from "../../components/ShowResults";
import { shuffle } from "../../helpers/shuffle";
import "./Quiz.css"

export default function Quiz() {
  const { gameState, totalCorrectAnswers} = useContext(QuizContext);
  const [triviaData, setTriviaData] = useState([]);
  const [questions, setQuestions] = useState([]);
  const [correctAnswers, setCorrectAnswers] = useState([])

  useEffect(() => {
    getQuestions().then(
      function (response) {
        setTriviaData(response.data);
        console.log(response.data)
      },
      (error) => {
        console.error(error);
      }
    );
  }, []);

  useEffect(() => {
    const questionArray = [];
    const correctAnswerArray = [];

    triviaData.map((data, index) => {
      const correctAnswers = data.correct_answer
      correctAnswerArray.push(correctAnswers)
      setCorrectAnswers(correctAnswerArray)
      const wrongAnswers = data.incorrect_answers;

      const wrongAnswersArray = wrongAnswers.map((data) => {
        return {
          name: data,
          isCorrect: false,
          id: nanoid(),
          index: index,
        };
      });
      const correctAnswer = {
        name: data.correct_answer,
        isCorrect: true,
        id: nanoid(),
        index: index,
      };
      wrongAnswersArray.push(correctAnswer);

      shuffle(wrongAnswersArray);
      const questionObject = {
        question: data.question,
        category: data.category,
        answers: wrongAnswersArray,
      };

      return questionArray.push(questionObject);
    });

    setQuestions(questionArray);
  }, [triviaData]);



  return (
    <>
      {gameState === "quiz" && questions.length > 0 && (
        <Question questions={questions} correctAnswers={correctAnswers} />
      )}
      {gameState === "end" && <ShowResults totalCorrectAnswers={totalCorrectAnswers} />}
    </>
  );
}
