package fx.github.greys.web.service;

import com.google.common.base.Splitter;
import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.PermissionDto;
import fx.github.greys.web.dto.RoleDto;
import fx.github.greys.web.entity.system.Permission;
import fx.github.greys.web.entity.system.Role;
import fx.github.greys.web.entity.system.RolePermission;
import fx.github.greys.web.repository.PermissionRepository;
import fx.github.greys.web.repository.RolePermissionRepository;
import fx.github.greys.web.repository.RoleRepository;
import fx.github.greys.web.repository.UserRoleRepository;
import fx.github.greys.web.vo.PermissionVo;
import fx.github.greys.web.vo.RoleVo;
import fx.github.greys.web.vo.TreePermissionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static fx.github.greys.web.constant.Constants.COMMA;

@Service
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 添加角色
     *
     * @param dto
     * @return
     */
    @Transactional
    public GreysResponse<String> modifyRole(RoleDto dto) {
        GreysResponse<String> result = GreysResponse.createSuccess();
        try {
            Role role = new Role();
            if (dto.getId() != null) {
                role = roleRepository.getById(dto.getId());
                role.setModifyTime(System.currentTimeMillis());
                rolePermissionRepository.deleteByRoleId(dto.getId());
            }
            role.setName(StringUtils.isNotBlank(dto.getName()) ? dto.getName() : role.getName());
            role.setDesc(StringUtils.isNotBlank(dto.getDesc()) ? dto.getDesc() : role.getDesc());
            final Role _role = roleRepository.saveAndFlush(role);
            String permissions = dto.getPermissions();
            if (StringUtils.isNotBlank(permissions)) {
                List<RolePermission> rolePermissions = Splitter.on(COMMA).omitEmptyStrings().trimResults()
                        .splitToList(permissions)
                        .stream().map(Long::parseLong).map(p -> {
                            RolePermission rolePermission = new RolePermission();
                            rolePermission.setRoleId(_role.getId());
                            rolePermission.setPermissionId(p);
                            return rolePermission;
                        }).collect(Collectors.toList());
                rolePermissionRepository.saveAll(rolePermissions);
            }
        } catch (Exception e) {
            log.error("add role occur exception.role:{}", dto, e);
            result = GreysResponse.createError();
        }
        return result;
    }

    /**
     * @param pageable
     * @return
     */
    public GreysResponse<Page<RoleVo>> listRoles(Pageable pageable) {
        GreysResponse<Page<RoleVo>> result = GreysResponse.createSuccess();
        try {
            Page<Role> roles = roleRepository.findAll(pageable);
            Page<RoleVo> roleVos = convertToVo(roles);
            result.setResult(roleVos);
        } catch (Exception e) {
            result = GreysResponse.createError("list user occurs error");
            log.error("list user occurs exception.", e);
        }
        return result;
    }

    private Page<RoleVo> convertToVo(Page<Role> roles) {
        List<RoleVo> roleVos = roles.getContent().stream().map(r -> {
            RoleVo roleVo = new RoleVo();
            roleVo.setId(r.getId());
            roleVo.setDesc(r.getDesc());
            roleVo.setStatus(r.getStatus());
            roleVo.setName(r.getName());
            roleVo.setCreateTime(r.getCreateTime());
            roleVo.setModifyTime(r.getModifyTime());
//            roleVo.setPermissionVos(r.getPermissions().stream().map(this::convertPermissionVo).collect(Collectors.toList()));
            return roleVo;
        }).collect(Collectors.toList());
        Page<RoleVo> roleVoPage = new PageImpl<>(roleVos, roles.getPageable(), roles.getTotalElements());
        return roleVoPage;
    }

    @Transactional
    public GreysResponse<String> modifyPermission(PermissionDto dto) {
        GreysResponse<String> result = GreysResponse.createSuccess();
        try {
            Permission permission = new Permission();
            if (dto.getId() != null) {
                permission = permissionRepository.getById(dto.getId());
                permission.setModifyTime(System.currentTimeMillis());
            }
            permission.setName(dto.getName());
            permission.setDesc(dto.getDesc());
            permission.setUrl(dto.getUrl());
            permission.setIcon(dto.getIcon());
            permission.setParent(dto.getParentId() == null ? -1L : dto.getParentId());
            permission.setSorts(dto.getSorts());
            permission.setViewPath(dto.getViewPath());
            permissionRepository.saveAndFlush(permission);
        } catch (Exception e) {
            log.error("add permission occur exception.permission:{}", dto, e);
            result = GreysResponse.createError();
        }
        return result;
    }

    public GreysResponse<Page<PermissionVo>> listPermissions(Pageable pageable) {
        GreysResponse<Page<PermissionVo>> result = GreysResponse.createSuccess();
        try {
            Page<Permission> permissions = permissionRepository.findAll(pageable);
            Page<PermissionVo> permissionVos = convertToPermissionVo(permissions);
            result.setResult(permissionVos);
        } catch (Exception e) {
            result = GreysResponse.createError("list permissions occurs error");
            log.error("list permissions occurs exception.", e);
        }
        return result;
    }

    private Page<PermissionVo> convertToPermissionVo(Page<Permission> permissions) {
        List<PermissionVo> permissionVos = permissions.getContent().stream().map(this::convertPermissionVo).collect(Collectors.toList());
        Page<PermissionVo> permissionVoPage = new PageImpl<>(permissionVos, permissions.getPageable(), permissions.getTotalElements());
        return permissionVoPage;
    }

    private PermissionVo convertPermissionVo(Permission r) {
        PermissionVo permissionVo = new PermissionVo();
        permissionVo.setId(r.getId());
        permissionVo.setName(r.getName());
        permissionVo.setDesc(r.getDesc());
        permissionVo.setUrl(r.getUrl());
        permissionVo.setIcon(r.getIcon());
        permissionVo.setParentId(r.getParent());
        permissionVo.setStatus(r.getStatus());
        permissionVo.setSorts(r.getSorts());
        permissionVo.setCreateTime(r.getCreateTime());
        permissionVo.setModifyTime(r.getModifyTime());
        permissionVo.setViewPath(r.getViewPath());
        return permissionVo;
    }

    public GreysResponse<List<PermissionVo>> rolePermissions(Long roleId) {
        GreysResponse<List<PermissionVo>> result = GreysResponse.createSuccess();
        try {
            List<Permission> permissions;
            if (roleId == null) {
                permissions = permissionRepository.findAll();
            } else {
                Role role = roleRepository.getById(roleId);
                permissions = role.getId() == null ? new ArrayList<>() : role.getPermissions();
            }
            result.setResult(permissions.stream().map(this::convertPermissionVo).collect(Collectors.toList()));
        } catch (Exception e) {
            result = GreysResponse.createError("role permissions occurs error");
            log.error("role permissions occurs exception.", e);
        }
        return result;
    }

    @Transactional
    public GreysResponse<String> deleteRoles(String roleIds) {
        GreysResponse<String> result = GreysResponse.createSuccess();
        try {
            List<Long> ids = Splitter.on(COMMA)
                    .omitEmptyStrings()
                    .trimResults()
                    .splitToList(roleIds)
                    .stream().map(Long::parseLong).collect(Collectors.toList());
            roleRepository.deleteAllById(ids);
            userRoleRepository.deleteAllByRoleIds(ids);
            rolePermissionRepository.deleteAllByRoleIds(ids);
        } catch (Exception e) {
            log.error("delete role:{} occurs exception.", roleIds, e);
            result = GreysResponse.createError("delete role error");
        }
        return result;
    }

    @Transactional
    public GreysResponse<String> deletePermissions(String ids) {
        GreysResponse<String> result = GreysResponse.createSuccess();
        try {
            List<Long> pIds = Splitter.on(COMMA)
                    .omitEmptyStrings()
                    .trimResults()
                    .splitToList(ids)
                    .stream().map(Long::parseLong).collect(Collectors.toList());
            rolePermissionRepository.deleteAllByPermissionIds(pIds);
            permissionRepository.deleteAllById(pIds);
        } catch (Exception e) {
            log.error("delete permission:{} occurs exception.", ids, e);
            result = GreysResponse.createError("delete permission error");
        }
        return result;
    }

    public GreysResponse<List<TreePermissionVo>> treePermissions() {
        GreysResponse<List<TreePermissionVo>> result = GreysResponse.createSuccess();
        try {
            List<Permission> permissions;
            permissions = permissionRepository.findAll();
            result.setResult(permissions.stream().map(this::convertTreePermissionVo).collect(Collectors.toList()));
        } catch (Exception e) {
            result = GreysResponse.createError("role permissions occurs error");
            log.error("role permissions occurs exception.", e);
        }
        return result;
    }

    private TreePermissionVo convertTreePermissionVo(Permission permission) {
        TreePermissionVo permissionVo = new TreePermissionVo();
        if (permission.getParent() != null && permission.getParent() > 0) {
            permissionVo.setPId(permission.getParent() + "");
        } else {
            permissionVo.setPId("");
        }
        permissionVo.setValue(permission.getId() + "");
        permissionVo.setId(permission.getId());
        permissionVo.setTitle(permission.getName());
        return permissionVo;
    }
}
