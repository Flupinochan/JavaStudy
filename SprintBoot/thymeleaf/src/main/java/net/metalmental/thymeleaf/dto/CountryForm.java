package net.metalmental.thymeleaf.dto;

public class CountryForm {
    private String selectedCountry;

    public String getSelectedCountry(){
        return selectedCountry;
    }

    public void setSelectedCountry(String selectedCountry){
        this.selectedCountry = selectedCountry;
    }
}
