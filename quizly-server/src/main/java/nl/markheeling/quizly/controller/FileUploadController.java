package nl.markheeling.quizly.controller;

import nl.markheeling.quizly.repository.UserRepository;
import nl.markheeling.quizly.security.CurrentUser;
import nl.markheeling.quizly.util.FileUtil;
import nl.markheeling.quizly.model.User;

import nl.markheeling.quizly.exception.ResourceNotFoundException;
import nl.markheeling.quizly.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
            @CurrentUser UserPrincipal currentUser) {
        try {
            String fileName = file.getOriginalFilename();
            FileUtil.filePath.resolve(fileName).toFile().delete();
            Files.copy(file.getInputStream(), FileUtil.filePath.resolve(fileName));

            User user = userRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

            user.setProfilePicture(fileName);

            userRepository.save(user);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<String[]> getListFiles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new java.io.File(FileUtil.folderPath).list());
    }

}