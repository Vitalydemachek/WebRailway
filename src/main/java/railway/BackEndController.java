package railway;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class BackEndController {

    ArrayList<City> list = new ArrayList<City>();
    ArrayList<Part> listParts = new ArrayList<Part>();
    HashSet<Trip> SetTrips = new HashSet();
    HashSet<Stop> SetStops = new HashSet();
    HashSet<Carriage> SetCarriage = new HashSet();
    HashSet<Seat> SetSeat = new HashSet();
    List<TypeCarriage> TypeCarriage = new ArrayList<>();
    List<BookedTicket> bookedTickets = new ArrayList<>();

    public void addCity(City c) {
        //добавить города
        if (list.contains(c)) {
            throw new IllegalArgumentException();
        }
        list.add(c);
    }

    public void linkCities(City A, City B) {
        //создать связи
        if (A == B) {
            throw new IllegalArgumentException();
        }

        if ((!list.contains(A)) || (!list.contains(B))) {
            throw new IllegalArgumentException();
        }

        Part p = new Part(A, B);
        Part p1 = new Part(B, A);
        listParts.add(p);
        listParts.add(p1);

    }

    public boolean checkLink(City A, City B) {
        //проверить что для пары городов существует связь

        for (int i = 0; i < listParts.size(); i++) {

            if (listParts.get(i).a == A && listParts.get(i).b == B) {

                return true;

            }

        }

        return false;
    }

    public Trip createTrip(String tripNumber, City From_, City To_, String depurtureDateTime) {

        String pattern = "HH:mm:ss dd.MM.yyyy";
        DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime datDepart = LocalDateTime.parse(depurtureDateTime, f);
        LocalDateTime current = LocalDateTime.now();
        Trip nextTrip;

        if (current.isAfter(datDepart)) {

            throw new IllegalArgumentException("Дата поездки должна быть в будующем");

        }

        nextTrip = new Trip(tripNumber, From_, To_, datDepart);

        SetTrips.add(nextTrip);

        return nextTrip;

    }

    public Stop createStop(String arriveDate, Trip relateTrip, City arriveCity) {
        String pattern = "HH:mm:ss dd.MM.yyyy";
        DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime datArr = LocalDateTime.parse(arriveDate, f);
        Stop nextStop;
        nextStop = new Stop(datArr, relateTrip, arriveCity);

        SetStops.add(nextStop);

        return nextStop;
    }

    public Carriage createCarriage(Trip reletedTrip, railway.TypeCarriage carriagtype, int carriagNumber) {
        Carriage carr = new Carriage(reletedTrip, carriagtype, carriagNumber);
        SetCarriage.add(carr);
        return carr;
    }

    //Pay attention, this is one of important approaches of project
    public HashSet<TicketWeb> saleTickets(City from, City to, String date) throws ClassNotFoundException {

        HashSet<TicketWeb> ticketsWeb = new HashSet();

        //get data of sql tables
        try{
        DataMapper onesMapper = new DataMapper();
        onesMapper.ConnectDB("postgres", "Natanmorderlamb13");
        HashSet ci = onesMapper.loadCities();
        HashMap<Integer, Trip> tr = onesMapper.loadTrip(ci);
        HashMap<Integer, Stop> st = onesMapper.LoadStop(ci, tr);
        HashMap<Integer, TypeCarriage> tc = onesMapper.LoadTypeCarriage();
        HashMap<Integer, Seat> se = onesMapper.LoadSeats(tc);
        HashMap<Integer, Carriage> cr = onesMapper.LoadCarriage(tr, tc);
        HashMap<Integer, Ticket> tck = onesMapper.LoadTicket(ci, tr, se, cr);
        List<BookedTicket> btck = onesMapper.loadBookedTicket(tck, st);

        SetStops.addAll(st.values());
        SetCarriage.addAll(cr.values());
        bookedTickets.addAll(btck);
        SetSeat.addAll(se.values());

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            return ticketsWeb;

        }

        String pattern = "HH:mm:ss dd.MM.yyyy";
        DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime requiredDate = LocalDateTime.parse(date, f);
        ComparatorStops OrderStopsByDate = new ComparatorStops();
        //HashSet<Ticket> tickets = new HashSet();

        //receive stops according to data request and turn list stops into map where key is trip and object is list stops
        Map<Trip, List<Stop>> stopsByTrip = SetStops
                .stream()
                .filter(s -> s.matches(from, to, requiredDate))
                .collect((Collectors.groupingBy(s -> s.trip)));

        //determine empty seat for each trip
        stopsByTrip.forEach((trip, stopList) -> {

            //check that there are both required stops on the trip(departure stop and arriving stop)
            if (stopList.size() > 1) {

                //order stops by date and determine what stop is (from or to)
                stopList.sort(OrderStopsByDate);
                Stop stopFrom = stopList.get(0);
                Stop stopTo = stopList.get(1);

                //find out carriages related with current trip
                List<Carriage> linkedCarr = SetCarriage
                        .stream()
                        .filter(crr -> crr.trip.equals(trip))
                        .collect(Collectors.toList());

                //define what tickets of current trip have been booked yet
                List<BookedTicket> linkedBookedTickets = bookedTickets.stream()
                        .filter(ticket -> ticket.getTicket().trip.equals(trip))
                        .collect(Collectors.toList());

                //define empty seats for each carriage of current trip
                linkedCarr.forEach(crr -> {

                            //get set of seats related with current carriage
                            ////change structure classes TypeCarriage,Seat
                            ////List<Seat> carriageConsistOfSeats = crr.type.setSeats;
                            List<Seat> carriageConsistOfSeats = SetSeat.stream().filter(seat
                                    -> seat.typeCrr.equals(crr.type)).collect(Collectors.toList());
                            //change structure classes TypeCarriage,Seat

                            //cut out booked seats from available seats of current carriage
                            List<Seat> linkedEmptySeats = carriageConsistOfSeats.stream().filter(seat
                                    -> linkedBookedTickets.stream()
                                    .noneMatch(ticket
                                            //check of current carriage match the booked ticket's carriage
                                            -> ticket.assignedTo(crr)
                                            //check of seat of current carriage mentioned in booked ticket
                                            && seat.isBookedBy(ticket)
                                            //check of intersection of required date's interval ("stopFrom' and "stopTo")
                                            // and date's interval already booked for current trip
                                            && ticket.getResoltCheckIntersectionStops(stopFrom, stopTo))).collect(Collectors.toList());

                            linkedEmptySeats.forEach(emptySeat -> {

                                //Ticket t_ = new Ticket(trip, stopFrom.city, stopTo.city, stopFrom.date, emptySeat, crr, "ServiceValue");
                                TicketWeb tw = new TicketWeb(trip.number, String.valueOf(crr.number), emptySeat.type, String.valueOf(emptySeat.number), stopFrom.date.toString());

                                ticketsWeb.add(tw);

                            });

                        }
                );

            }

        });

        return ticketsWeb;

    }

}
