package com.example.tacohouse.uses;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EditForm {
    private String newUsername;
    private String currentPassword;
    private String newPassword;
    private String newConfirmPassword;
    private String newFullName;
    private String newStreet;
    private String newCity;
    private String newState;
    @Pattern(regexp = "^$|\\d{6}", message = "ZIP must be 6 digits if provided")
    private String newZip;
    @Pattern(regexp = "^$|\\d{10}", message = "Phone Number must be 10 digits if provided")
    private String newPhoneNumber;
}
