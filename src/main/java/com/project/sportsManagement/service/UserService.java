package com.project.sportsManagement.service;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.exception.UserNotFoundException;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstitutionRepository institutionRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("In the user service for authentication");

       Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            return student.get();
        }
        Optional<Institution> institution = institutionRepository.findByEmail(email);
        if (institution.isPresent()){
            return institution.get();
        }
        throw new UsernameNotFoundException("No account is associated with this email");
    }


    public UserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<Student> student = studentRepository.findByEmail(username);
        if (student.isPresent()){
            return student.get();
        }
        Optional<Institution> institution = institutionRepository.findByEmail(username);
        if (institution.isPresent()){
            return institution.get();
        }
        throw new UserNotFoundException("No user is Logged In");
    }
}
