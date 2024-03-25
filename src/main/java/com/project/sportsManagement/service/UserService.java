package com.project.sportsManagement.service;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.entity.Team;
import com.project.sportsManagement.exception.UserNotFoundException;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.StudentRepository;
import com.project.sportsManagement.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstitutionRepository institutionRepository;



    @Autowired
    private TeamRepository teamRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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

    public List<Institution> getAllInstitution() {
       return institutionRepository.findAll();
    }


    public void addStudentInTeam(Student student, Team team){
        team.getTeamMembers().add(student);
    }

    public void createATeam(Team team, Student student, Set<Student> teamMembers){
        team.setCreator(student);
        teamRepository.saveAndFlush(team);
        student.getTeams().add(team);

        studentRepository.saveAndFlush(student);// saving the student enitity


        for (Student teamMember : teamMembers){
            teamMember.getTeams().add(team);
            studentRepository.saveAndFlush(teamMember);
        }
    }
}
