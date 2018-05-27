package railway;

import java.time.LocalDateTime;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Ticket {
    Trip trip;
    City from;
    City to;
    LocalDateTime date;
    Seat seat;
    Carriage carriag;
    String ferst_name;
    
  public Ticket(Trip tr_, City f_, City t_, LocalDateTime d_, Seat s_, Carriage c_, String fn_){
  this.trip=tr_;
  this.from=f_;
  this.to = t_;
  this.date = d_;
  this.seat = s_;
  this.carriag = c_;
  this.ferst_name =fn_;
    
          } 
    
}
