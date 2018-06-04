package railway;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Seat {
    
    //change stracture classes TypeCarriage,Seat 
    TypeCarriage typeCrr; 
    //change stracture classes TypeCarriage,Seat 
   
    String type;
    int number;
    
public boolean isBookedBy(BookedTicket t){
       return t.getTicket().seat.equals(this);
    } 
    
}
