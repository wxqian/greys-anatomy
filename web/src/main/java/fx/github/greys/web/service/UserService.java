package fx.github.greys.web.service;

import fx.github.greys.web.dto.GreysResponse;
import fx.github.greys.web.entity.User;
import fx.github.greys.web.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

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


    public GreysResponse<List<User>> listUser() {
        GreysResponse<List<User>> result = GreysResponse.createSuccess();
        try {
            List<User> users = userRepository.findAll();
            result.setData(users);
        } catch (Exception e) {
            result = GreysResponse.createError("list user occurs error");
            log.error("list user occurs exception.", e);
        }
        return result;
    }
}
