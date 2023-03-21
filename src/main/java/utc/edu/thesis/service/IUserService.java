package utc.edu.thesis.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import utc.edu.thesis.domain.entity.User;

public interface IUserService extends IService<User>, UserDetailsService {
    User findByUserName(String username);

    UserDetails loadUserById(long id);

    boolean checkLogin(User user);

    boolean isRegister(User user);

    boolean isCorrectConfirmPassword(User user);

    boolean existsByUsername(String username);
}
