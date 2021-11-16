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
import fx.github.greys.web.vo.PermissionVo;
import fx.github.greys.web.vo.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            role.setName(dto.getName());
            role.setDesc(dto.getDesc());
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
            roleVo.setCreateTime(r.getCreateTime());
            roleVo.setModifyTime(r.getModifyTime());
            roleVo.setPermissionVos(r.getPermissions().stream().map(this::convertPermissionVo).collect(Collectors.toList()));
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
            permission.setParent(dto.getParentId());
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
        return permissionVo;
    }
}
