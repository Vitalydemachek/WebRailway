/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package railway;

import java.time.LocalDateTime;

/**
 *
 * @author user
 */
public class BookedTicket {

    private Ticket t;
    private Stop from;
    private Stop to;

    public BookedTicket(Ticket t, Stop from, Stop to) {
        this.t = t;
        this.from = from;
        this.to = to;
    }

    public Ticket getTicket() {
        return t;
    }

    public Stop getFrom() {
        return from;
    }

    public Stop getTo() {
        return to;
    }

    public boolean assignedTo(Carriage carriage) {
        return t.carriag.equals(carriage);
    }

    private boolean intersects(Stop from, Stop to) {
        LocalDateTime topA = this.from.date;
        LocalDateTime topB = this.to.date;
        LocalDateTime bottomA = from.date;
        LocalDateTime bottomB = to.date;

        return topB.isAfter(bottomA) && bottomB.isAfter(topA);
    }

    public boolean getResoltCheckIntersectionStops(Stop from, Stop to) {
        return this.intersects(from, to);

    }

}
