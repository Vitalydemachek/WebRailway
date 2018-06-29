import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String greet(@RequestParam(name = "str", required = false, defaultValue = "world") String name, Model model) {

        model.addAttribute("name", "railway");

        return "greeting";

    }

    @GetMapping("/mainPage")
    public String main() {

        //model.addAttribute("name","railway");

        return "main";

    }

    @GetMapping("/search")
    public String process(@RequestParam(name = "city_from") String cityFrom,
                          @RequestParam(name = "city_to") String cityTo,
                          @RequestParam(name = "depurt_date") String depurtDate,
                          @RequestParam(name = "order",required = false,defaultValue = "ass") String order,
                          Model model) throws ClassNotFoundException, SQLException {

        HashSet<TicketWeb> AppropriateTickets;
        City cityF = new City(cityFrom);
        City cityT = new City(cityTo);

        String externalPattern = "yyyy-MM-dd";
        String internalPattern = "HH:mm:ss dd.MM.yyyy";
        DateTimeFormatter externalf = DateTimeFormatter.ofPattern(externalPattern);
        DateTimeFormatter internalf = DateTimeFormatter.ofPattern(internalPattern);
        String internalDepurtDate = LocalDate.parse(depurtDate, externalf).atStartOfDay().format(internalf);
        AppropriateTickets = backEndController.saleTickets(cityF, cityT, internalDepurtDate);
        List<TicketWeb> orderedAppropriateTickets;

//        List<TicketWeb> orderedAppropriateTickets = AppropriateTickets.stream().sorted(TicketWeb.TripNumberComparator.
//                thenComparing(TicketWeb.carrNumberComparator).
//                thenComparing(TicketWeb.seatNumberComparator)).collect(Collectors.toList());

        if (order.equals("tn")){

            orderedAppropriateTickets = AppropriateTickets.stream().sorted(TicketWeb.TripNumberComparator).collect(Collectors.toList());

        }else if (order.equals("cn")){

            orderedAppropriateTickets = AppropriateTickets.stream().sorted(TicketWeb.carrNumberComparator).collect(Collectors.toList());

        }else if (order.equals("sn")){

            orderedAppropriateTickets = AppropriateTickets.stream().sorted(TicketWeb.seatNumberComparator).collect(Collectors.toList());

        }else{

            orderedAppropriateTickets = AppropriateTickets.stream().sorted(TicketWeb.TripNumberComparator.
                thenComparing(TicketWeb.carrNumberComparator).
                thenComparing(TicketWeb.seatNumberComparator)).collect(Collectors.toList());

        }

        model.addAttribute("c_from", cityFrom);
        model.addAttribute("c_to", cityTo);
        model.addAttribute("d_date", depurtDate);
        model.addAttribute("emptySeats", orderedAppropriateTickets);

        return "result";
    }

    public static void main(String[] args) {

        SpringApplication.run(WebController.class, args);

    }

}
