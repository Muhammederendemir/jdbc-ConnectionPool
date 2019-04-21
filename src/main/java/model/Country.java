package model;

public class Country {
    private int countryNo;
    private String countryName;
    private String countryLanguage;

    public Country(int countryNo, String countryName, String countryLanguage) {
        this.countryNo = countryNo;
        this.countryName = countryName;
        this.countryLanguage = countryLanguage;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryNo=" + countryNo +
                ", countryName='" + countryName + '\'' +
                ", countryLanguage='" + countryLanguage + '\'' +
                '}';
    }

    public int getCountryNo() {
        return countryNo;
    }

    public void setCountryNo(int countryNo) {
        this.countryNo = countryNo;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryLanguage() {
        return countryLanguage;
    }

    public void setCountryLanguage(String countryLanguage) {
        this.countryLanguage = countryLanguage;
    }
}
