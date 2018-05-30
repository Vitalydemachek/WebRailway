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
public class TicketWeb {
  private String  tripeNumber;
  private String  carrNamber;
  private String  seatType;
  private String  seatNumber;
  private String  depDate;
  
 public TicketWeb(String trNum,String carrNam,String sType,String sNum,String dDate){
     
   this.tripeNumber = trNum;
   this.carrNamber = carrNam;
   this.seatType = sType; 
   this.seatNumber = sNum; 
   this.depDate = dDate; 
     
 }
 
 public String getTripNumber(){
      return tripeNumber;
  }
 
 public String getCarrNumber(){
        return carrNamber;
        
    }
 
  public String getSeatType(){
 return seatType;   
    
}

public String getSeatNumber(){
    return seatNumber;
}
 
  public String getdepDate(){
      return depDate;
  }
    
    
}
