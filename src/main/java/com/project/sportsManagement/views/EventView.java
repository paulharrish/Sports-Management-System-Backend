package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.EventGame;
import com.project.sportsManagement.entity.Game;
import com.project.sportsManagement.service.EventService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@PermitAll
@Route(value = "event",layout = MainLayout.class)
public class EventView extends VerticalLayout implements HasUrlParameter<Integer> {

    private EventService eventService;



    public EventView(@Autowired EventService eventService) {
        this.eventService = eventService;
        getStyle().set("gap","40px");
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer eventId) {
        Event event = eventService.getEventById(eventId);
        if (event != null){
            H1 eventName = new H1(event.getEventName());
            eventName.getStyle().set("color","#223349");

            //Description Section
            VerticalLayout descriptionBox = new VerticalLayout();
            H3 descriptionHeading = new H3("About Event");
            Paragraph description = new Paragraph(event.getDescription());
            descriptionBox.add(descriptionHeading,description);
            descriptionBox.setPadding(false);
            descriptionBox.setSpacing(false);

            //Games Section
            VerticalLayout gamesBox = new VerticalLayout();
            H3 gamesHeading = new H3("Games");
            HorizontalLayout games = new HorizontalLayout();
            games.getStyle().set("flex-wrap", "wrap");
            for (EventGame eventGame : event.getGames()){
                Span game = new Span(eventGame.getGameId().getGame());
                game.getStyle().setBackgroundColor("#f4f5f7 ");
                game.getStyle().setPadding("10px");
                game.getStyle().set("border-radius","50px");
                game.getStyle().set("font-weight","600");
                game.getStyle().set("font-size","15px");
                games.add(game);
            }
            gamesBox.add(gamesHeading,games);
            gamesBox.setPadding(false);
            gamesBox.getStyle().set("gap","20px");


            //grid Section
            Grid<EventGame> gamesGrid = new Grid<>(EventGame.class);
            gamesGrid.setColumns("gameCode");
            gamesGrid.addColumn(eventGame -> eventGame.getGameId().getGame()).setHeader("Games");
            gamesGrid.addColumn(eventGame -> eventGame.getParticipation().size()).setHeader("Participants");
            gamesGrid.getColumns().forEach(col -> col.setAutoWidth(true));
            gamesGrid.setMaxHeight("200px");
            gamesGrid.setItems(event.getGames());

            //participants section
            Span totalParticipantsText = new Span("Total no of participants: " + eventService.getTotalNoOfEventParticipants(event));


            //Buttons Section
            HorizontalLayout bl = new HorizontalLayout();
            Button participateBtn = new Button("Participate in this Event");
            participateBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
            bl.add(participateBtn);


            //click listener for participating button
            participateBtn.addClickListener(click -> {
                Dialog participationDialogue = new Dialog();
                VerticalLayout vl = new VerticalLayout();
                Span headerText = new Span("Select the games you wish to participate:");
                HorizontalLayout hl = new HorizontalLayout();

                MultiSelectComboBox<Game> comboBox= new MultiSelectComboBox<>();
                comboBox.setItems(eventService.getGamesInAEvent(event));
                comboBox.setItemLabelGenerator(Game::getGame);
                Button ok = new Button("Ok");
                ok.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                hl.add(comboBox,ok);
                vl.add(headerText,hl);
                participationDialogue.add(vl);
                participationDialogue.open();
            });

            add(eventName,descriptionBox,gamesBox,gamesGrid,totalParticipantsText,bl);
        }

    }




}

