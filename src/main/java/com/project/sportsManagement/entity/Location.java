package com.project.sportsManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int locationId;
    @Column(name = "institution_address")
    private String institutionAddress;

    @Column(name = "district")
    private String district;

    @Column(name = "state")
    private String state;

    public Location() {
    }

    public Location(String institutionAddress, String district, String state) {
        this.institutionAddress = institutionAddress;
        this.district = district;
        this.state = state;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getInstitutionAddress() {
        return institutionAddress;
    }

    public void setInstitutionAddress(String institutionAddress) {
        this.institutionAddress = institutionAddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
