package nl.markheeling.quizly.payload;

import javax.validation.constraints.*;

public class CreateQuestionRequest {
  @NotBlank
  @Size(max = 250)
  private String question;

  @NotBlank
  @Size(max = 40)
  private String correct_answer;

  @NotBlank
  @Size(max = 200)
  private String incorrect_answer;

  public String getQuestion() {
    return question;
  }

  public void getQuestion(String question) {
    this.question = question;
  }

  public String getCorrect_answer() {
    return correct_answer;
  }

  public void setCorrect_answer(String correct_answer) {
    this.correct_answer = correct_answer;
  }

  public String getIncorrect_answer() {
    return incorrect_answer;
  }

  public void setIncorrect_answer(String incorrect_answer) {
    this.incorrect_answer = incorrect_answer;
  }

}
