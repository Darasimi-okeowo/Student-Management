package com.darasimi.pagination.service;

import com.darasimi.pagination.domain.LoginResponse;
import com.darasimi.pagination.dto.LoginDto;
import com.darasimi.pagination.dto.UserDto;
import com.darasimi.pagination.entity.User;

public interface UserService {
    User save (UserDto userDto);
//    String save(UserDto userDto);
    LoginResponse loginUser(LoginDto loginDto);
}
