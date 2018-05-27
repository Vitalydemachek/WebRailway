package railway;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Carriage {
    Trip trip;
    TypeCarriage type;
    int number;
    
    
    public Carriage(Trip trip, TypeCarriage type, int number){
        this.trip = trip;
        this.type = type;
        this.number = number;       
    }
    
}
