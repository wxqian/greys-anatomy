package fx.github.greys.web.ctrl;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.entity.User;
import fx.github.greys.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("addUser")
    public GreysResponse<String> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("listUser")
    public GreysResponse<List<User>> listUser(){
        return userService.listUser();
    }
}
