import React, { useContext, useState } from "react";
import { AuthContext } from "../../context/AuthContext";
import { useForm } from "react-hook-form";
import { Link } from "react-router-dom";
import { loginUser } from "../../network/user";
import "./Login.css";

export default function Login() {
  const { login } = useContext(AuthContext);
  const [apiError, setApiError] = React.useState(false);
  const [passwordShown, setPasswordShown] = useState(false);

  const togglePassword = () => {
    setPasswordShown(!passwordShown);
  };

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    loginUser(data).then(
      function (response) {
        login(response.data.accessToken);
      },
      () => {
        setApiError(true);
      }
    );
  };

  return (
    <>
      <h1 className="title-center">Inloggen</h1>
      <div className="container">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="container-middle">
            <div className="row">
              <label name="usernameOrEmail">Emailadres / Gebruikersnaam</label>
              <div className="input">
                <input
                  type="text"
                  {...register("usernameOrEmail", {
                    required: "Dit is veld is verplicht",
                  })}
                  id="usernameOrEmail"
                />
                {errors.usernameOrEmail && (
                  <p className="err-message">{errors.usernameOrEmail?.message} </p>
                )}
              </div>
            </div>
            <div className="row">
              <label name="password">Wachtwoord</label>
              <div className="input">
                <input
                  type={passwordShown ? "text" : "password"}
                  {...register("password", {
                    required: "Dit is veld is verplicht",
                  })}
                  name="password"
                  id="password"
                />
                <button
                  onClick={togglePassword}
                  className="toggle-button"
                  type="button"
                >
                  {passwordShown ? "verberg" : "toon"}
                </button>
                {errors.password && (
                  <p className="err-message">{errors.password?.message} </p>
                )}
              </div>
            </div>
          </div>
          {apiError && (
            <div className="err-message-container">
              De combinatie van het e-mailadres en het wachtwoord is niet bij
              ons bekend.
            </div>
          )}
          <div className="container-bottom">
            <button className="button">Inloggen</button>
          </div>
          <div className="forgot-password">
            <Link to="/wachtwoord-vergeten">Wachtwoord vergeten?</Link>
          </div>
        </form>
      </div>
    </>
  );
}
