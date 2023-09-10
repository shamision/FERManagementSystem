package com.project.FERMS.Security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByEmail(String email);
}
