package demo.fileupload.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String viewHomePage() {
        String a = "12";
        return "home";
    }
}
