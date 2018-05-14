package railway;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class City {
    String name;

    public City(String name){
        this.name = name;

    }

    @Override
 public boolean equals   (Object o){

        if (this==o) return  true;
        if (o==null || getClass() != o.getClass())
            return false;
        City City = (railway.City) o;
        
        return  name.equals(City.name);


    }



}
