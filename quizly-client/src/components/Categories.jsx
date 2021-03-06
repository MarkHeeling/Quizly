import React from "react";
import { useState } from "react";
import { useContext } from "react";
import { QuizContext } from "../context/QuizContext";

export default function Categories() {
  const { addCategories } = useContext(QuizContext);

  // Door tijdsgebrek heb ik deze gegevens er hard coded in gezet.
  const [categories, setCategories] = useState([
    {
      id: 1,
      name: "Geschiedenis",
    },
    {
      id: 2,
      name: "Sport",
    },
    {
      id: 3,
      name: "Aardrijkskunde",
    },
    {
      id: 4,
      name: "Kunst",
    },
    {
      id: 5,
      name: "Entertainment",
    },
    {
      id: 6,
      name: "Wetenschap",
    },
  ]);
  const [selectedCategories, setSelectedCategories] = useState([]);

  const handleSelectCategory = (categoryId) => {
    if (selectedCategories.includes(categoryId)) {
      setSelectedCategories(
        selectedCategories.filter((id) => id !== categoryId)
      );
      return;
    }

    setSelectedCategories([...selectedCategories, categoryId]);
  };

  return (
    <>
      <h3>Categorieen [Work in progress]</h3>
      <div className="categories">
        {categories.map((category) => {
          const categoryStyle = selectedCategories.includes(category.id)
            ? "category selected"
            : "category";
          return (
            <div
              onClick={() => handleSelectCategory(category.id)}
              className={`${categoryStyle}`}
              key={category.id}
            >
              <div className="category-image"></div>
              <div className="category-name">{category.name} </div>
            </div>
          );
        })}
      </div>
      <div className="start-round">
        <button
          className="button button-start"
          onClick={() => addCategories(selectedCategories)}
        >
          Volgende
        </button>
      </div>
    </>
  );
}
