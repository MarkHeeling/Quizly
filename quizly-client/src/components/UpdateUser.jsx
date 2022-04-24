import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { getUser } from "../network/user";

export default function UpdateUser({ userId }) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();
  const [user, setUser] = useState();
  const [apiError, setApiError] = React.useState(false);

  useEffect(() => {
    getUser(userId).then(
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

  const onSubmit = async (data) => {
    console.log(data)
  };

  return (
    <>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="container-middle">
          <div className="row">
              <label name="username">Gebruikersnaam</label>
              <div className="input">
                <input
                  type="text"
                  {...register("username", {
                    required: "Dit is veld is verplicht",
                  })}
                  onChange={(e) => (e.target.value)}
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
                  onChange={(e) => (e.target.value)}
                  id="email"
                />
                {errors.email && (
                  <p className="err-message">{errors.email?.message} </p>
                )}
              </div>
            </div>
          </div>
          <div className="container-bottom">
            <button className="button">Wijzigen</button>
          </div>
        </form>
    </>
  );
}
