package utc.edu.thesis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utc.edu.thesis.domain.entity.User;
import utc.edu.thesis.domain.entity.UserPrinciple;
import utc.edu.thesis.repository.IUserRepo;
import utc.edu.thesis.service.IUserService;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepo iUserRepo;

    @Override
    public User save(User user) {
        return iUserRepo.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return iUserRepo.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return iUserRepo.findById(id);
    }

    @Override
    public void delete(Long id) {
        iUserRepo.deleteById(id);
    }

    @Override
    public User findByUserName(String username) {
        return iUserRepo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserById(long id) {
        Optional<User> user = iUserRepo.findById(id);
        if(!user.isPresent()){
            throw new NullPointerException();
        }
        return UserPrinciple.build(user.get());
    }

    @Override
    public boolean checkLogin(User user) {
        Iterable<User>users = this.findAll();
        for (User u : users){
            if(u.getUsername().equals(user.getUsername())
                    && u.getPassword().equals(user.getPassword())
                    && u.isEnabled()){
                return true;

            }
        }
        return false;
    }

    @Override
    public boolean isRegister(User user) {
        Iterable<User> users = this.findAll();
        for (User u : users){
            if(u.getUsername().equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCorrectConfirmPassword(User user) {
        return user.getPassword().equals(user.getConfirmPassword());
    }

    @Override
    public boolean existsByUsername(String username) {
        return iUserRepo.existsByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = iUserRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        if (this.checkLogin(user)) {
            return UserPrinciple.build(user);
        }
        boolean enable = false;
        boolean accountNonExpired = false;
        boolean credentialsNonExpired = false;
        boolean accountNonLocked = false;
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), enable, accountNonExpired, credentialsNonExpired,
                accountNonLocked, null);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String userName = null;
        if (principal instanceof UserDetails) {
            UserDetails userDetail = (UserDetails)principal;
            userName = userDetail.getUsername();
        } else {
            userName = principal.toString();
        }

        if (userName != null) {
            return this.iUserRepo.findByUsername(userName);
        }

        return null;
    }
}
