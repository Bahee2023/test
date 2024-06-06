package com.web.registry.entities;

import com.web.registry.dto.UserDTO;
import com.web.registry.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String email;

    private String password;

    private UserRole userRole;

    private byte[] img;

    private boolean mfa;

    private String secret;

}
