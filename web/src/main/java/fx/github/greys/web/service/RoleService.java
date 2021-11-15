package fx.github.greys.web.service;

import com.google.common.base.Splitter;
import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.RoleDto;
import fx.github.greys.web.entity.system.Role;
import fx.github.greys.web.entity.system.RolePermission;
import fx.github.greys.web.repository.RolePermissionRepository;
import fx.github.greys.web.repository.RoleRepository;
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
            role.setName(dto.getName());
            role.setDesc(dto.getDesc());
            if (dto.getId() != null) {
                role.setId(dto.getId());
                rolePermissionRepository.deleteByRoleId(dto.getId());
            }
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
            return roleVo;
        }).collect(Collectors.toList());
        Page<RoleVo> roleVoPage = new PageImpl<>(roleVos, roles.getPageable(), roles.getTotalElements());
        return roleVoPage;
    }
}
