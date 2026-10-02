package com.autoservice.backend.repository;

import com.autoservice.backend.enums.Role;
import com.autoservice.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.role IN :role")
    List<User> findUserByRole(@Param("role")List<Role> roles);
}
