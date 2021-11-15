package fx.github.greys.web.service;

import com.google.common.base.Splitter;
import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.RoleDto;
import fx.github.greys.web.entity.system.Role;
import fx.github.greys.web.entity.system.RolePermission;
import fx.github.greys.web.repository.RolePermissionRepository;
import fx.github.greys.web.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    public GreysResponse<String> addRole(RoleDto dto) {
        GreysResponse<String> result = GreysResponse.createSuccess();
        try {
            Role role = new Role();
            role.setName(dto.getName());
            role.setDesc(dto.getDesc());
            final Role _role = roleRepository.save(role);
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
}
