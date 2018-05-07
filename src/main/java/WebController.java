import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class WebController {

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

    public static void main(String[] args) {

        SpringApplication.run(WebController.class,args);

    }


}
