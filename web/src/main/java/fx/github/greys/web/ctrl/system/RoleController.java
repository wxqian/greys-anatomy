package fx.github.greys.web.ctrl.system;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.RoleDto;
import fx.github.greys.web.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/system")
@RestController
@Slf4j
@Api(tags = "系统管理")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping
    @ApiOperation(value = "添加角色", notes = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "角色名"),
            @ApiImplicitParam(name = "desc", value = "描述")})
    public GreysResponse<String> addRole(@RequestBody RoleDto dto) {
        return roleService.addRole(dto);
    }



}
