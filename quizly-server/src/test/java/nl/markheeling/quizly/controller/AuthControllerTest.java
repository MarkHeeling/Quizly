package nl.markheeling.quizly.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.markheeling.quizly.exception.AppException;
import nl.markheeling.quizly.payload.LoginRequest;
import nl.markheeling.quizly.payload.SignUpRequest;
import nl.markheeling.quizly.repository.RoleRepository;
import nl.markheeling.quizly.repository.UserRepository;
import nl.markheeling.quizly.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = { AuthController.class })
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
        @Autowired
        private AuthController authController;

        @MockBean
        private AuthenticationManager authenticationManager;

        @MockBean
        private JwtTokenProvider jwtTokenProvider;

        @MockBean
        private PasswordEncoder passwordEncoder;

        @MockBean
        private RoleRepository roleRepository;

        @MockBean
        private UserRepository userRepository;

        @Test
        void testAuthenticateUser() throws Exception {
                when(this.jwtTokenProvider.generateToken((org.springframework.security.core.Authentication) any()))
                                .thenReturn("ABC123");
                when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setPassword("wachtwoord");
                loginRequest.setUsernameOrEmail("vincentvangogh");
                String content = (new ObjectMapper()).writeValueAsString(loginRequest);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content);
                MockMvcBuilders.standaloneSetup(this.authController)
                                .build()
                                .perform(requestBuilder)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string("{\"accessToken\":\"ABC123\",\"tokenType\":\"Bearer\"}"));
        }

        @Test
        void testAuthenticateUser2() throws Exception {
                when(this.jwtTokenProvider.generateToken((org.springframework.security.core.Authentication) any()))
                                .thenReturn("ABC123");
                when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                                .thenThrow(new AppException("An error occurred"));

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setPassword("wachtwoord");
                loginRequest.setUsernameOrEmail("vincentvangogh");
                String content = (new ObjectMapper()).writeValueAsString(loginRequest);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content);
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.authController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
        }

        @Test
        void testRegisterUser() throws Exception {
                when(this.userRepository.existsByEmail((String) any())).thenReturn(true);
                when(this.userRepository.existsByUsername((String) any())).thenReturn(true);

                SignUpRequest signUpRequest = new SignUpRequest();
                signUpRequest.setEmail("vincent@vangogh.nl");
                signUpRequest.setName("Vincent");
                signUpRequest.setPassword("wachtwoord");
                signUpRequest.setUsername("vanGogh");
                String content = (new ObjectMapper()).writeValueAsString(signUpRequest);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content);
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.authController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string("{\"success\":false,\"message\":\"Deze gebruikersnaam bestaat al\"}"));
        }

        @Test
        void testRegisterUser2() throws Exception {
                when(this.userRepository.existsByEmail((String) any())).thenReturn(true);
                when(this.userRepository.existsByUsername((String) any())).thenReturn(false);

                SignUpRequest signUpRequest = new SignUpRequest();
                signUpRequest.setEmail("vincent@vangogh.nl");
                signUpRequest.setName("vincent");
                signUpRequest.setPassword("wachtwoord");
                signUpRequest.setUsername("vanGogh");
                String content = (new ObjectMapper()).writeValueAsString(signUpRequest);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content);
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.authController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(MockMvcResultMatchers.content()
                                                .string("{\"success\":false,\"message\":\"Dit emailadres is al in gebruik\"}"));
        }

        @Test
        void testRegisterUser3() throws Exception {
                when(this.userRepository.existsByEmail((String) any()))
                                .thenThrow(new AppException("An error occurred"));
                when(this.userRepository.existsByUsername((String) any()))
                                .thenThrow(new AppException("An error occurred"));

                SignUpRequest signUpRequest = new SignUpRequest();
                signUpRequest.setEmail("vincent@vangogh.nl");
                signUpRequest.setName("vincent");
                signUpRequest.setPassword("wachtwoord");
                signUpRequest.setUsername("vanGogh");
                String content = (new ObjectMapper()).writeValueAsString(signUpRequest);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content);
                ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.authController)
                                .build()
                                .perform(requestBuilder);
                actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
        }
}
