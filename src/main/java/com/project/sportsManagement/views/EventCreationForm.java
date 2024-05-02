package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.*;
import com.project.sportsManagement.repo.EventLevelRepo;
import com.project.sportsManagement.repo.GameRepository;
import com.project.sportsManagement.service.EventService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.dom.Style;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EventCreationForm extends FormLayout {


    TextField eventName = new TextField("Event Name");
    TextField description = new TextField("Description");

    DateTimePicker startTime = new DateTimePicker("Start Time");
    DateTimePicker endTime = new DateTimePicker("End Time");

    ComboBox<EventLevel> level = new ComboBox<>("Event Level");

    MultiSelectComboBox<Game> games = new MultiSelectComboBox<>("Event Games");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    Binder<Event> binder = new Binder<>(Event.class);

    Event event;


    public EventCreationForm(EventLevelRepo eventLevelRepo, Event event , EventService eventService, GameRepository gameRepository) {

        this.event = event;
        level.setItems(eventLevelRepo.findAll());
        level.setItemLabelGenerator(EventLevel::getLevel);
        games.setItems(gameRepository.findAll());
        games.setItemLabelGenerator(Game::getGame);
        add(eventName,description,startTime,endTime,level,games,getButtonsLayout());



        binder.forField(eventName).asRequired("Event Name cannot be empty").bind(Event::getEventName,Event::setEventName);
        binder.forField(description).asRequired("Event Name cannot be empty").bind(Event::getDescription,Event::setDescription);
        binder.forField(level).asRequired("Level Cannot be empty").bind(Event::getLevel,Event::setLevel);


        binder.setBean(event);

        save.addClickListener(click ->{
            if (binder.validate().isOk()){
                eventService.createEvent(event,Timestamp.valueOf(startTime.getValue()),Timestamp.valueOf(endTime.getValue()),games.getValue());
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

}
