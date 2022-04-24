import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { getQuestion } from "../network/trivia";

export default function CreateQuestion({ questionId }) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();
  const [question, setQuestion] = useState();
  const [apiError, setApiError] = React.useState(false);

  useEffect(() => {
    getQuestion(questionId).then(
      function (response) {
        setQuestion(response.data);
      },
      (error) => {
        console.error(error);
      }
    );
  }, []);

  useEffect(() => {
    reset(question);
  }, [question, reset]);

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
