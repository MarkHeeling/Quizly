import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import "./Header.css";

export default function Header() {
  const { isAuth, logout } = useContext(AuthContext);

  return (
    <header className="header">
      <nav className="header-items">
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/speluitleg">Speluitleg</Link>
          </li>
        </ul>
      </nav>
      <Link to="/" className="header-items logo-header">
        Quizly
      </Link>
      <ul className="header-items">
        {isAuth ? (
          <>
            <li>
              <button type="button" onClick={logout} className="button menu-login">
                Uitloggen
              </button>
            </li>
            <li>
              <Link to="/mijn-account/profiel" className="button menu-signup">
                Mijn account
              </Link>
            </li>
          </>
        ) : (
          <>
            <li>
              <Link to="/inloggen" className="button menu-login">
                Inloggen
              </Link>
            </li>
            <li>
              <Link to="/registreren" className="button menu-signup">
                Registreren
              </Link>
            </li>
          </>
        )}
      </ul>
    </header>
  );
}
