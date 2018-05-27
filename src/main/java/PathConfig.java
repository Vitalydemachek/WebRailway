import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PathConfig implements WebMvcConfigurer{



    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       // registry.addViewController("/").setViewName("main");
     //   registry.addViewController("/main").setViewName("mainPage");
        registry.addViewController("/search").setViewName("search");
        registry.addViewController("/greeting").setViewName("greeting");
    }
}
