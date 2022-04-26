package nl.markheeling.quizly.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 250)
    private String question;

    @NotBlank
    @Size(max = 40)
    private String correct_answer;

    @NotBlank
    @Size(max = 200)
    private String incorrect_answer;

    @NotBlank
    @Size(max = 40)
    private String category;

    @NotBlank
    @Size(max = 40)
    private String created_by;

    public Question() {

    }

    public Question(String question, String correct_answer, String incorrect_answer, String created_by, String category) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answer = incorrect_answer;
        this.category = category;
        this.created_by = created_by;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}