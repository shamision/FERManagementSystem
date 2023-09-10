package com.project.FERMS.Security.auth;

import com.project.FERMS.Security.config.JwtService;
import com.project.FERMS.Security.user.Role;
import com.project.FERMS.Security.user.Employee;
import com.project.FERMS.Security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = Role.valueOf(request.getRole());
        var user = Employee.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .employee(user)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user  = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .employee(user)
                .build();
    }

    public List<Employee> listAllEmployees() {
        return repository.findAll();
    }

    public Employee listEmployeeById(int id) {
        return repository.findById(id).orElseThrow(() -> new IllegalStateException("The employee doesn't exist!!"));
    }

    public void deleteEmployee(int id) {
        repository.deleteById(id);
    }
}
