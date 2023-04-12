package utc.edu.thesis.service;

import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.entity.Role;
import utc.edu.thesis.domain.dto.UserRequest;
import utc.edu.thesis.domain.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public interface UserService {

    UserResponse saveUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest, Long id);

    Role saveRole(Role role);

    UserResponse getCurrentUser();

    UserDetails getUserByUsername(String username);

    void addRoleToUser(String username, String roleName);

    Page<UserResponse> getAll(int page, int size, String[] sorts);

    Boolean deleteUser(Long id);
}
