package railway;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Seat {
    String type;
    int number;
    
public boolean isBookedBy(BookedTicket t){
       return t.getTicket().seat.equals(this);
    } 
    
}
