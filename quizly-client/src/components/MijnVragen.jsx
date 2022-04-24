import React, { useEffect } from "react";
import Modal from "react-modal";
import { HiOutlinePencilAlt, HiOutlineTrash } from "react-icons/hi";
import { getQuestions } from "../network/trivia";
import { useState } from "react";
import CreateQuestion from "./CreateQuestion";

export default function Users() {
  const [questions, setQuestions] = React.useState([]);
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
  }, []);

  function openModal(id) {
    setQuestionId(id);
    setIsOpen(true);
  }

  function closeModal() {
    setIsOpen(false);
  }

  const deleteQuestion = (id) => {
    deleteQuestion(id).then(
      function (response) {
        setQuestions(response.data);
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
            <th className="userID">ID</th>
            <th>Vraag</th>
            <th><button className="button">Vraag aanmaken</button></th>
          </tr>
        </thead>
        <tbody>
          {questions.map((question) => {
            return (
              <tr key={question.id}>
                <td className="userID">{question.id}</td>
                <td>{question.username}</td>
                <td className="table-actions">
                  <button
                    onClick={() => openModal(question.id)}
                    className="table-button edit"
                  >
                    <HiOutlinePencilAlt size={25} />
                  </button>
                  <button
                    onClick={() => deleteQuestion(question.id)}
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
            <CreateQuestion questionId={questionId} />
          </div>
        </Modal>
      </div>
    </div>
  );
}
