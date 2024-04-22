package com.project.sportsManagement.service;

import com.project.sportsManagement.entity.*;
import com.project.sportsManagement.exception.ParticipationException;
import com.project.sportsManagement.repo.*;
import com.project.sportsManagement.responseclasses.ParticipationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private  EventRepository eventRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private EventGameRepository eventGameRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<Event> filterByName(String filterText){
        if (filterText == null || filterText.isEmpty()){
            return eventRepository.findAll();
        }else {
            return eventRepository.search(filterText);
        }

    }

    public List<Event> filterByCollege(String filterText){
        if (filterText == null || filterText.isEmpty()){
            return eventRepository.findAll();
        }else {
            return eventRepository.searchByCollege(filterText);
        }
    }

    public List<Event> getParticipatedEvents(Student student){
        return eventRepository.getParticipatedEvents(student);
    }

    public Event getEventById(int eventId){
        return eventRepository.findById(eventId).get();
    }

    public Game getGameById(int gameId) {
        return gameRepository.findById(gameId).get();
    }

    List<ParticipationDetails> getParticipationDetailsForStudent(Student student){
        List<Participation> participations = participationRepository.findAllByStudent(student.getStudentId());
        return participations.stream()
                .map(participation -> {
                    ParticipationDetails participationDetails = new ParticipationDetails(participation.getParticipationId(),participation.getGameCode().getEventId().getEventName(),participation.getGameCode().getGameId().getGame());
                    return participationDetails;
                }).collect(Collectors.toList());
    }



    public int getTotalNoOfEventParticipants(Event event){
        int noOfParticipants = 0;
        Set<EventGame> eventGameInstances = event.getGames();
        for (EventGame eventGameInstance : eventGameInstances){
            noOfParticipants = noOfParticipants + eventGameInstance.getParticipation().size();
        }
        return noOfParticipants;
    }


    public  List<Game> getGamesInAEvent(Event event){
        Set<EventGame> eventGames = event.getGames();
        List<Game> games = new ArrayList<>();
        for (EventGame eventGame: eventGames){
                games.add(eventGame.getGameId());
        }
        return games;
    }
    @Transactional
    public void participateInASoloEvent(Student student, Set<EventGame> gameList){
        for(EventGame game : gameList){
            if (game.getGameId().isSoloParticipationAllowed()){
                Student studentManaged = studentRepository.findByStudentId(student.getStudentId());
                EventGame eventGameManaged = eventGameRepository.findById(game.getGameCode()).get();
                if (participationRepository.findByStudentStudentIdAndGameCodeGameCode(studentManaged.getStudentId(),eventGameManaged.getGameCode()).isPresent()){
                    throw new ParticipationException("You have already participated in " + eventGameManaged.getGameId().getGame());
                }
                Participation participation = new Participation(studentManaged,eventGameManaged);
                participationRepository.saveAndFlush(participation);

            }
            else {
                throw new ParticipationException("One of the games you have selected requires Team participation.Participate as a team by Creating a team or participate with existing team");
            }

        }
    }
    @Transactional
    public void participateAsTeam(Team team, Set<EventGame> gamesList) {
        Team teamManaged = teamRepository.findById(team.getTeamId()).get();

        for (EventGame game : gamesList) {
            EventGame eventGameManaged = eventGameRepository.findById(game.getGameCode()).get();

            if (participationRepository.findByTeamTeamIdAndGameCodeGameCode(teamManaged.getTeamId(), eventGameManaged.getGameCode()).isPresent()) {
                throw new ParticipationException("This team has already participated " + eventGameManaged.getGameId().getGame());
            }

            Participation participation = new Participation(teamManaged, eventGameManaged);
            participationRepository.save(participation);
        }
    }



    public void deleteEvent(Event event){
        List<Participation> allParticipation = participationRepository.findAll();
        Set<EventGame> games = event.getGames();
        for (Participation participation : allParticipation){
            if (games.contains(participation.getGameCode())){
                participationRepository.delete(participation);
            }
        }
        event.getGames().clear();
        eventRepository.delete(event);
    }
}
