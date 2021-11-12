package fx.github.greys.web.ctrl.system;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.UserDto;
import fx.github.greys.web.entity.system.User;
import fx.github.greys.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("api")
@Api(value = "用户操作类接口", tags = "系统管理")
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
    public GreysResponse<Page<User>> listUser(@ApiParam @RequestParam(required = false, defaultValue = "10") int pageSize,
                                              @ApiParam @RequestParam(required = false, defaultValue = "1") int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return userService.pageUser(pageable);
    }

    @DeleteMapping("user/{id}")
    @ApiOperation(value = "用户删除", notes = "用户删除")
    public GreysResponse<String> deleteUser(@ApiParam("id") @PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }


    @PostMapping("login")
    @ApiOperation(value = "用户登陆", notes = "用户登陆")
    public GreysResponse<UserDto> login(@ApiParam("用户名") String username, @ApiParam("密码") String password) {
        return userService.login(username, password);
    }


}
