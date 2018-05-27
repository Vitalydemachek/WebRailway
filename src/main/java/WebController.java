import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import railway.*;


@Controller
@EnableAutoConfiguration
public class WebController {

    private BackEndController backEndController = new BackEndController(); 
    
//    @RequestMapping("/")
//    @ResponseBody
//    public String Home() {
//
//        //метод обрабатывает запросы браузера
//
//        return "tt";
//
//
//    }

    @GetMapping("/greeting")
    public String greet(@RequestParam(name="str",required = false,defaultValue = "world") String name, Model model){

        model.addAttribute("name","railway");

       return "greeting";


    }

    @GetMapping("/mainPage")
    public String main(){

        //model.addAttribute("name","railway");

        return "main";


    }

    @GetMapping("/search")
    public  String proces(@RequestParam(name="city_from") String cityFrom,
        @RequestParam(name="city_to") String cityTo,
        @RequestParam(name="depurt_date") String depurtDate,
        Model model) {

     HashSet<Ticket> AppropriatTicckets;
     City cityF = new City(cityFrom);
     City cityT = new City(cityTo);
     
     String externalPattern = "yyyy-MM-dd";
     String internalPattern = "HH:mm:ss dd.MM.yyyy";
     DateTimeFormatter externalf = DateTimeFormatter.ofPattern(externalPattern);
     DateTimeFormatter internalf = DateTimeFormatter.ofPattern(internalPattern);
    String internalDepurtDate = LocalDate.parse(depurtDate, externalf).atStartOfDay().format(internalf);
     AppropriatTicckets = backEndController.saleTickets(cityF, cityT, internalDepurtDate);
        
     model.addAttribute("c_from",cityFrom);
     model.addAttribute("c_to",cityTo);
     model.addAttribute("d_date",depurtDate);
     model.addAttribute("emptySeats",AppropriatTicckets);

        return "result";
    }


    public static void main(String[] args) {

        SpringApplication.run(WebController.class,args);

    }



}
