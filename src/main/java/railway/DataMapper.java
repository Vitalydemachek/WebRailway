package railway;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Created by pro-27 on 16.04.2018.
 */
public class DataMapper {

    public HashSet<City> loadCities() throws ClassNotFoundException {

        HashSet<City> Cities = new HashSet<>();

        String address = "jdbc:postgresql://localhost:5432/railway";
        Class.forName("org.postgresql.Driver");

        try {
            Connection connection = DriverManager.getConnection(address, "postgres", "Natanmorderlamb13");
            Statement statement = connection.createStatement();
            String query = "Select name From cities;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                String name = result.getString("name");

                City city = new City(name);
                Cities.add(city);

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());

        }

        return Cities;

    }

    public HashSet<Trip> loadTrip() throws ClassNotFoundException {

        HashSet<Trip> trips = new HashSet<>();

        String address = "jdbc:postgresql://localhost:5432/railway";
        Class.forName("org.postgresql.Driver");

        try {
            Connection connection = DriverManager.getConnection(address, "postgres", "Natanmorderlamb13");
            Statement statement = connection.createStatement();
            String query = "Select ID,number,city_from,city_to,departure_date,foreign From trips;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                int id = result.getInt("ID");
                String namber = result.getString("number");
                String cityFrom = result.getString("city_from");
                String cityTo = result.getString("city_to");
                LocalDate DipDate = result.getDate("departure_date").toLocalDate();
                LocalTime DipTime = result.getTime("departure_date").toLocalTime();
                LocalDateTime DipDatTime = LocalDateTime.of(DipDate, DipTime);

                City cityFromLink = loadCities().stream().filter(c -> (c.name == null ? cityFrom == null : c.name.equals(cityFrom))).collect(Collectors.toList()).isEmpty()? new City(cityFrom):loadCities().stream().filter(c -> (c.name == null ? cityFrom == null : c.name.equals(cityFrom))).collect(Collectors.toList()).get(0);
                City cityToLink = loadCities().stream().filter(c -> (c.name == null ? cityTo == null : c.name.equals(cityTo))).collect(Collectors.toList()).isEmpty()?new City(cityFrom):loadCities().stream().filter(c -> (c.name == null ? cityTo == null : c.name.equals(cityTo))).collect(Collectors.toList()).get(0);
//                String pattern = "HH:mm:ss dd.MM.yyyy";
//                DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
//                LocalDateTime date_ = LocalDateTime.parse(DipDate, f);

                Trip trip = new Trip(namber, cityFromLink, cityToLink, DipDatTime);
                trips.add(trip);

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());

        }

        return trips;

    }

}
