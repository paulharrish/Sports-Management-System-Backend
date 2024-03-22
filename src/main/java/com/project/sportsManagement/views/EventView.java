package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Event;
import com.project.sportsManagement.entity.EventGame;
import com.project.sportsManagement.entity.Student;
import com.project.sportsManagement.exception.ParticipationException;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.EventService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

@PermitAll
@Route(value = "event",layout = MainLayout.class)
public class EventView extends VerticalLayout implements HasUrlParameter<Integer> {

    private final AuthenticationService authenticationService;
    private EventService eventService;




    public EventView(@Autowired EventService eventService, @Autowired AuthenticationService auth) {
        this.eventService = eventService;
        this.authenticationService = auth;
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
            Button back = new Button("Back");
            back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            back.addClickListener(click -> {
                UI.getCurrent().navigate("");
            });
            if (event.getStartTime().toInstant().isAfter(Instant.now())){
                bl.add(participateBtn,back);
            }else {
                bl.add(back);
            }

            //click listener for participating button
            participateBtn.addClickListener(click -> {
                Dialog participationDialogue = new Dialog();
                VerticalLayout vl = new VerticalLayout();
                Span headerText = new Span("Select the games you wish to participate:");
                HorizontalLayout hl = new HorizontalLayout();

                MultiSelectComboBox<EventGame> comboBox= new MultiSelectComboBox<>();
                comboBox.setItems(event.getGames());
                comboBox.setItemLabelGenerator(eventGame -> eventGame.getGameId().getGame());
                Button ok = new Button("Ok");
                ok.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                ok.addClickListener(clickEvent -> {
                    try{
                        eventService.participateInAEvent(getCurrentUser(),comboBox.getSelectedItems());
                        participationDialogue.close();
                    }catch (ParticipationException exception){
                        ConfirmDialog confirmDialog = new ConfirmDialog();
                        confirmDialog.setHeader("Already Participated");
                        confirmDialog.setText(exception.getMessage());
                        confirmDialog.setConfirmText("Ok");
                        confirmDialog.open();
                        confirmDialog.addConfirmListener(confirmEvent -> confirmDialog.close());
                    }


                });
                hl.add(comboBox,ok);
                vl.add(headerText,hl);
                participationDialogue.add(vl);
                participationDialogue.open();
            });

            add(eventName,descriptionBox,gamesBox,gamesGrid,totalParticipantsText,bl);
        }

    }

    private Student getCurrentUser() {
        return (Student)authenticationService.getAuthenticatedUser();
    }



}

