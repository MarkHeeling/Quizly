// import axios from "axios";
import React, { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { getUser } from "../network/user";

export default function Profiel() {
  const [user, setUser] = useState(null);
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm();

  useEffect(() => {
    getUser().then(
      function (response) {
        setUser(response.data);
      },
      (error) => {
        console.error(error);
      }
    );
  }, []);

  useEffect(() => {
    reset(user);
  }, [user, reset]);

  const onSubmit = (data) => {
    console.log(data);
  };

  return (
    <>
      {user && (
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="container-middle">
            <div className="row">
              <div className="half">
                <label name="username">Gebruikersnaam</label>
                <div className="input">
                  <input
                    type="text"
                    {...register("username", {
                      required: "Dit is veld is verplicht",
                    })}
                    id="username"
                  />
                  <p className="err-message">{errors.username?.message} </p>
                </div>
              </div>
              <div className="half">
                <label name="image">Profielfoto</label>
                <div className="input">
                  <input
                    type="file"
                    accept="image/*"
                    {...register("image", {
                      required: "Dit is veld is verplicht",
                    })}
                    id="image"
                  />
                  <p className="err-message">{errors.image?.message} </p>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="half">
                <label name="email">Emailadres</label>
                <div className="input">
                  <input
                    type="text"
                    {...register("email", {
                      required: "Dit is veld is verplicht",
                    })}
                    id="username"
                  />
                  <p className="err-message">{errors.email?.message} </p>
                </div>
              </div>
              <div className="half">
                <label name="name">Naam</label>
                <div className="input">
                  <input
                    type="text"
                    {...register("name", {
                      required: "Dit is veld is verplicht",
                    })}
                    id="name"
                  />
                  <p className="err-message">{errors.name?.message} </p>
                </div>
              </div>
            </div>
          </div>
          <div className="form-submit">
            <button className="button form-button">Bijwerken</button>
          </div>
        </form>
      )}
      {!user && (
        <div className="text-center p-3">
          <span className="spinner-border spinner-border-lg align-center"></span>
        </div>
      )}
    </>
  );
}
