package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import model.Country;
import org.apache.log4j.Logger;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class CountryDAOImpl implements CountryDAO {
    static Logger logger = Logger.getLogger(CountryDAO.class);
  /*  private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
*/
    private final String GET_COUNTRY="SELECT * FROM country Where countryNo=?";
    private final String SAVE_COUNTRY="INSERT INTO country(countryNo,countryName,countryLanguage) VALUES(?,?,?)";
    private final String UPDATE_COUNTRY="UPDATE country SET countryName=?,countryLanguage=? WHERE countryNo=? ";
    private final String DELETE_COUNTRY="  DELETE FROM country WHERE countryNo=?";
    private DataSource dataSource;
    public CountryDAOImpl() {
        dataSource=setConnection();
    }

    public DataSource setConnection(){
        if(dataSource == null)
        {
            HikariConfig config = new HikariConfig();
            //config.setDriverClassName("");

            Properties prop = new Properties();
            InputStream input = null;
            try {
                input = ClassLoader.getSystemClassLoader().getResourceAsStream("database.properties");
                prop.load(input);
            } catch (IOException io) {
                logger.warn("input oluşturmada hata meydana geldi  HATA : "+io);
            }

            String jdbcUrl = prop.getProperty("jdbc.url");
            String jdbcUsername = prop.getProperty("jdbc.username");
            String jdbcPassword = prop.getProperty("jdbc.password");

            Properties properties = new Properties();
            properties.setProperty("user", jdbcUsername);
            properties.setProperty("password", jdbcPassword);
            properties.setProperty("useUnicode", "yes");
            properties.setProperty("characterEncoding", "UTF-8");
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("autoReconnect", "true");
            properties.setProperty("useSSL", "false");


            config.setJdbcUrl(jdbcUrl);
            config.setUsername(jdbcUsername);
            config.setPassword(jdbcPassword);

            config.setMaximumPoolSize(10);
            config.setAutoCommit(false);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

@Override
public Country getCountryId(int countryNo) {
    try {
        ResultSet resultSet=executeQuery(GET_COUNTRY,countryNo);
        while (resultSet.next()){

            int countryId=resultSet.getInt("countryNo");

            String countryName= resultSet.getString("countryName");
            String countryLanguage= resultSet.getString("countryLanguage");

            Country country=new Country(countryId,countryName,countryLanguage);
            logger.info("Veritabanından veri çekilmiştir");
            return country;
        }
    } catch (Exception e) {
        logger.error("Veritabanından veri çekilirken hata oluştmuştur   HATA : "+e);
    }
    return null;
}

    @Override
    public void updateCountry(Country country) {
        try {
            execute(UPDATE_COUNTRY,country.getCountryName(),country.getCountryLanguage(),country.getCountryNo());

            logger.info("ülke güncellenmiştir");
        } catch (Exception e) {
            logger.error("Güncelleme yapılırken hata oluştmuştur   HATA : "+e);
        }
    }

    @Override
    public void saveCountry(Country country) {
        try {
            execute(SAVE_COUNTRY,country.getCountryNo(),country.getCountryName(),country.getCountryLanguage());
            logger.info("Veritabanına kayıt yapılmıştır");
        } catch (Exception e) {
            logger.error("Veritabanına kayıt yapılırken hata oluştmuştur   HATA : "+e);
        }
    }

    @Override
    public void deleteCountry(int countryNo) {
        try {
            execute(DELETE_COUNTRY,countryNo);
            logger.info(countryNo+" numaralı ülke silinmistir");
        } catch (Exception e) {
            logger.error("silme işleminde hata meydana gelmiştir   HATA : "+e);
        }
    }

    private void execute(String sql, Object... queryParameters){
        try {
            Connection connection=dataSource.getConnection();
            PreparedStatement ps=connection.prepareStatement(sql);
            int index=1;
            if(queryParameters!=null){

                // queryParameters.length(n-> ps.setObject(index++,n));
                for(Object paramater:queryParameters){
                    ps.setObject(index++,paramater);
                }
            }
            ps.executeUpdate();

        } catch (Exception e) {
            logger.error("execute functionunda hata meydana geldi  HATA :"+e);
        }
    }

    private ResultSet executeQuery(String sql, Object... queryParameters) {
        try {
            Connection connection=dataSource.getConnection();
            PreparedStatement ps=connection.prepareStatement(sql);

            int index=1;
            if(queryParameters!=null){
                for(Object paramater:queryParameters){
                    ps.setObject(index++,paramater);
                }
            }
            ResultSet resultSet=ps.executeQuery();
            return resultSet;
        } catch (Exception e) {
            logger.error("ResultSet functionunda hata meydana geldi  HATA :"+e);
        }
        return null;
    }

}
