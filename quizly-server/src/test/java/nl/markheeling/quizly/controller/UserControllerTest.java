package nl.markheeling.quizly.controller;

import nl.markheeling.quizly.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@Service
class UserControllerTest {

    @Autowired
    private UserRepository repository;

    @Test
    void getCurrentUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void getProfilePicture() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void checkUsernameAvailability() {
    }

    @Test
    void checkEmailAvailability() {
    }

    @Test
    void deleteUser() {
    }
}