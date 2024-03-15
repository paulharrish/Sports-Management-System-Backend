package com.project.sportsManagement.views;

import com.project.sportsManagement.entity.Location;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class LocationField extends CustomField<Location> {

    private TextArea institutionAddress = new TextArea("Institution Address");
    private ComboBox<String> district = new ComboBox<>("District");
    private ComboBox<String> state = new ComboBox<>("State");


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
