package fx.github.greys.web.ctrl.system;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.RoleDto;
import fx.github.greys.web.service.RoleService;
import fx.github.greys.web.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    @PostMapping("role")
    @ApiOperation(value = "添加角色", notes = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "角色名"),
            @ApiImplicitParam(name = "desc", value = "描述"),
            @ApiImplicitParam(name = "permissions", value = "权限")})
    public GreysResponse<String> addRole(@RequestBody RoleDto dto) {
        return roleService.modifyRole(dto);
    }


    public GreysResponse<Page<RoleVo>> listRoles(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return roleService.listRoles(pageable);
    }

}
