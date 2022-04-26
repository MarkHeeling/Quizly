package nl.markheeling.quizly.payload;

import javax.validation.constraints.*;

public class UpdateUserRequest {
  @NotBlank
  @Size(max = 40)
  private String username;

  @NotBlank
  @Size(max = 40)
  private String name;

  @NotBlank
  @Size(max = 40)
  @Email
  private String email;

  @Size(max = 60)
  private String profile_picture;
  


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getProfile_picture() {
    return profile_picture;
  }

  public void setProfile_picture(String profile_picture) {
    this.profile_picture = profile_picture;
  }
}
