package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserForm {
    @NotBlank(message = "username is required")
    @Size(min = 3, message = "mininmum 3 char required")
    private String name;

    @Email(message = "Inavlid email address")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6, message = "mininmum 6 char required")
    private String password;

    @NotBlank(message = "contact is required")
    @Size(min = 8, max = 12, message = "invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "about is required")
    private String about;

}
