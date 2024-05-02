package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.*;
import com.project.sportsManagement.exception.ParticipationException;
import com.project.sportsManagement.service.AuthenticationService;
import com.project.sportsManagement.service.EventService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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
            gamesGrid.setAllRowsVisible(true);
            gamesGrid.setItems(event.getGames());

            //participants section
            Span totalParticipantsText = new Span("Total no of participants: " + eventService.getTotalNoOfEventParticipants(event));


            //host section
            HorizontalLayout hostLayout = new HorizontalLayout();
            H4 hostHeading = new H4("Hosted by :  ");
            Paragraph venue = new Paragraph(event.getHost().getInstitutionName());
            hostLayout.add(hostHeading,venue);
            hostLayout.setPadding(false);
            hostLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);


            //address section
            VerticalLayout addressLayout = new VerticalLayout();
            H4 addressheading = new H4("Address:");
            Paragraph address = new Paragraph(event.getHost().getAddress().getInstitutionAddress()+",");
            Paragraph district = new Paragraph(event.getHost().getAddress().getDistrict()+",");
            Paragraph state = new Paragraph(event.getHost().getAddress().getState()+".");
            addressLayout.add(addressheading,address,district,state);
            addressLayout.setPadding(false);
            addressLayout.getStyle().setLineHeight("0px");





            //Buttons Section for Student login
            HorizontalLayout studentBl = new HorizontalLayout();
            Button participateBtn = new Button("Participate in this Event");
            Button TeamParticipateBtn = new Button("participate as a team");
            participateBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
            TeamParticipateBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
            Button back = new Button("Back");
            back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            back.addClickListener(click -> {
                UI.getCurrent().navigate("");
            });
            if (event.getStartTime().toInstant().isAfter(Instant.now())){
                studentBl.add(participateBtn,TeamParticipateBtn,back);
            }else {
                studentBl.add(back);
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
                        eventService.participateInASoloEvent(getCurrentUser(),comboBox.getSelectedItems());
                        participationDialogue.close();
                    }catch (ParticipationException exception){
                        ConfirmDialog confirmDialog = new ConfirmDialog();
                        confirmDialog.setHeader("Error");
                        confirmDialog.setText(exception.getMessage());
                        confirmDialog.setConfirmText("Ok");
                        confirmDialog.open();
                        confirmDialog.addConfirmListener(confirmEvent -> confirmDialog.close());
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }


                });
                hl.add(comboBox,ok);
                vl.add(headerText,hl);
                participationDialogue.add(vl);
                participationDialogue.open();
            });


            //click listener for team participation button
            TeamParticipateBtn.addClickListener(click -> {
                Dialog participationDialogue = new Dialog();
                VerticalLayout vl = new VerticalLayout();
                Span headerText = new Span("Select the games you wish to participate:");
                HorizontalLayout hl = new HorizontalLayout();

                MultiSelectComboBox<EventGame> comboBox= new MultiSelectComboBox<>();
                Set<EventGame> teamGames = new HashSet<>();
                for (EventGame gamesinEvent : event.getGames()){
                    if (!gamesinEvent.getGameId().isSoloParticipationAllowed()){
                        teamGames.add(gamesinEvent);
                    }
                }
                comboBox.setItems(teamGames);
                comboBox.setItemLabelGenerator(eventGame -> eventGame.getGameId().getGame());
                Button ok = new Button("Ok");
                ok.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


                Dialog teamSelectDialog = new Dialog("Select the team");
                HorizontalLayout hLayout = new HorizontalLayout();
                ComboBox<Team> teamComboBox = new ComboBox<>("Select a team from you team list:");
                teamComboBox.setItems(getCurrentUser().getTeams());
                teamComboBox.setItemLabelGenerator(Team::getTeamName);
                Button okbtn = new Button("Ok");
                hLayout.add(teamComboBox,okbtn);
                hLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
                teamSelectDialog.add(hLayout);


                ok.addClickListener(clickEvent -> {
                    participationDialogue.close();
                    teamSelectDialog.open();
                });


                okbtn.addClickListener(clickEvent -> {
                    try {
                        eventService.participateAsTeam(teamComboBox.getValue(),comboBox.getSelectedItems());
                        teamSelectDialog.close();
                    }catch (ParticipationException e){
                        ConfirmDialog confirmDialog = new ConfirmDialog();
                        confirmDialog.setHeader("Error");
                        confirmDialog.setText(e.getMessage());
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


            //buttonslayout for institution Login
            HorizontalLayout institutionBl = new HorizontalLayout();
            Button editBtn = new Button("Edit");
            Button viewParticipants = new Button("View Participants");
            Button addGames = new Button("Add games");
            addGames.addThemeVariants(ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_PRIMARY);
            editBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editBtn.setEnabled(false);
            addGames.setEnabled(false);
            viewParticipants.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            viewParticipants.setEnabled(false);
            institutionBl.add(editBtn,viewParticipants,addGames);
            if (authenticationService.getAuthenticatedUser().equals(event.getHost())){
                if (event.getStartTime().toInstant().isAfter(Instant.now())){
                    editBtn.setEnabled(true);
                    addGames.setEnabled(true);
                }
                viewParticipants.setEnabled(true);
            }
            addGames.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
                @Override
                public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                    Dialog addGames = new Dialog("Add games");
                    addGames.open();

                }
            });

            //changing the buttons layout based on the user login.
            if (authenticationService.getAuthenticatedUser() instanceof Student){
                add(eventName,descriptionBox,gamesBox,gamesGrid,totalParticipantsText,hostLayout,addressLayout,studentBl);
            }else if (authenticationService.getAuthenticatedUser() instanceof Institution){
                add(eventName,descriptionBox,gamesBox,gamesGrid,totalParticipantsText,hostLayout,addressLayout,institutionBl);
            }
        }

    }

    private String formatDurationText(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.toSeconds() % 60;

        return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
    }

    private Student getCurrentUser() {
        return (Student)authenticationService.getAuthenticatedUser();
    }





}

