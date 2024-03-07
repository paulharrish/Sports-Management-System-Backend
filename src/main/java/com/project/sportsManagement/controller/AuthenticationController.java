//package com.project.sportsManagement.controller;
//
//import com.project.sportsManagement.dto.InstitutionRegistrationDto;
//import com.project.sportsManagement.dto.LoginDto;
//import com.project.sportsManagement.dto.LoginResponse;
//import com.project.sportsManagement.dto.StudentRegistrationDto;
//import com.project.sportsManagement.entity.Institution;
//import com.project.sportsManagement.entity.Student;
//import com.project.sportsManagement.service.AuthenticationService;
//import com.project.sportsManagement.service.UserService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AuthenticationController {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    @PostMapping("/auth/register/student")
//    public ResponseEntity<Student> registerUser(@RequestBody @Valid StudentRegistrationDto studentRegistrationDto){
//        Student student = authenticationService.registerStudent(studentRegistrationDto);
//        return new ResponseEntity<>(student, HttpStatus.CREATED);
//    }
//
//
//    @PostMapping("/auth/register/institution")
//    public ResponseEntity<Institution> registerInstitution(@RequestBody @Valid InstitutionRegistrationDto institutionRegistrationDto){
//        Institution institution = authenticationService.registerInstitution(institutionRegistrationDto);
//        return new ResponseEntity<>(institution,HttpStatus.CREATED);
//    }
//
//    @PostMapping("auth/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto){
//        LoginResponse loginResponse = authenticationService.login(loginDto);
//        return new ResponseEntity<>(loginResponse,HttpStatus.OK);
//    }
//
//}
