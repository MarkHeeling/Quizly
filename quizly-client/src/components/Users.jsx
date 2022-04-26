import React, { useEffect } from "react";
import Modal from "react-modal";
import { HiOutlinePencilAlt, HiOutlineTrash } from "react-icons/hi";
import { deleteUser, getAllUsers } from "../network/user";
import { useState } from "react";
import UpdateUser from "./UpdateUser";

export default function Users() {
  const [users, setUsers] = React.useState([]);
  const [modalIsOpen, setIsOpen] = useState(false);
  const [userId, setUserId] = useState();

  useEffect(() => {
    getAllUsers().then(
      function (response) {
        setUsers(response.data);
      },
      (error) => {
        console.error(error);
      }
    );
  }, []);

  function openModal(id) {
    setUserId(id);
    setIsOpen(true);
  }

  function closeModal() {
    setIsOpen(false);
  }

  const handleDeleteUser = (id) => {
    deleteUser(id).then(
      function (response) {
        setUsers(response.data);
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
            <th>Gebruikersnaam</th>
            <th>Email</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => {
            return (
              <tr key={user.id}>
                <td className="userID">{user.id}</td>
                <td>{user.username}</td>
                <td>{user.email}</td>
                <td className="table-actions">
                  <button
                    onClick={() => openModal(user.id)}
                    className="table-button edit"
                  >
                    <HiOutlinePencilAlt size={25} />
                  </button>
                  <button
                    onClick={() => handleDeleteUser(user.id)}
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
          contentLabel="Update Modal"
        >
          <div className="container">
            <div className="close-modal">
              <button className="button close-button" onClick={closeModal}>
                X
              </button>
            </div>
            <UpdateUser userId={userId} />
          </div>
        </Modal>
      </div>
    </div>
  );
}
