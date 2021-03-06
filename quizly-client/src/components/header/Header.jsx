import React, { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import "./Header.css";
import useDropdownMenu from "react-accessible-dropdown-menu-hook";
import { getProfilePicture } from "../../network/user";

export default function Header() {
  const { isAuth, logout } = useContext(AuthContext);
  const { buttonProps, itemProps, isOpen } = useDropdownMenu(2);

  const [profilePicturePath, setProfilePicturePath] = useState("standard-icon.svg");

  useEffect(() => {
    if (isAuth) {
      getProfilePicture().then((imagePath) => {
        setProfilePicturePath(imagePath.data);
      });
    }
  }, [isAuth]);

  return (
    <header className="header">
      <nav className="header-items">
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
        </ul>
      </nav>
      <Link to="/" className="header-items logo-header">
        Quizly
      </Link>
      <div className="header-items">
        {isAuth ? (
          <>
            <button
              style={{
                backgroundImage: `url("/uploads/${profilePicturePath}")`,
              }}
              className="button user-icon"
              {...buttonProps}
            />
            <div className={isOpen ? "visible header-dropdown" : ""} role="menu">
              <Link
                {...itemProps[0]}
                to="/mijn-account/profiel"
                className="button menu-signup"
              >
                Mijn account
              </Link>
              <button
                {...itemProps[1]}
                type="button"
                onClick={logout}
                className="button menu-login"
              >
                Uitloggen
              </button>
            </div>
          </>
        ) : (
          <>
            <ul>
              <li>
                <Link to="/inloggen" className="button menu-signup">
                  Inloggen
                </Link>
              </li>
            </ul>
          </>
        )}
      </div>
    </header>
  );
}
