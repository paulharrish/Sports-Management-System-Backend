package com.project.sportsManagement.service;

import com.project.sportsManagement.dto.StudentRegistrationDto;
import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Role;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.exception.InstitutionNotFoundException;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.RoleRepository;
import com.project.sportsManagement.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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


    public Student registerStudent(StudentRegistrationDto studentRegistrationDto){

        Role studentAuthority = roleRepository.findByAuthority("STUDENT").get();
        String encodedPassword = passwordEncoder.encode(studentRegistrationDto.getPassword());
        Institution institutionCode = institutionRepository.findByInstitutionCode(studentRegistrationDto.getInstitutionCode()).orElseThrow(() -> new InstitutionNotFoundException("Institution not found with the given institution code"));
        return studentRepository.saveAndFlush(new Student(studentRegistrationDto.getFirstName(),studentRegistrationDto.getLastName(),studentRegistrationDto.getRollNo(),studentRegistrationDto.getEmail(),encodedPassword,institutionCode,studentAuthority));
    }
}
