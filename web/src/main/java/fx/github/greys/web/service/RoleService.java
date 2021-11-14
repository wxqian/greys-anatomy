package fx.github.greys.web.service;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.RoleDto;
import fx.github.greys.web.entity.system.Role;
import fx.github.greys.web.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public GreysResponse<String> addRole(RoleDto dto) {
        GreysResponse<String> result = GreysResponse.createSuccess();
        try {
            Role role = new Role();
            role.setName(dto.getName());
            role.setDesc(dto.getDesc());
            roleRepository.save(role);
        } catch (Exception e) {
            log.error("add role occur exception.role:{}", dto,e);
            result = GreysResponse.createError();
        }
        return result;
    }
}
