package railway;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Seat {
    
    //change structure classes TypeCarriage,Seat
    TypeCarriage typeCrr; 
    //change structure classes TypeCarriage,Seat
   
    String type;
    int number;
    
public boolean isBookedBy(BookedTicket t){
       return t.getTicket().seat.equals(this);
    } 
    
}
