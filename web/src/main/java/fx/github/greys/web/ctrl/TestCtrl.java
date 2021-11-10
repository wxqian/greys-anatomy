package fx.github.greys.web.ctrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestCtrl {

    @GetMapping("show")
    public String show(){
        return "hello";
    }
}
