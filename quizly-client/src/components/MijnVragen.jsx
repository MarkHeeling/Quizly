import React, { useEffect } from "react";
import Modal from "react-modal";
import { HiOutlinePencilAlt, HiOutlineTrash } from "react-icons/hi";
import {
  getQuestions,
  deleteQuestion,
  createQuestion,
  getQuestion,
} from "../network/trivia";
import { useState } from "react";
import { useForm } from "react-hook-form";

export default function Users() {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();
  const [questions, setQuestions] = useState([]);
  const [question, setQuestion] = useState();
  const [modalIsOpen, setIsOpen] = useState(false);
  const [questionId, setQuestionId] = useState();

  useEffect(() => {
    getQuestions().then(
      function (response) {
        setQuestions(response.data);
      },
      (error) => {
        console.error(error);
      }
    );
    getQuestion(questionId).then(
      function (response) {
        setQuestion(response.data);
      },
      (error) => {
        console.error(error);
      }
    );
  }, [modalIsOpen]);

  useEffect(() => {
    reset(question);
  }, [questions, reset]);

  function openModal(id) {
    setQuestionId(id);
    setIsOpen(true);
  }

  function closeModal() {
    setIsOpen(false);
  }

  const handleDeleteQuestion = (id) => {
    deleteQuestion(id).then(
      function (response) {
        setQuestion(response.data);
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const onSubmit = async (data) => {
    console.log(data);
    createQuestion(data).then(
      function (response) {
        setQuestions([...questions, response.data]);
        setIsOpen(false);
      },
      (error) => {
        console.error(error);
      }
    );
  };

  return (
    <div className="users-table">
      <table>
        <thead>
          <tr>
            <th>Vraag</th>
            <th>Categorie</th>
            <th className="add-question">
              <button className="button" onClick={openModal}>
                +
              </button>
            </th>
          </tr>
        </thead>
        <tbody>
          {questions.map((question) => {
            return (
              <tr key={question.id}>
                <td>{question.question}</td>
                <td>{question.category}</td>
                <td className="table-actions">
                  <button
                    onClick={() => handleDeleteQuestion(question.id)}
                    className="table-button delete"
                  >
                    <HiOutlineTrash size={25} />
                  </button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
      <div>
        <Modal
          isOpen={modalIsOpen}
          onRequestClose={closeModal}
          shouldCloseOnOverlayClick={true}
          contentLabel="Add question"
        >
          <div className="container">
            <div className="close-modal">
              <button className="button close-button" onClick={closeModal}>
                X
              </button>
            </div>
            <form onSubmit={handleSubmit(onSubmit)}>
              <div className="container-middle">
                <div className="row">
                  <label name="question">Vraag</label>
                  <div className="input">
                    <input
                      type="text"
                      {...register("question", {
                        required: "Dit is veld is verplicht",
                      })}
                      onChange={(e) => e.target.value}
                      id="question"
                    />
                  </div>
                  {errors.question && (
                    <p className="err-message">{errors.question?.message} </p>
                  )}
                </div>
                <div className="row">
                  <label name="category">Categorie</label>
                  <div className="input">
                    <select
                      {...register("category", {
                        required: "Dit is veld is verplicht",
                      })}
                      id="category"
                    >
                      <option value="Geschiedenis">Geschiedenis</option>
                      <option value="Sport">Sport</option>
                      <option value="Aardrijkskunde">Aardrijkskunde</option>
                      <option value="Kunst">Kunst</option>
                      <option value="Entertainment">Entertainment</option>
                      <option value="Wetenschap">Wetenschap</option>

                    </select>
                  </div>
                  {errors.category && (
                    <p className="err-message">{errors.category?.message} </p>
                  )}
                </div>
                <div className="row">
                  <label name="correctAnswer">Goede antwoord</label>
                  <div className="input">
                    <input
                      type="text"
                      {...register("correct_answer", {
                        required: "Dit is veld is verplicht",
                      })}
                      onChange={(e) => e.target.value}
                      id="correctAnswer"
                    />
                  </div>
                  {errors.correctAnswer && (
                    <p className="err-message">
                      {errors.correctAnswer?.message}{" "}
                    </p>
                  )}
                </div>
                <div className="row">
                  <label name="incorrectAnswer">Foute antwoorden</label>
                  <div className="input">
                    <input
                      type="text"
                      {...register("incorrect_answer", {
                        required: "Dit is veld is verplicht",
                      })}
                      onChange={(e) => e.target.value}
                      id="incorrectAnswer"
                      placeholder="antwoord1,antwoord2,antwoord3"
                    />
                  </div>
                  {errors.incorrectAnswerThree && (
                    <p className="err-message">
                      {errors.incorrectAnswerThree?.message}{" "}
                    </p>
                  )}
                </div>
              </div>
              <div className="container-bottom">
                <button className="button">Wijzigen</button>
              </div>
            </form>
          </div>
        </Modal>
      </div>
    </div>
  );
}
