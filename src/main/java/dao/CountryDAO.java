package dao;

import model.Country;

import javax.sql.DataSource;

public interface CountryDAO {
   // DataSource setConnection(DataSource dataSource);
    public Country getCountryId(int countryId);
    public void updateCountry(Country country);
    public void saveCountry(Country country);
    public void deleteCountry(int countryId);
}
