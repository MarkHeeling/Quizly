package nl.markheeling.quizly.controller;

import nl.markheeling.quizly.exception.ResourceNotFoundException;
import nl.markheeling.quizly.model.Question;
import nl.markheeling.quizly.payload.ApiResponse;
import nl.markheeling.quizly.payload.CreateQuestionRequest;
import nl.markheeling.quizly.repository.QuestionRepository;
import nl.markheeling.quizly.security.CurrentUser;
import nl.markheeling.quizly.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

        @Autowired
        private QuestionRepository questionRepository;

        @GetMapping("/getQuestions")
        @PreAuthorize("hasRole('USER')")
        public List<Question> getQuestions() {
                return questionRepository.findAll();
        } 

        @GetMapping("/{id}")
        public Question getQuestion(@PathVariable(value = "id") Long id) {
                return questionRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
        }

        @PostMapping("/newQuestion")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<?> newQuestion(@Valid @RequestBody CreateQuestionRequest createQuestionRequest, @CurrentUser UserPrincipal currentUser) {
                Question result = new Question(createQuestionRequest.getQuestion(), createQuestionRequest.getCorrect_answer(),
                createQuestionRequest.getIncorrect_answer(), currentUser.getUsername());

                Question newQuestion = questionRepository.save(result);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/api/question/{id}")
                                .buildAndExpand(newQuestion.getId()).toUri();

                return ResponseEntity.created(location).body(new ApiResponse(true, "Question saved successfully"));
        }

        @DeleteMapping("/deleteQuestion/{id}")
        @PreAuthorize("hasRole('USER')")
        public ResponseEntity<?> deleteQuestion(@PathVariable(value = "id") Long id) {
                Question question = questionRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));

                questionRepository.delete(question);

                return ResponseEntity.ok().body(new ApiResponse(true, "Question deleted successfully"));
        }
}
