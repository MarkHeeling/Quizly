import React, { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import jwt_decode from "jwt-decode";

export const AuthContext = createContext({});

function AuthProvider({ children } ) {
  const [auth, toggleAuth] = useState({
    isAuth: false,
    status: "pending",
  });

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decoded = jwt_decode(token);
      const currentTime = Date.now() / 1000;
      if (decoded.exp < currentTime) {
        localStorage.removeItem("token");
        toggleAuth({
          isAuth: false,
          status: "done",
        });
      } else {
        toggleAuth({
          ...auth,
          isAuth: true,
          status: "done",
        });
      }
    } else {
      toggleAuth({
        isAuth: false,
        status: "done",
      });
    }
  }, []);

  function login(jwtToken) {
    localStorage.setItem("token", jwtToken);

    toggleAuth({
      ...auth,
      isAuth: true,
    });

    navigate("/");
  }

  function logout() {
    localStorage.removeItem("token");

    toggleAuth({
      ...auth,
      isAuth: false,
    });
    navigate("/");
  }

  const contextData = {
    isAuth: auth.isAuth,
    status: auth.status,
    login,
    logout,
  };

  const status = auth.status === "done";

  return (
    <AuthContext.Provider value={contextData}>
      {status ? children : <p>Laden...</p>}
    </AuthContext.Provider>
  );
}

export { AuthProvider };
