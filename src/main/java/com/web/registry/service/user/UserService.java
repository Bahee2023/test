package com.web.registry.service.user;

import com.web.registry.dto.SignupDTO;
import com.web.registry.dto.UserDTO;

public interface UserService {
    UserDTO createUser(SignupDTO signupDTO);

    boolean hasUserWithEmail(String email);
}
