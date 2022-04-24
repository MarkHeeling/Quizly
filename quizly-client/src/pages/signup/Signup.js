import React, { useState } from "react";
import { registerUser } from "../../network/user";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";

export default function Signup() {
  const [apiError, setApiError] = React.useState(false);
  const [passwordShown, setPasswordShown] = useState(false);

  const togglePassword = () => {
    setPasswordShown(!passwordShown);
  };
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    getValues,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    registerUser(data).then(
      function () {
        navigate("/inloggen");
      },
      (error) => {
        setApiError(true);
      }
    );
  };

  return (
    <>
      <h1 className="title-center">Registreren</h1>
      <div className="container">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="container-middle">
          <div className="row">
              <label name="name">Naam</label>
              <div className="input">
                <input
                  type="text"
                  {...register("name", {
                    required: "Dit is veld is verplicht",
                  })}
                  id="name"
                />
                {errors.name && (
                  <p className="err-message">{errors.name?.message} </p>
                )}
              </div>
            </div>
            <div className="row">
              <label name="username">Gebruikersnaam</label>
              <div className="input">
                <input
                  type="text"
                  {...register("username", {
                    required: "Dit is veld is verplicht",
                  })}
                  id="username"
                />
                {errors.username && (
                  <p className="err-message">{errors.username?.message} </p>
                )}
              </div>
            </div>
            <div className="row">
              <label name="email">Emailadres</label>
              <div className="input">
                <input
                  type="email"
                  {...register("email", {
                    required: "Dit is veld is verplicht",
                  })}
                  name="email"
                  id="email"
                />
                {errors.email && (
                  <p className="err-message">{errors.email?.message} </p>
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
                    minLength: {
                      value: 8,
                      message: "Minimaal 8 karakters",
                    },
                  })}
                  name="password"
                  id="password"
                />
                {errors.password && (
                  <p className="err-message">{errors.password?.message} </p>
                )}
              
              <button
                  onClick={togglePassword}
                  className="toggle-button"
                  type="button"
                >
                  {passwordShown ? "verberg" : "toon"}
                </button>
                </div>
            </div>
            <div className="row">
              <label name="confirmPassword">Wachtwoord bevestigen</label>
              <div className="input">
                <input
                  {...register("confirmPassword", {
                    required: "Dit is veld is verplicht",
                    validate: {
                      matchesPreviousPassword: (value) => {
                        const { password } = getValues();
                        return (
                          password === value ||
                          "De wachtwoorden zijn niet gelijk"
                        );
                      },
                    },
                  })}
                  type={passwordShown ? "text" : "password"}
                  id="confirmPassword"
                />
                {errors.confirmPassword && (
                  <p className="err-message">
                    {errors.confirmPassword?.message}{" "}
                  </p>
                )}
              </div>
            </div>
          </div>
          {apiError && (
            <div className="err-message-container">
              Het opgegeven e-mailadres is al in gebruik. Ben je je{" "}
              <Link to="/wachtwoord-vergeten">wachtwoord vergeten</Link>?
            </div>
          )}
          <div className="container-bottom">
            <button className="button">Registreren</button>
          </div>
        </form>
      </div>
    </>
  );
}
