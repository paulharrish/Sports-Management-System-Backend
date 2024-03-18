package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Location;
import com.project.sportsManagement.enums.States;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import javassist.Loader;

import java.util.Collection;
import java.util.Collections;

public class LocationField extends CustomField<Location> {

    private final TextArea institutionAddress = new TextArea("Institution Address");
    private final TextField district = new TextField("District");
    private final TextField state = new TextField("State");


    public LocationField() {
        institutionAddress.setWidth("100%");
        add(institutionAddress,district,state);

    }

    @Override
    protected Location generateModelValue() {
        return new Location(institutionAddress.getValue(),district.getValue(),state.getValue());
    }

    @Override
    protected void setPresentationValue(Location location) {
        institutionAddress.setValue(location.getInstitutionAddress());
        district.setValue(location.getDistrict());
        state.setValue(location.getState());
    }


}
