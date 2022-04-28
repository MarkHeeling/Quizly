// import axios from "axios";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { uploadFile } from "../network/user";

export default function ProfilePicture() {
  const [apiSucces, setApiSucces] = useState(false);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    const image = data.image[0];
    console.log(image)
    let formData = new FormData();
    formData.append("file", image);
    uploadFile(formData).then(
      function () {
        setApiSucces(true);
      },
      (error) => {
        console.error(error);
      }
    );
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="container-middle">
          <div className="row">
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
            </div>
            <p className="err-message">{errors.image?.message} </p>
          </div>
        </div>
        {apiSucces && (
            <div className="succ-message-container">
              Foto succesvol geupload.
            </div>
          )}
        <div className="form-submit">
          <button className="button form-button">Uploaden</button>
        </div>
      </form>
    </>
  );
}
