package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.EventGame;
import com.project.sportsManagement.entity.EventLevel;
import com.project.sportsManagement.entity.Institution;
import com.project.sportsManagement.repo.EventLevelRepo;
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

public class EventCreationForm extends FormLayout {


    TextField eventName = new TextField("Event Name");
    TextField description = new TextField("Description");

    DateTimePicker startTime = new DateTimePicker("Start Time");
    DateTimePicker endTime = new DateTimePicker("End Time");

    ComboBox<EventLevel> level = new ComboBox<>("Event Level");

    MultiSelectComboBox<EventGame> games = new MultiSelectComboBox<>("Event Games");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    Binder<Event> binder = new Binder<>(Event.class);

    Event event;


    public EventCreationForm(EventLevelRepo eventLevelRepo, Event event , EventService eventService, Institution institution) {

        add(eventName,description,startTime,endTime,level,getButtonsLayout());
        level.setItems(eventLevelRepo.findAll());
        level.setItemLabelGenerator(level -> level.getLevel());
        games.setItems();
        this.event = event;


        binder.forField(eventName).bind(Event::getEventName,Event::setEventName);
        binder.forField(description).bind(Event::getDescription,Event::setDescription);


        binder.setBean(event);

        save.addClickListener(click ->{
            if (binder.validate().isOk()){
                eventService.createEvent(event);
                binder.setBean(new Event(institution));
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
