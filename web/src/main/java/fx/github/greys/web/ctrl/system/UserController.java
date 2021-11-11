package fx.github.greys.web.ctrl.system;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.UserDto;
import fx.github.greys.web.entity.system.User;
import fx.github.greys.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api")
@Api("用户操作类接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("user")
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public GreysResponse<String> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("listUser")
    @ApiOperation(value = "展示用户列表", notes = "展示用户列表")
    public GreysResponse<List<User>> listUser() {
        return userService.listUser();
    }


    @PostMapping("login")
    @ApiOperation(value = "用户登陆", notes = "用户登陆")
    public GreysResponse<UserDto> login(String username, String password) {
        return userService.login(username, password);
    }


}
