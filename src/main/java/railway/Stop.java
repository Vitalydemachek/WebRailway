package railway;

import java.time.LocalDateTime;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Stop {
    LocalDateTime date;
    Trip trip;
    City city;

    public Stop(LocalDateTime d_, Trip t_, City c_) {
        this.date = d_;
        this.trip = t_;
        this.city = c_;

    }

    public boolean matches(City from, City to, LocalDateTime requiredDate) {
        boolean matchesByCity = this.city.equals(from) || this.city.equals(to);
        boolean matchesByDate = !date.isBefore(requiredDate);
        return matchesByCity && matchesByDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stop stop = (Stop) o;

        if (date != null ? !date.equals(stop.date) : stop.date != null) return false;
        if (trip != null ? !trip.equals(stop.trip) : stop.trip != null) return false;
        return city != null ? city.equals(stop.city) : stop.city == null;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (trip != null ? trip.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }


}
