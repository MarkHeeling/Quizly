import React, { useContext } from "react";
import { Navigate, Route, Routes, useLocation } from "react-router-dom";
import { AuthContext } from "./context/AuthContext";
import "./App.css";
import Header from "./components/header/Header";
import MijnVragen from "./components/MijnVragen";
import Profiel from "./components/Profiel";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import MijnAccount from "./pages/mijnAccount/MijnAccount";
import Signup from "./pages/signup/Signup";
import Users from "./components/Users";
import Lobby from "./pages/lobby/Lobby";
import Quiz from "./pages/quiz/Quiz";
import ProfilePicture from "./components/ProfilePicture";

function App() {
  const { isAuth, role } = useContext(AuthContext);
  const location = useLocation();

  return (
    <div className="App">
      {location.pathname !== "/quiz" && <Header />}
      <Routes>
        <Route index path="/" element={<Home />} />
        <Route path="/inloggen" element={<Login />} />
        <Route path="/registreren" element={<Signup />} />
        {isAuth && (
          <>
            <Route path="/mijn-account" element={<MijnAccount />}>
              <Route path="profiel" element={<Profiel />} />
              <Route path="profielfoto" element={<ProfilePicture />} />
              <Route path="vragen" element={<MijnVragen />} />
              {role === "ROLE_ADMIN" && (
                <Route path="gebruikers" element={<Users />} />
              )}
            </Route>
            <Route path="/lobby" element={<Lobby />} />
            <Route path="/quiz" element={<Quiz />} />
          </>
        )}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </div>
  );
}

export default App;
