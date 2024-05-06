package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.EventGame;
import com.project.sportsManagement.entity.EventLevel;
import com.project.sportsManagement.entity.Game;
import com.project.sportsManagement.repo.EventLevelRepo;
import com.project.sportsManagement.repo.GameRepository;
import com.project.sportsManagement.service.EventService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import com.vaadin.flow.dom.Style;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EventForm extends FormLayout {


    private final boolean editMode;


    TextField eventName = new TextField("Event Name");
    TextArea description = new TextArea("Description");

    DateTimePicker startTime = new DateTimePicker("Start Time");
    DateTimePicker endTime = new DateTimePicker("End Time");

    ComboBox<EventLevel> level = new ComboBox<>("Event Level");

    MultiSelectComboBox<Game> games = new MultiSelectComboBox<>("Event Games");

    Span warningtxt = new Span("Games selected once cannot be removed.");


    Button save = new Button("Save");

    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    Binder<Event> binder = new Binder<>(Event.class);

    Event event;







    public EventForm(EventLevelRepo eventLevelRepo, Event event , EventService eventService, GameRepository gameRepository,Boolean editMode) {
        this.editMode = editMode;
        this.event = event;
        level.setItems(eventLevelRepo.findAll());
        level.setItemLabelGenerator(EventLevel::getLevel);
        games.setItems(gameRepository.findAll());
        games.setItemLabelGenerator(Game::getGame);
        warningtxt.getStyle().setColor("red");
        warningtxt.getStyle().setFontSize("12px");
        if (!editMode){
            add(eventName,description,startTime,endTime,level,games,warningtxt,getButtonsLayout());
        }else {
            add(eventName,description,startTime,endTime,level,getButtonsLayout());
        }




        binder.forField(eventName).asRequired("Event Name cannot be empty").bind(Event::getEventName,Event::setEventName);
        binder.forField(description).asRequired("Event Name cannot be empty").bind(Event::getDescription,Event::setDescription);
        binder.forField(level).asRequired("Level Cannot be empty").bind(Event::getLevel,Event::setLevel);
        Binder.Binding<Event,Timestamp> startTimeBinding =  binder.forField(startTime)
                .withConverter(dateTime -> dateTime == null ? null : Timestamp.valueOf(dateTime),
                        timestamp -> timestamp == null ? null : timestamp.toLocalDateTime()).asRequired("Start time cannot be Empty").withValidator(new AbstractValidator<Timestamp>("Start time must be in the future") {
                    @Override
                    public ValidationResult apply(Timestamp value, ValueContext context) {
                        if (value == null || value.toLocalDateTime().isAfter(LocalDateTime.now())) {
                            return ValidationResult.ok();
                        } else {
                            return ValidationResult.error("Start time must be in the future");
                        }
                    }
                })
                .bind(Event::getStartTime, Event::setStartTime);

        Binder.Binding<Event,Timestamp> endTimeBinder = binder.forField(endTime)
                .withConverter(dateTime -> dateTime == null ? null : Timestamp.valueOf(dateTime),
                        timestamp -> timestamp == null ? null : timestamp.toLocalDateTime()).asRequired("End Time cannot be empty").withValidator(new AbstractValidator<Timestamp>("End time must be after start time") {
                    @Override
                    public ValidationResult apply(Timestamp value, ValueContext context) {
                        LocalDateTime startDateTime = startTime.getValue();
                        if (value == null || (startDateTime != null && value.toLocalDateTime().isAfter(startDateTime))) {
                            return ValidationResult.ok();
                        } else {
                            return ValidationResult.error("End time must be after start time");
                        }
                    }
                })
                .bind(Event::getEndTime, Event::setEndTime);
        startTime.addValueChangeListener(e -> endTimeBinder.validate());
        if (!editMode){
            binder.forField(games).withConverter(this::gamesToEventGames,this::eventGamesToGames).bind(Event::getGames,Event::setGames);

        }

        binder.setBean(event);

        if (editMode){
            save.addClickListener(clickEvent -> {
                if (binder.validate().isOk()){
                    eventService.modifyEvent(event);
                    UI.getCurrent().navigate("");
                }
            });

        }else {
            save.addClickListener(click ->{
                if (binder.validate().isOk()){
                    eventService.createEvent(event);
                    UI.getCurrent().navigate("");
                }
            });

        }
        close.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> clickEvent) {
                UI.getCurrent().navigate("");
            }
        });

    }



    private HorizontalLayout getButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        HorizontalLayout buttonLayout =  new HorizontalLayout(save, delete, close);
        buttonLayout.getStyle().set("margin-top","20px");
        buttonLayout.getStyle().setJustifyContent(Style.JustifyContent.SPACE_BETWEEN);
        return buttonLayout;
    }

    private Set<EventGame> gamesToEventGames(Set<Game> games) {
        if (games == null) {
            return new HashSet<>();
        }
        return games.stream()
                .map(game -> new EventGame(event, game)) // Assuming EventGame constructor accepts eventId and gameId
                .collect(Collectors.toSet());
    }
    private Set<Game> eventGamesToGames(Set<EventGame> eventGames) {
        if (eventGames == null) {
            return new HashSet<>();
        }
        return eventGames.stream()
                .map(eventGame -> eventGame.getGameId())
                .filter(game -> game != null)
                .collect(Collectors.toSet());
    }


}
