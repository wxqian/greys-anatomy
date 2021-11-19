package fx.github.greys.web.ctrl.system;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.PermissionDto;
import fx.github.greys.web.dto.RoleDto;
import fx.github.greys.web.service.RoleService;
import fx.github.greys.web.vo.PermissionVo;
import fx.github.greys.web.vo.RoleVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/system")
@RestController
@Slf4j
@Api(tags = "系统管理")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping(value = "roles", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(value = "添加角色", notes = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "角色名"),
            @ApiImplicitParam(name = "desc", value = "描述"),
            @ApiImplicitParam(name = "permissions", value = "权限")})
    public GreysResponse<String> addRole(RoleDto dto) {
        return roleService.modifyRole(dto);
    }


    @GetMapping("roles")
    @ApiOperation(value = "角色列表", notes = "角色列表")
    public GreysResponse<Page<RoleVo>> listRoles(@ApiParam("每页条数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                 @ApiParam("页数") @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return roleService.listRoles(pageable);
    }

    @PostMapping(value = "permissions", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(value = "添加权限", notes = "添加权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "权限名"),
            @ApiImplicitParam(name = "desc", value = "描述"),
            @ApiImplicitParam(name = "url", value = "路由"),
            @ApiImplicitParam(name = "icon", value = "icon"),
            @ApiImplicitParam(name = "parentId", value = "父权限id"),
            @ApiImplicitParam(name = "sorts", value = "排序")})
    public GreysResponse<String> addPermission(PermissionDto permissionDto) {
        return roleService.modifyPermission(permissionDto);
    }

    @GetMapping("permissions")
    @ApiOperation(value = "权限列表", notes = "权限列表")
    public GreysResponse<Page<PermissionVo>> listPermissions(@ApiParam("每页条数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                             @ApiParam("页数") @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return roleService.listPermissions(pageable);
    }

    @GetMapping({"rolePermissions/{roleId}", "rolePermissions"})
    @ApiOperation(value = "角色权限", notes = "角色权限")
    public GreysResponse<List<PermissionVo>> rolePermissions(@PathVariable(name = "roleId", required = false) @ApiParam(name = "角色id", required = false) Long roleId) {
        return roleService.rolePermissions(roleId);
    }

    @PatchMapping("roles/{roleId}")
    @ApiOperation(value = "角色修改", notes = "角色修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "角色名"),
            @ApiImplicitParam(name = "desc", value = "描述"),
            @ApiImplicitParam(name = "permissions", value = "权限")})
    public GreysResponse<String> pathRoles(@PathVariable(name = "roleId") @ApiParam(name = "角色id") Long roleId,
                                           @RequestBody RoleDto roleDto) {
        roleDto.setId(roleId);
        return roleService.modifyRole(roleDto);
    }

    @DeleteMapping("roles/{roleIds}")
    @ApiOperation(value = "角色删除", notes = "角色删除")
    public GreysResponse<String> deleteRoles(@PathVariable(name = "roleIds") @ApiParam(name = "角色id") String roleIds) {
        return roleService.deleteRoles(roleIds);
    }

    @DeleteMapping("permissions/{ids}")
    @ApiOperation(value = "角色删除", notes = "角色删除")
    public GreysResponse<String> deletePermissions(@PathVariable(name = "ids") @ApiParam(name = "角色id") String ids) {
        return roleService.deletePermissions(ids);
    }

    @PatchMapping(value = "permissions/{id}")
    @ApiOperation(value = "权限修改", notes = "权限修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "权限名"),
            @ApiImplicitParam(name = "desc", value = "描述"),
            @ApiImplicitParam(name = "url", value = "路由"),
            @ApiImplicitParam(name = "icon", value = "icon"),
            @ApiImplicitParam(name = "parentId", value = "父权限id"),
            @ApiImplicitParam(name = "sorts", value = "排序")})
    public GreysResponse<String> patchPermissions(@PathVariable("id") @ApiParam("权限id") Long permissionId,
                                                  @RequestBody PermissionDto permissionDto) {
        permissionDto.setId(permissionId);
        return roleService.modifyPermission(permissionDto);
    }

    @GetMapping("parentPermissions/{parentId}")
    @ApiOperation(value = "上级权限列表", notes = "上级权限列表")
    public GreysResponse<List<PermissionVo>> parentPermissions(@PathVariable("parentId") @ApiParam("parentId") Long parentId) {
        return roleService.parentPermissions(parentId);
    }
}