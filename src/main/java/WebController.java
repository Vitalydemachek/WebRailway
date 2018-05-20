import java.util.ArrayList;
import java.util.List;
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
    
    private static List<Ticket> AppropriatTicckets = new ArrayList<Ticket>();
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
     AppropriatTicckets = (List<Ticket>)backEndController.saleTickets(cityFrom, cityTo, depurtDate);
        
        
     model.addAttribute("c_from",cityFrom);
     model.addAttribute("c_to",cityTo);
     model.addAttribute("depurt_date",depurtDate);

        return "result";
    }


    public static void main(String[] args) {

        SpringApplication.run(WebController.class,args);

    }



}
