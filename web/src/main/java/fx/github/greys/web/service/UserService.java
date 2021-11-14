package fx.github.greys.web.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.dto.UserDto;
import fx.github.greys.web.vo.UserVo;
import fx.github.greys.web.entity.system.User;
import fx.github.greys.web.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

import static fx.github.greys.web.constant.Constants.TOKEN_EXPIRE_TIME;

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
     * @param dto
     * @return
     */
    public GreysResponse<String> addUser(@RequestBody UserDto dto) {
        GreysResponse<String> result = GreysResponse.createSuccess();
        try {
            User user = new User();
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
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
    public GreysResponse<Page<User>> pageUser(Pageable pageable) {
        GreysResponse<Page<User>> result = GreysResponse.createSuccess();
        try {
            Page<User> users = userRepository.findAll(pageable);
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
    public GreysResponse<UserVo> login(String username, String password) {
        log.info("user login .username:{}", username);
        GreysResponse<UserVo> result = GreysResponse.createError("username or password not correct.");
        if (StringUtils.isAnyBlank(username, password)) {
            return result;
        }
        User user = userRepository.findByUsername(username);
        if (user == null || !StringUtils.equals(user.getPassword(), password)) {
            return result;
        }
        UserVo dto = convertToDto(user);
        result = GreysResponse.createSuccess(dto, "success");
        return result;
    }

    private UserVo convertToDto(User user) {
        UserVo dto = new UserVo();
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
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME))
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    /**
     * @param username
     * @return
     */
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    public GreysResponse<String> deleteUser(Long userId) {
        GreysResponse<String> result = GreysResponse.createSuccess();
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            result = GreysResponse.createError("delete user occurs error.");
            log.error("delete user occurs exception.userId:{}", userId, e);
        }
        return result;
    }
}
