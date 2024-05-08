package com.project.sportsManagement.service;

import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.entity.Participation;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.entity.Team;
import com.project.sportsManagement.exception.UserNotFoundException;
import com.project.sportsManagement.repo.InstitutionRepository;
import com.project.sportsManagement.repo.ParticipationRepository;
import com.project.sportsManagement.repo.StudentRepository;
import com.project.sportsManagement.repo.TeamRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private ParticipationRepository participationRepository;


    @Autowired
    JavaMailSender javaMailSender;


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
    @Transactional
    public void createATeam(Team team, Student student, Set<Student> teamMembers) throws MessagingException, UnsupportedEncodingException {
        team.setCreator(student);
        teamRepository.saveAndFlush(team);

        Set<Student> teamMembersWithCreator = new HashSet<>();
        teamMembersWithCreator.add(student);
        teamMembersWithCreator.addAll(teamMembers);

//        student.getTeams().add(team);
//        studentRepository.saveAndFlush(student);// saving the student entity
//        teamRepository.saveAndFlush(team);

          for (Student teamMember : teamMembersWithCreator){
              sendteammail(teamMember,team,student);
              teamMember.getTeams().add(team);
              studentRepository.saveAndFlush(teamMember);
          }


//        for (Student teamMember : teamMembers){
//            team.getTeamMembers().add(teamMember);
//            teamRepository.saveAndFlush(team);
//        }
    }

    private void sendteammail(Student teamMember,Team team,Student student) throws MessagingException, UnsupportedEncodingException {
        String email = teamMember.getEmail();
        String subject = " Team Registration - Details.";
        String sender = "SportsHub - A Games Repository";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("eventhub72@gmail.com", sender);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);


        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><body>");
        htmlContent.append("<h2>Team Registration Details</h2>");
        htmlContent.append("<h3>Dear "+ teamMember.getFirstName() + ",</h3>");
        htmlContent.append("<p style='font-size: 16px; line-height: 1.6;'>We hope this email finds you in high spirits. We are thrilled to announce that you've been selected to join the <strong style='color: #0066cc;'>"+team.getTeamName()+"</strong> team, formed by <strong style='color: #0066cc;'>"+student.getFirstName()+"</strong>.</p>");
        htmlContent.append("<p style='font-size: 16px; line-height: 1.6;'>Your inclusion in our team is a testament to your skill and dedication to the sport. We believe your talent will strengthen our team and contribute to our shared success.</p>");
        htmlContent.append("<p style='font-size: 16px; line-height: 1.6;'>We're excited to have you on board and look forward to achieving great things together. Welcome to the <strong>"+team.getTeamName()+"</strong> family!</p>");
        messageHelper.setText(htmlContent.toString(), true);
        javaMailSender.send(message);

    }

    public List<Team> getAllTeamsOfAUser(Student student){
        return studentRepository.getAllTeams(student);
    }


    public List<Participation> getSoloParticipationsOfAStudent(int StudentId){
        List<Participation> allParticipations = participationRepository.findAllByStudent(StudentId);
        List<Participation> soloParticipations = new ArrayList<>();
        for (Participation participation : allParticipations){
            if (participation.getTeam() == null){
                soloParticipations.add(participation);
            }
        }
        return soloParticipations;
    }

    public List<Participation> getTeamParticipationsOfAStudent(int StudentId){
        List<Participation> allParticipations = participationRepository.findAllByStudent(StudentId);
        List<Participation> teamParticipations = new ArrayList<>();
        for (Participation participation : allParticipations){
            if (participation.getTeam() != null){
                teamParticipations.add(participation);
            }
        }
        return teamParticipations;
    }

}
