package dao;

import model.Country;

public class Test
{
    public static void main(String[] args) {
        //System.out.println("dfg");
        Country country=new Country(4,"Azerbaycan","Türkçe");
       // System.out.println("wsdfsg");
        CountryDAOImpl countryDAO=new CountryDAOImpl();
        countryDAO.saveCountry(country);
        Country country1=countryDAO.getCountryId(1);
        System.out.println(country1);
    }

}
