package fx.github.greys.web.ctrl.system;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.PermissionDto;
import fx.github.greys.web.dto.RoleDto;
import fx.github.greys.web.service.RoleService;
import fx.github.greys.web.vo.MenuItemVo;
import fx.github.greys.web.vo.PermissionVo;
import fx.github.greys.web.vo.RoleVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("listRoles")
    @ApiOperation(value = "角色列表", notes = "角色列表")
    public GreysResponse<Page<RoleVo>> listRoles(@ApiParam("每页条数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                 @ApiParam("页数") @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return roleService.listRoles(pageable);
    }

    @PostMapping("permission")
    @ApiOperation(value = "添加角色", notes = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "权限名"),
            @ApiImplicitParam(name = "desc", value = "描述"),
            @ApiImplicitParam(name = "url", value = "路由"),
            @ApiImplicitParam(name = "icon", value = "icon"),
            @ApiImplicitParam(name = "parentId", value = "父权限id"),
            @ApiImplicitParam(name = "sorts", value = "排序")})
    public GreysResponse<String> addPermission(@RequestBody PermissionDto permissionDto) {
        return roleService.modifyPermission(permissionDto);
    }

    @GetMapping("listPermissions")
    @ApiOperation(value = "角色列表", notes = "角色列表")
    public GreysResponse<Page<PermissionVo>> listPermissions(@ApiParam("每页条数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                             @ApiParam("页数") @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return roleService.listPermissions(pageable);
    }

    @GetMapping({"rolePermissions/{roleId}","rolePermissions"})
    @ApiOperation(value = "角色权限", notes = "角色权限")
    public GreysResponse<List<PermissionVo>> rolePermissions(@PathVariable(name = "roleId", required = false) @ApiParam(name = "角色id",required = false) Long roleId) {
        return roleService.rolePermissions(roleId);
    }

}
