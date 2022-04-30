package nl.markheeling.quizly.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import nl.markheeling.quizly.exception.ResourceNotFoundException;
import nl.markheeling.quizly.model.User;
import nl.markheeling.quizly.payload.UpdateUserRequest;
import nl.markheeling.quizly.repository.UserRepository;
import nl.markheeling.quizly.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCheckEmailAvailability() throws Exception {
        when(this.userRepository.existsByEmail((String) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/checkEmailAvailability")
                .param("email", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"available\":false}"));
    }

    @Test
    void testCheckEmailAvailability2() throws Exception {
        when(this.userRepository.existsByEmail((String) any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/checkEmailAvailability")
                .param("email", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"available\":true}"));
    }

    @Test
    void testCheckEmailAvailability3() throws Exception {
        when(this.userRepository.existsByEmail((String) any())).thenReturn(true);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/checkEmailAvailability");
        getResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("email", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"available\":false}"));
    }

    @Test
    void testCheckEmailAvailability4() throws Exception {
        when(this.userRepository.existsByEmail((String) any()))
                .thenThrow(new ResourceNotFoundException("?", "?", "Field Value"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/checkEmailAvailability")
                .param("email", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCheckUsernameAvailability() throws Exception {
        when(this.userRepository.existsByUsername((String) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/checkUsernameAvailability")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"available\":false}"));
    }

    @Test
    void testCheckUsernameAvailability2() throws Exception {
        when(this.userRepository.existsByUsername((String) any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/checkUsernameAvailability")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"available\":true}"));
    }

    @Test
    void testCheckUsernameAvailability3() throws Exception {
        when(this.userRepository.existsByUsername((String) any())).thenReturn(true);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/checkUsernameAvailability");
        getResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"available\":false}"));
    }

    @Test
    void testCheckUsernameAvailability4() throws Exception {
        when(this.userRepository.existsByUsername((String) any()))
                .thenThrow(new ResourceNotFoundException("?", "?", "Field Value"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/checkUsernameAvailability")
                .param("username", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setEmail("vincent@vangogh.nl");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("wachtwoord");
        user.setProfilePicture("profielfoto.png");
        user.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user.setUsername("vincent");
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(this.userRepository).delete((User) any());
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/deleteUser/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"success\":true,\"message\":\"User deleted successfully\"}"));
    }

    @Test
    void testDeleteUser2() throws Exception {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setEmail("vincent@vangogh.nl");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("wachtwoord");
        user.setProfilePicture("profielfoto.png");
        user.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user.setUsername("vincent");
        Optional<User> ofResult = Optional.of(user);
        doThrow(new ResourceNotFoundException("?", "?", "Field Value")).when(this.userRepository).delete((User) any());
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/deleteUser/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteUser3() throws Exception {
        doNothing().when(this.userRepository).delete((User) any());
        when(this.userRepository.findById((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/deleteUser/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteUser4() throws Exception {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setEmail("vincent@vangogh.nl");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("wachtwoord");
        user.setProfilePicture("profielfoto.png");
        user.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user.setUsername("vincent");
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(this.userRepository).delete((User) any());
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/deleteUser/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"success\":true,\"message\":\"User deleted successfully\"}"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(this.userRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/users");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllUsers2() throws Exception {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setEmail("vincent@vangogh.nl");
        user.setId(123L);
        user.setName("?");
        user.setPassword("wachtwoord");
        user.setProfilePicture("?");
        user.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user.setUsername("vincent");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(this.userRepository.findAll()).thenReturn(userList);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/users");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":123,\"username\":\"vincent\",\"email\":\"vincent@vangogh.nl\",\"name\":\"?\",\"password\":\"wachtwoord\"}]"));
    }

    @Test
    void testGetAllUsers3() throws Exception {
        when(this.userRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/users");
        getResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllUsers4() throws Exception {
        when(this.userRepository.findAll()).thenThrow(new ResourceNotFoundException("?", "?", "Field Value"));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/users");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser", String.valueOf((Object) null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetAllUsers5() throws Exception {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setEmail("vincent@vangogh.nl");
        user.setId(123L);
        user.setName("?");
        user.setPassword("wachtwoord");
        user.setProfilePicture("?");
        user.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user.setUsername("vincent");

        User user1 = new User();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setCreatedAt(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant());
        user1.setEmail("vincent@vangogh.nl");
        user1.setId(123L);
        user1.setName("?");
        user1.setPassword("wachtwoord");
        user1.setProfilePicture("?");
        user1.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setUpdatedAt(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant());
        user1.setUsername("vincent");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user);
        when(this.userRepository.findAll()).thenReturn(userList);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/users");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":123,\"username\":\"vincent\",\"email\":\"vincent@vangogh.nl\",\"name\":\"?\",\"password\":\"wachtwoord\"},{\"id"
                                        + "\":123,\"username\":\"vincent\",\"email\":\"vincent@vangogh.nl\",\"name\":\"?\",\"password\":\"wachtwoord\"}]"));
    }

    @Test
    void testGetCurrentUser() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/me");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"username\":null,\"email\":null,\"name\":null,\"password\":null}"));
    }

    @Test
    void testGetCurrentUser2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/me");
        getResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"username\":null,\"email\":null,\"name\":null,\"password\":null}"));
    }

    @Test
    void testGetProfilePicture() throws Exception {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setEmail("vincent@vangogh.nl");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("wachtwoord");
        user.setProfilePicture("profielfoto.png");
        user.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user.setUsername("vincent");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/me/profile-picture");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("profielfoto.png"));
    }

    @Test
    void testGetProfilePicture2() throws Exception {
        when(this.userRepository.findById((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/me/profile-picture");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser", String.valueOf((Object) null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetProfilePicture3() throws Exception {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setEmail("vincent@vangogh.nl");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("wachtwoord");
        user.setProfilePicture("profielfoto.png");
        user.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user.setUsername("vincent");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/user/me/profile-picture");
        getResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("currentUser", String.valueOf((Object) null));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("profielfoto.png"));
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setEmail("vincent@vangogh.nl");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("wachtwoord");
        user.setProfilePicture("profielfoto.png");
        user.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user.setUsername("vincent");
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setCreatedAt(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant());
        user1.setEmail("vincent@vangogh.nl");
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("wachtwoord");
        user1.setProfilePicture("profielfoto.png");
        user1.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setUpdatedAt(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant());
        user1.setUsername("vincent");
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("vincent@vangogh.nl");
        updateUserRequest.setName("Name");
        updateUserRequest.setUsername("vincent");
        String content = (new ObjectMapper()).writeValueAsString(updateUserRequest);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/user/update");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("currentUser", String.valueOf((Object) null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"success\":true,\"message\":\"User updated successfully\"}"));
    }

    @Test
    void testUpdateUser2() throws Exception {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setEmail("vincent@vangogh.nl");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("wachtwoord");
        user.setProfilePicture("profielfoto.png");
        user.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user.setUsername("vincent");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.save((User) any())).thenThrow(new ResourceNotFoundException("?", "?", "Field Value"));
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("vincent@vangogh.nl");
        updateUserRequest.setName("Name");
        updateUserRequest.setUsername("vincent");
        String content = (new ObjectMapper()).writeValueAsString(updateUserRequest);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/user/update");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("currentUser", String.valueOf((Object) null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    void testUpdateUser3() throws Exception {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setCreatedAt(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setEmail("vincent@vangogh.nl");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("wachtwoord");
        user.setProfilePicture("profielfoto.png");
        user.setRoles(new HashSet<>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setUpdatedAt(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user.setUsername("vincent");
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.findById((Long) any())).thenReturn(Optional.empty());

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("vincent@vangogh.nl");
        updateUserRequest.setName("Name");
        updateUserRequest.setUsername("vincent");
        String content = (new ObjectMapper()).writeValueAsString(updateUserRequest);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/user/update");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("currentUser", String.valueOf((Object) null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

