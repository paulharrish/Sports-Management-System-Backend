package com.project.sportsManagement.enums;

public enum States {
    ANDHRA_PRADESH("Andhra Pradesh"),
    ARUNACHAL_PRADESH("Arunachal Pradesh"),
    ASSAM("Assam"),
    BIHAR("Bihar"),
    CHHATTISGARH("Chhattisgarh"),
    GOA("Goa"),
    GUJARAT("Gujarat"),
    HARYANA("Haryana"),
    HIMACHAL_PRADESH("Himachal Pradesh"),
    JAMMU_AND_KASHMIR("Jammu and Kashmir"),
    JHARKHAND("Jharkhand"),
    KARNATAKA("Karnataka"),
    KERALA("Kerala"),
    MADHYA_PRADESH("Madhya Pradesh"),
    MAHARASHTRA("Maharashtra"),
    MANIPUR("Manipur"),
    MEGHALAYA("Meghalaya"),
    MIZORAM("Mizoram"),
    NAGALAND("Nagaland"),
    ODISHA("Odisha"),
    PUNJAB("Punjab"),
    RAJASTHAN("Rajasthan"),
    SIKKIM("Sikkim"),
    TAMIL_NADU("Tamil Nadu"),
    TELANGANA("Telangana"),
    TRIPURA("Tripura"),
    UTTARAKHAND("Uttarakhand"),
    UTTAR_PRADESH("Uttar Pradesh"),
    WEST_BENGAL("West Bengal"),
    ANDAMAN_AND_NICOBAR_ISLANDS("Andaman and Nicobar Islands"),
    CHANDIGARH("Chandigarh"),
    DELHI("Delhi"),
    LAKSHADWEEP("Lakshadweep"),
    PUDUCHERRY("Puducherry");

    private final String label;

    States(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
