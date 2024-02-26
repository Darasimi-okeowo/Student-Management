package com.darasimi.pagination.service.implementation;

import com.darasimi.pagination.domain.LoginResponse;
import com.darasimi.pagination.dto.LoginDto;
import com.darasimi.pagination.dto.UserDto;
import com.darasimi.pagination.entity.User;
import com.darasimi.pagination.repository.UserRepository;
import com.darasimi.pagination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.regex.Pattern.matches;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public User save(UserDto userDto) {
        User user = new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getFullName(),
                userDto.getEmail(),
                userDto.getRole(),
//                this.passwordEncoder.encode(userDto.getPassword())
                userDto.getPassword()
        );
//        userRepository.save(user);
        return userRepository.save(user);
//        return userRepository.save(user);
    }
//    @Override
//    public User saved(UserDto userDto) {
//        User user = new User(
//                userDto.getFirstName(),
//                userDto.getLastName(),
//                userDto.getFullName(),
//                userDto.getEmail(),
//                userDto.getRole(),
//                userDto.getPassword()
////                this.passwordEncoder.encode(userDto.getPassword())
//        );
//        return userRepository.save(user);
//    }
    @Override
    public LoginResponse loginUser(LoginDto loginDto) {
        String msg = "";
        User user1 = userRepository.findByEmail(loginDto.getEmail());
        if (user1 != null) {
            String password = loginDto.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> user = userRepository.findOneByEmailAndPassword(loginDto.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("password Not Match", false);
            }
        }else {
            return new LoginResponse("Email not exits", false);
        }

    }
}
