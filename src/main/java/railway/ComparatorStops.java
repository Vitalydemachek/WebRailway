/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package railway;

import java.util.Comparator;

/**
 *
 * @author user
 */ 
 public class ComparatorStops implements Comparator<Stop> {   
    @Override
    public int compare(Stop s1, Stop s2) {
        if(s1.date.compareTo(s2.date) > 0){
            return 1;
        } else if (s1.date.compareTo(s2.date) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
    
}   
    
