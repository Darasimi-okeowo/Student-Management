package com.darasimi.pagination.dto;

import com.darasimi.pagination.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;


public class UserDto {
    @NotBlank(message = "Enter your first name")
    private String firstName;
    @NotBlank(message = "Enter your last name")
    private String lastName;
    private String fullName;
    @NotBlank(message = "Enter your email")
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @NotBlank(message = "Enter your password")
    private String password;
    @NotBlank(message = "Confirm your password")
    private String confirmPassword;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UserDto(String firstName, String lastName, String fullName, String email, UserRole role, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.password = password;
        this.confirmPassword = confirmPassword;

    }
}
