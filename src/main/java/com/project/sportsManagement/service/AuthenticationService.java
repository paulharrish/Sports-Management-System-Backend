package com.project.sportsManagement.service;

import com.project.sportsManagement.dto.LoginDto;
import com.project.sportsManagement.dto.LoginResponse;
import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Location;
import com.project.sportsManagement.entity.Role;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.exception.UserNotFoundException;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.LocationRepository;
import com.project.sportsManagement.repo.RoleRepository;
import com.project.sportsManagement.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationRepository locationRepository;


    public void registerStudent(Student student){

        Role studentAuthority = roleRepository.findByAuthority("STUDENT").get();
        String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setAuthority(studentAuthority);
        student.setPassword(encodedPassword);
        student.setCreatedAt(new Date());
        student.setUpdatedAt(new Date());
        studentRepository.saveAndFlush(student);
    }


    public void registerInstitution(Institution institution){


        Role institutionAuthority = roleRepository.findByAuthority("INSTITUTION").get();
        String encodedPassword = passwordEncoder.encode(institution.getPassword());
        institution.setAuthority(institutionAuthority);
        institution.setPassword(encodedPassword);
        institution.setCreatedAt(new Date());
        institution.setUpdatedAt(new Date());
        Location location = institution.getAddress();
        locationRepository.saveAndFlush(location);
        institutionRepository.saveAndFlush(institution);

    }


    public LoginResponse login(LoginDto loginDto) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            String token = tokenService.generateJwt(auth);

            Optional<Student> studentOptional = studentRepository.findByEmail(loginDto.getEmail());
            if (studentOptional.isPresent()) {
                return new LoginResponse(studentOptional.get(), token);
            }


            Optional<Institution> institutionOptional = institutionRepository.findByEmail(loginDto.getEmail());
            if (institutionOptional.isPresent()) {
                return new LoginResponse(institutionOptional.get(), token);
            }

            throw new UserNotFoundException("User not found for the given email");

        } catch (AuthenticationException e) {
            String message = e.getLocalizedMessage();
            throw new UserNotFoundException(message);
        }
    }

    public UserDetails getAuthenticatedUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            return (UserDetails) context.getAuthentication().getPrincipal();
        }
        return null;
    }

    public void modifyStudentDetails(Student student) {
        student.setUpdatedAt(new Date());
        studentRepository.saveAndFlush(student);

    }

    public void modifyInstitutionDetails(Institution institution) {
        institution.setUpdatedAt(new Date());
        institutionRepository.saveAndFlush(institution);
    }
}
