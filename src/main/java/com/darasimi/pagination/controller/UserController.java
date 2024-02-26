package com.darasimi.pagination.controller;

import com.darasimi.pagination.dto.UserDto;
import com.darasimi.pagination.entity.User;
import com.darasimi.pagination.repository.UserRepository;
import com.darasimi.pagination.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords don't match");
        }
        try {
            // Concatenate first name and last name to form full name
            String fullName = userDto.getFirstName() + " " + userDto.getLastName();
            userDto.setFullName(fullName);

            // Save the user with the updated full name to the database
            User savedUser = userService.save(userDto);

            return ResponseEntity.ok(savedUser);
        } catch (DataIntegrityViolationException e) {
            // Handle data integrity violation (e.g., unique constraint violation)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserDto userDto, BindingResult result){
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user.getPassword().equals(userDto.getPassword()))
            return ResponseEntity.ok(user);
        return (ResponseEntity<?>) ResponseEntity.internalServerError();
    }
}
