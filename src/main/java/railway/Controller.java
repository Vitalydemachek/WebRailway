package railway;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Controller {

    ArrayList<City> list = new ArrayList<City>();
    ArrayList<Part> listParts = new ArrayList<Part>();
    HashSet<Trip> SetTrips = new HashSet();
    HashSet<Stop> SetStops = new HashSet();
    HashSet<Carriage> SetCarriage = new HashSet();
    List<railway.TypeCarriage> TypeCarriage = new ArrayList<>();
    //Map<Ticket, List<Stop>> MapBooking = new HashMap();
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

    public Trip creatTrip(String tripNumber, City From_, City To_, String depurtureDateTime) {

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

    public Stop creatStop(String arriveDate, Trip relateTrip, City arriveCity) {
        String pattern = "HH:mm:ss dd.MM.yyyy";
        DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime datArr = LocalDateTime.parse(arriveDate, f);
        Stop nextStop;
        nextStop = new Stop(datArr, relateTrip, arriveCity);

        SetStops.add(nextStop);

        return nextStop;
    }

    public Carriage creatCarriage(Trip reletedTrip, railway.TypeCarriage carriagtype, int carriagNumber) {
        Carriage carr = new Carriage(reletedTrip, carriagtype, carriagNumber);
        SetCarriage.add(carr);
        return carr;
    }

    public HashSet<Ticket> saleTickets(City from, City to, String date) {
        String pattern = "HH:mm:ss dd.MM.yyyy";
        DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime requiredDate = LocalDateTime.parse(date, f);
        ComparatorStops OrderStopsByDate = new ComparatorStops();
        HashSet<Ticket> tickets = new HashSet();

        Map<Trip, List<Stop>> stopsByTrip = SetStops
                .stream()
                .filter(s -> s.matches(from, to, requiredDate))
                .collect((Collectors.groupingBy(s -> s.trip)));

        stopsByTrip.forEach((trip, stopList) -> {
            if (stopList.size() > 1) {
                stopList.sort(OrderStopsByDate);

                Stop stopFrom = stopList.get(0);
                Stop stopTo = stopList.get(1);

                List<Carriage> linkedCarr = SetCarriage
                        .stream()
                        .filter(crr -> crr.trip.equals(trip))
                        .collect(Collectors.toList());

                List<BookedTicket> linkedBookedTickets = bookedTickets.stream()
                        .filter(ticket -> ticket.getTicket().trip.equals(trip))
                        .collect(Collectors.toList());

                linkedCarr.forEach(crr -> {
                    List<Seat> carriageConsisOfSeats = crr.type.setSeats;
                    List<Seat> linkedEmptySeats = carriageConsisOfSeats.stream().filter(seat
                            -> linkedBookedTickets.stream()
                                    .noneMatch(ticket -> ticket.assignedTo(crr) && seat.isBookedBy(ticket) && ticket.getResoltCheckIntersectionStops(stopFrom, stopTo))).collect(Collectors.toList());

                    linkedEmptySeats.forEach(emptySeat -> {

                        Ticket t_ = new Ticket(trip, stopFrom.city, stopTo.city, stopFrom.date, emptySeat, crr, "Петя");
                        tickets.add(t_);

                    });

                }
                );

            }

        });

        return tickets;

    }

}
