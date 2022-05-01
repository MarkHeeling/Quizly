package nl.markheeling.quizly.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import nl.markheeling.quizly.exception.ResourceNotFoundException;

import nl.markheeling.quizly.model.Question;
import nl.markheeling.quizly.payload.CreateQuestionRequest;
import nl.markheeling.quizly.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = { QuestionController.class })
@ExtendWith(SpringExtension.class)
class QuestionControllerTest {
        @Autowired
        private QuestionController questionController;

        @MockBean
        private QuestionRepository questionRepository;

        @Test
        void testDeleteQuestion() throws Exception {
                Question question = new Question();
                question.setCategory("Kunst");
                question.setCorrect_answer("Correcte antwoord");
                question.setCreated_by("Jan 1, 2020 8:00am GMT+0100");
                question.setId(123L);
                question.setIncorrect_answer("Incorrecte antwoord");
                Optional<Question> ofResult = Optional.of(question);
                doNothing().when(this.questionRepository).delete((Question) any());
                when(this.questionRepository.findById((Long) any())).thenReturn(ofResult);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                                "/api/question/deleteQuestion/{id}",
                                123L);
                MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(
                                                MockMvcResultMatchers.content().string(
                                                                "{\"success\":true,\"message\":\"Question deleted successfully\"}"));
        }

        @Test
        void testDeleteQuestion2() throws Exception {
                Question question = new Question();
                question.setCategory("Kunst");
                question.setCorrect_answer("Correcte antwoord");
                question.setCreated_by("Jan 1, 2020 8:00am GMT+0100");
                question.setId(123L);
                question.setIncorrect_answer("Incorrecte antwoord");
                Optional<Question> ofResult = Optional.of(question);
                doThrow(new ResourceNotFoundException("?", "?", "Field Value")).when(this.questionRepository)
                                .delete((Question) any());
                when(this.questionRepository.findById((Long) any())).thenReturn(ofResult);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                                "/api/question/deleteQuestion/{id}",
                                123L);
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void testDeleteQuestion3() throws Exception {
                doNothing().when(this.questionRepository).delete((Question) any());
                when(this.questionRepository.findById((Long) any())).thenReturn(Optional.empty());
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                                "/api/question/deleteQuestion/{id}",
                                123L);
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void testDeleteQuestion4() throws Exception {
                Question question = new Question();
                question.setCategory("Kunst");
                question.setCorrect_answer("Correcte antwoord");
                question.setCreated_by("Jan 1, 2020 8:00am GMT+0100");
                question.setId(123L);
                question.setIncorrect_answer("Incorrecte antwoord");
                Optional<Question> ofResult = Optional.of(question);
                doNothing().when(this.questionRepository).delete((Question) any());
                when(this.questionRepository.findById((Long) any())).thenReturn(ofResult);
                MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete(
                                "/api/question/deleteQuestion/{id}",
                                123L);
                deleteResult.contentType("https://example.org/example");
                MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(deleteResult)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(
                                                MockMvcResultMatchers.content().string(
                                                                "{\"success\":true,\"message\":\"Question deleted successfully\"}"));
        }

        @Test
        void testGetQuestion() throws Exception {
                Question question = new Question();
                question.setCategory("Kunst");
                question.setCorrect_answer("Correcte antwoord");
                question.setCreated_by("Jan 1, 2020 8:00am GMT+0100");
                question.setId(123L);
                question.setIncorrect_answer("Incorrecte antwoord");
                Optional<Question> ofResult = Optional.of(question);
                when(this.questionRepository.findById((Long) any())).thenReturn(ofResult);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/question/{id}", 123L);
                MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string(
                                                                "{\"id\":123,\"question\":null,\"correct_answer\":\"Correcte antwoord\",\"incorrect_answer\":\"Incorrecte antwoord\","
                                                                                + "\"category\":\"Kunst\",\"created_by\":\"Jan 1, 2020 8:00am GMT+0100\"}"));
        }

        @Test
        void testGetQuestion2() throws Exception {
                when(this.questionRepository.findById((Long) any())).thenReturn(Optional.empty());
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/question/{id}", 123L);
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void testGetQuestion3() throws Exception {
                Question question = new Question();
                question.setCategory("Kunst");
                question.setCorrect_answer("Correcte antwoord");
                question.setCreated_by("Jan 1, 2020 8:00am GMT+0100");
                question.setId(123L);
                question.setIncorrect_answer("Incorrecte antwoord");
                Optional<Question> ofResult = Optional.of(question);
                when(this.questionRepository.findById((Long) any())).thenReturn(ofResult);
                MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/question/{id}", 123L);
                getResult.contentType("https://example.org/example");
                MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(getResult)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string(
                                                                "{\"id\":123,\"question\":null,\"correct_answer\":\"Correcte antwoord\",\"incorrect_answer\":\"Incorrecte antwoord\","
                                                                                + "\"category\":\"Kunst\",\"created_by\":\"Jan 1, 2020 8:00am GMT+0100\"}"));
        }

        @Test
        void testGetQuestion4() throws Exception {
                when(this.questionRepository.findById((Long) any()))
                                .thenThrow(new ResourceNotFoundException("?", "?", "Field Value"));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/question/{id}", 123L);
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void testGetQuestions() throws Exception {
                when(this.questionRepository.findAll()).thenReturn(new ArrayList<>());
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/question/getQuestions");
                MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content().string("[]"));
        }

        @Test
        void testGetQuestions2() throws Exception {
                when(this.questionRepository.findAll()).thenReturn(new ArrayList<>());
                MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/question/getQuestions");
                getResult.contentType("https://example.org/example");
                MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(getResult)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content().string("[]"));
        }

        @Test
        void testGetQuestions3() throws Exception {
                when(this.questionRepository.findAll())
                                .thenThrow(new ResourceNotFoundException("?", "?", "Field Value"));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/question/getQuestions");
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void testNewQuestion() throws Exception {
                CreateQuestionRequest createQuestionRequest = new CreateQuestionRequest();
                createQuestionRequest.setCategory("Kunst");
                createQuestionRequest.setCorrect_answer("Correcte antwoord");
                createQuestionRequest.setIncorrect_answer("Incorrecte antwoord");
                String content = (new ObjectMapper()).writeValueAsString(createQuestionRequest);
                MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/question/newQuestion");
                MockHttpServletRequestBuilder requestBuilder = postResult
                                .param("currentUser", String.valueOf((Object) null))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content);
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.questionController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
        }
}
