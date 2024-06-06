package com.web.registry.service.user;

import com.web.registry.dto.SignupDTO;
import com.web.registry.dto.UserDTO;
import com.web.registry.entities.User;
import com.web.registry.enums.UserRole;
import com.web.registry.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        try {
            User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
            if(adminAccount == null){
                User user = new User();
                user.setUserRole(UserRole.ADMIN);
                user.setEmail("admin@test.com");
                user.setName("admin");
                user.setPassword(new BCryptPasswordEncoder().encode("admin"));
                userRepository.save(user);
            }
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            // For example:
            e.printStackTrace();
            // You can also throw a custom exception or handle it based on your application requirements
        }
    }


    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setUserRole(UserRole.User);
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setName(createdUser.getName());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setUserRole(createdUser.getUserRole());
        return userDTO;
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email)!=null;
    }
}
