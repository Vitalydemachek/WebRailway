package railway;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class railway {

//    @Test
//    public void test() {
//        boolean cat = false;
//        try {
//            Controller С = new Controller();
//            City msc = new City("Москва");
//            //City omsk = new City("Омск");
//            //City nov = new City("Новосибирск");
//
//            С.addCity(msc);
//            //С.addCity(omsk);
//            С.linkCities(msc, msc);
//
//
//        } catch (IllegalArgumentException e) {
//
//            cat = true;
//
//        }
//
//        Assert.assertTrue(cat);
//
//        // Assert.assertTrue(С.checkLink(msc, omsk));//проверка
//
////        ArrayList <City> list = new ArrayList<City>();
////        City msc = new City("msc");
////        list.add(msc);
////        Assert.assertTrue(list.contains(msc));//проверка
//
//    }
//
//    @Test
//    public void checkCity() {
//
//        Controller С = new Controller();
//        City msc = new City("Москва");
//        City omsk = new City("Омск");
//        City nov = new City("Новосибирск");
//
//        С.addCity(msc);
//        С.addCity(omsk);
//        С.linkCities(msc, omsk);
//
//        Assert.assertTrue(С.checkLink(msc, omsk));//проверка
//        Assert.assertTrue(С.checkLink(omsk, msc));//проверка
//        Assert.assertFalse(С.checkLink(msc, nov));//проверка
//        Assert.assertFalse(С.checkLink(omsk, nov));//проверка
//
//
//    }
//
//    @Test
//    public void checkAddCity() {
//        boolean cat = false;
//        Controller C = new Controller();
//        City msc = new City("Москва");
//        C.addCity(msc);
//
//        try {
//            C.addCity(msc);
//
//
//        } catch (IllegalArgumentException e) {
//            cat = true;
//
//        }
//
//        Assert.assertTrue(cat);
//
//
//    }
//
//    @Test
//    public void testCreatTrip() {
//
//        Controller с = new Controller();
//        String tNumber = "РТ-1500";
//        City NVS = new City("Новосибирск");
//        City OMS = new City("Омск");
//        String tDate = "19:27:00 19.05.2018";
//        с.creatTrip(tNumber, NVS, OMS, tDate);
//
//
//    }
//
//    @Test
//    public void checkTripEsterday(){
//        boolean cat = false;
//        Controller с = new Controller();
//        String tNumber = "РТ-1500";
//        City NVS = new City("Новосибирск");
//        City OMS = new City("Омск");
//        String tDate = "19:27:00 25.09.2018";
//
//        try {
//
//            с.creatTrip(tNumber, NVS, OMS, tDate);
//        }catch(IllegalArgumentException e) {
//
//            cat = true;
//            System.out.println(e.getMessage());
//        }
//
//        Assert.assertTrue(cat);
//
//
//
//
//
//    }

    @Test
    public  void  testConnection() throws ClassNotFoundException, SQLException {

        DataMapper onesMapper = new DataMapper();

        onesMapper.ConnectDB("postgres","Natanmorderlamb13");

       HashSet s=onesMapper.loadCities();
       
       HashMap<Integer,Trip> t = onesMapper.loadTrip(s);

       Assert.assertNotEquals(0,s.size());


    } 
    
}
