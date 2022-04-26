package nl.markheeling.quizly.controller;

import nl.markheeling.quizly.exception.ResourceNotFoundException;
import nl.markheeling.quizly.model.User;
import nl.markheeling.quizly.payload.*;
import nl.markheeling.quizly.repository.UserRepository;
import nl.markheeling.quizly.security.UserPrincipal;
import nl.markheeling.quizly.utils.FileUploadUtil;
import nl.markheeling.quizly.security.CurrentUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/user/me")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(),
                currentUser.getEmail(), currentUser.getPassword());
        return userSummary;
    }

    @PostMapping("/user/update")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest,
            @CurrentUser UserPrincipal currentUser, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

        user.setName(updateUserRequest.getName());
        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        user.setProfile_picture(fileName);

        userRepository.save(user);


        String uploadDir = "user-photos/" + currentUser.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        
        return ResponseEntity.ok(new ApiResponse(true, "User updated successfully"));

    }

    @GetMapping("/user/users")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<UserSummary> getAllUsers(@CurrentUser UserPrincipal currentUser) {
        return userRepository.findAll().stream()
                .map(user -> new UserSummary(user.getId(), user.getUsername(), user.getName(), user.getEmail(),
                        user.getPassword()))
                .collect(Collectors.toList());
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(),
                user.getCreatedAt());

        return userProfile;
    }

    // Geeft ? weer ook al bestaat de gebruiker wel
    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userRepository.delete(user);
        return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
    }
}
