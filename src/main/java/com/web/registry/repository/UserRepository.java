package com.web.registry.repository;

import com.web.registry.entities.User;
import com.web.registry.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByEmail(String email);

    User findByUserRole(UserRole userRole);
}
