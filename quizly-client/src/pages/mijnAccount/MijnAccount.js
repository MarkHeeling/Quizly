import React, { useContext } from "react";
import { Link, Outlet } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import "./MijnAccount.css";


export default function MijnAccount() {
  const { role } = useContext(AuthContext);

  return (
    <>
      <h1 className="title-center">Mijn account</h1>
      <div className="account-container">
        <div className="menubar">
          <nav className="menubar-nav">
            <ul>
              <li>
                <Link to="profiel">Profiel</Link>
              </li>
              <li>
                <Link to="vragen">Vragen</Link>
              </li>
              {role === "ROLE_ADMIN" && (
                <li>
                  <Link to="gebruikers">Gebruikers</Link>
                </li>
              )}
            </ul>
          </nav>
        </div>
        <div className="user-tab">
          <div className="form-container">
            <Outlet />
          </div>
        </div>
      </div>
    </>
  );
}
