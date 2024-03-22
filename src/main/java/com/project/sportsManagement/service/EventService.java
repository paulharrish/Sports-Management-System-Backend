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
//        Set<Participation> participations = student.getParticipation();
//        List<Event> participatedevents = new ArrayList<>();
//        for (Participation participation  : participations){
//            Event participatedevent = participation.getGameCode().getEventId();
//            participatedevents.add(participatedevent);
//        }

        //these lines are commented out because live changes in db is not reflecting here. so everytime fetching the records from db using query method.
        return eventRepository.getParticipatedEvents(student);
    }

    public Event getEventById(int eventId){
        return eventRepository.findById(eventId).get();
    }

    public Game getGameById(int gameId) {
        return gameRepository.findById(gameId).get();
    }

    List<ParticipationDetails> getParticipationDetailsForStudent(Student student){
        List<Participation> participations = participationRepository.findAllByStudent(student);
        return participations.stream()
                .map(participation -> {
                    ParticipationDetails participationDetails = new ParticipationDetails(participation.getParticipationId(),participation.getGameCode().getEventId().getEventName(),participation.getGameCode().getGameId().getGame());
                    return participationDetails;
                }).collect(Collectors.toList());
    }

    List<Student> getEventParticipants(int eventId){
        return eventRepository.getEventParticipants(eventId);
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
    public void participateInAEvent(Student student,Set<EventGame> gameList){
        for(EventGame game : gameList){
            Student studentManaged = studentRepository.findByStudentId(student.getStudentId());
            EventGame eventGameManaged = eventGameRepository.findById(game.getGameCode()).get();
            if (participationRepository.findByStudentStudentIdAndGameCodeGameCode(studentManaged.getStudentId(),eventGameManaged.getGameCode()).isPresent()){
                throw new ParticipationException("You have already participated in " + eventGameManaged.getGameId().getGame());
            }
            Participation participation = new Participation(studentManaged,eventGameManaged);
            participationRepository.saveAndFlush(participation);
        }
    }
}
