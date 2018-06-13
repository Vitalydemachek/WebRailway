package railway;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by pro-27 on 16.04.2018.
 */
public class DataMapper {
    Connection cnn;

    public void ConnectDB(String User, String Password) throws ClassNotFoundException, SQLException {

        String address = "jdbc:postgresql://localhost:5432/railway";
        Class.forName("org.postgresql.Driver");
        cnn = DriverManager.getConnection(address, User, Password);

        //return connection;

    }

    public HashSet<City> loadCities() throws ClassNotFoundException {

        HashSet<City> Cities = new HashSet<>();

//        String address = "jdbc:postgresql://localhost:5432/railway";
//        Class.forName("org.postgresql.Driver");

        try {
            //Connection connection = DriverManager.getConnection(address, "postgres", "123");
            Connection connection = cnn;
            Statement statement = connection.createStatement();
            String query = "Select name From cities;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                String name = result.getString("name");

                City city = new City(name);
                Cities.add(city);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());

        }

        return Cities;

    }

    public HashMap<Integer, Trip> loadTrip(HashSet<City> ci) throws ClassNotFoundException {

        HashMap<Integer, Trip> trips = new HashMap<>();

        //String address = "jdbc:postgresql://localhost:5432/railway";
        //Class.forName("org.postgresql.Driver");

        try {
            Connection connection = cnn;
            Statement statement = connection.createStatement();
            String query = "Select ID,number,city_from,city_to,departure_date From trips;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                int id = result.getInt("ID");
                String number = result.getString("number");
                String cityFrom = result.getString("city_from");
                String cityTo = result.getString("city_to");
                LocalDate DipDate = result.getDate("departure_date").toLocalDate();
                LocalTime DipTime = result.getTime("departure_date").toLocalTime();
                LocalDateTime DipDatTime = LocalDateTime.of(DipDate, DipTime);

                City cityFromLink = ci.stream().filter(c
                        -> (c.name == null ? cityFrom == null : c.name.equals(cityFrom))).collect(Collectors.toList()).isEmpty()
                        ? new City(cityFrom) : ci.stream().filter(c
                        -> (c.name == null ? cityFrom == null : c.name.equals(cityFrom))).collect(Collectors.toList()).get(0);

                City cityToLink = ci.stream().filter(c
                        -> (c.name == null ? cityTo == null : c.name.equals(cityTo))).collect(Collectors.toList()).isEmpty()
                        ? new City(cityTo) : ci.stream().filter(c
                        -> (c.name == null ? cityTo == null : c.name.equals(cityTo))).collect(Collectors.toList()).get(0);
//                String pattern = "HH:mm:ss dd.MM.yyyy";
//                DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
//                LocalDateTime date_ = LocalDateTime.parse(DipDate, f);

                Trip trip = new Trip(number, cityFromLink, cityToLink, DipDatTime);
                trips.put(id, trip);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());

        }

        return trips;

    }

    public HashMap<Integer, Stop> LoadStop(HashSet<City> ci, HashMap<Integer, Trip> tr) throws ClassNotFoundException {

        HashMap<Integer, Stop> stops = new HashMap<>();

        try {
            Connection connection = cnn;
            Statement statement = connection.createStatement();
            String query = "Select ID,arrive_date,tripID,city From stops;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                int id = result.getInt("ID");
                LocalDate ArrDate = result.getDate("arrive_date").toLocalDate();
                LocalTime ArrTime = result.getTime("arrive_date").toLocalTime();
                LocalDateTime ArrDatTime = LocalDateTime.of(ArrDate, ArrTime);
                int tripID = result.getInt("tripID");
                String city = result.getString("city");

                City cityLink = ci.stream().filter(c
                        -> (c.name == null ? city == null : c.name.equals(city))).collect(Collectors.toList()).isEmpty()
                        ? new City(city) : ci.stream().filter(c
                        -> (c.name == null ? city == null : c.name.equals(city))).collect(Collectors.toList()).get(0);

                Trip tripLink = tr.get(tripID);

                Stop stop = new Stop(ArrDatTime, tripLink, cityLink);
                stops.put(id, stop);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());

        }

        return stops;

    }

    public HashMap<Integer, TypeCarriage> LoadTypeCarriage() throws ClassNotFoundException {

        HashMap<Integer, TypeCarriage> carriageTypes = new HashMap<>();

        try {
            Connection connection = cnn;
            Statement statement = connection.createStatement();
            String query = "Select ID,typeDescription From typeCarriage;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                int id = result.getInt("ID");
                String typeDescription = result.getString("typeDescription");

                TypeCarriage typeCarriage = new TypeCarriage(typeDescription);
                carriageTypes.put(id, typeCarriage);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());

        }

        return carriageTypes;

    }

    public HashMap<Integer, Seat> LoadSeats(HashMap<Integer, TypeCarriage> tc) throws ClassNotFoundException {

        HashMap<Integer, Seat> seats = new HashMap<>();

        try {
            Connection connection = cnn;
            Statement statement = connection.createStatement();
            String query = "Select ID,typeCrr_ID,typeSeat,numberSeat From seats;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                int id = result.getInt("ID");
                int typeCrr_ID = result.getInt("typeCrr_ID");
                String typeSeat = result.getString("typeSeat");
                int numberSeat = result.getInt("numberSeat");

                TypeCarriage typeCarriageLink = tc.get(typeCrr_ID);

                Seat seat = new Seat();
                seat.typeCrr = typeCarriageLink;
                seat.type = typeSeat;
                seat.number = numberSeat;
                seats.put(id, seat);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());

        }

        return seats;

    }

    public HashMap<Integer, Carriage> LoadCarriage(HashMap<Integer, Trip> tr, HashMap<Integer, TypeCarriage> tc) throws ClassNotFoundException {

        HashMap<Integer, Carriage> carriages = new HashMap<>();

        try {
            Connection connection = cnn;
            Statement statement = connection.createStatement();
            String query = "Select ID,tripID,typeCrr_ID,numberCarriage From carriage;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                int id = result.getInt("ID");
                int tripID = result.getInt("tripID");
                int typeCrr_ID = result.getInt("typeCrr_ID");
                int numberCarriage = result.getInt("numberCarriage");

                Trip tripeLink = tr.get(tripID);
                TypeCarriage typeCarriageLink = tc.get(typeCrr_ID);

                Carriage carriage = new Carriage(tripeLink, typeCarriageLink, numberCarriage);
                carriages.put(id, carriage);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());

        }

        return carriages;

    }

    public HashMap<Integer, Ticket> LoadTicket(HashSet<City> ci, HashMap<Integer, Trip> tr, HashMap<Integer, Seat> se, HashMap<Integer, Carriage> cr) throws ClassNotFoundException {

        HashMap<Integer, Ticket> tickets = new HashMap<>();

        try {
            Connection connection = cnn;
            Statement statement = connection.createStatement();
            String query = "Select ID,tripID,city_from,city_to,departure_date,seatID,carrID,customerFirstName From ticket;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                int id = result.getInt("ID");
                int tripID = result.getInt("tripID");
                String cityFrom = result.getString("city_from");
                String cityTo = result.getString("city_to");
                LocalDate DipDate = result.getDate("departure_date").toLocalDate();
                LocalTime DipTime = result.getTime("departure_date").toLocalTime();
                LocalDateTime DipDatTime = LocalDateTime.of(DipDate, DipTime);
                int seatID = result.getInt("seatID");
                int carrID = result.getInt("carrID");
                String customerFirstName = result.getString("customerFirstName");

                Optional<City> cityFromLink = ci.stream().filter(c
                        -> (c.name == null ? cityFrom == null : c.name.equals(cityFrom))).findFirst();
                City ChoseCityFrom = cityFromLink.orElse(new City(cityFrom));

                Optional<City> cityToLink =   ci.stream().filter(c
                        -> (c.name == null ? cityTo == null : c.name.equals(cityTo))).findFirst();
                City ChoseCityTo = cityToLink.orElse(new City(cityTo));

                Trip tripeLink = tr.get(tripID);
                Seat seatLink = se.get(seatID);
                Carriage carriageLink = cr.get(carrID);

                Ticket ticket = new Ticket(tripeLink, ChoseCityFrom, ChoseCityTo, DipDatTime, seatLink, carriageLink, customerFirstName);

                tickets.put(id, ticket);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());

        }

        return tickets;

    }

    public List<BookedTicket> loadBookedTicket(HashMap<Integer,Ticket> tck,HashMap<Integer,Stop> st) throws ClassNotFoundException {

        List<BookedTicket> BookedTickets = new ArrayList<>();

        try {
            Connection connection = cnn;
            Statement statement = connection.createStatement();
            String query = "Select ID,ticketID,stopFromID,stopToID From bookedTicket;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                int id = result.getInt("ID");
                int ticketID = result.getInt("ticketID");
                int stopFromID = result.getInt("stopFromID");
                int stopToID = result.getInt("stopToID");

                Ticket ticketLink = tck.get(ticketID);
                Stop stopFromLink = st.get(stopFromID);
                Stop stopToLink = st.get(stopToID);

                BookedTicket bookedTicket = new BookedTicket(ticketLink,stopFromLink,stopToLink);
                BookedTickets.add(bookedTicket);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());

        }

        return BookedTickets;

    }

}
