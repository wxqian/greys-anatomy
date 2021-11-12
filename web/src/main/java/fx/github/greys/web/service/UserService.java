package fx.github.greys.web.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.UserDto;
import fx.github.greys.web.entity.system.User;
import fx.github.greys.web.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 用户操作类
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 用户添加
     *
     * @param user
     * @return
     */
    public GreysResponse<String> addUser(@RequestBody User user) {
        GreysResponse<String> result = GreysResponse.createSuccess();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            result = GreysResponse.createError("add user occurs error");
            log.error("add user occurs exception.", e);
        }
        return result;
    }


    /**
     * 用户列表展示
     *
     * @return
     */
    public GreysResponse<List<User>> listUser() {
        GreysResponse<List<User>> result = GreysResponse.createSuccess();
        try {
            List<User> users = userRepository.findAll();
            result.setResult(users);
        } catch (Exception e) {
            result = GreysResponse.createError("list user occurs error");
            log.error("list user occurs exception.", e);
        }
        return result;
    }

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return
     */
    public GreysResponse<UserDto> login(String username, String password) {
        log.info("user login .username:{}", username);
        GreysResponse<UserDto> result = GreysResponse.createError("username or password not correct.");
        if (StringUtils.isAnyBlank(username, password)) {
            return result;
        }
        User user = userRepository.findByUsername(username);
        if (user == null || !StringUtils.equals(user.getPassword(), password)) {
            return result;
        }
        UserDto dto = convertToDto(user);
        result = GreysResponse.createSuccess(dto, "success");
        return result;
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setToken(getToken(user));
        return dto;
    }

    /**
     * 获取token
     *
     * @param user
     * @return
     */
    public String getToken(User user) {
        return JWT.create().withAudience(user.getUsername())
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    /**
     * @param username
     * @return
     */
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
