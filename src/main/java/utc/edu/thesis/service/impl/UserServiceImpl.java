package utc.edu.thesis.service.impl;

import org.springframework.data.domain.Page;
import utc.edu.thesis.domain.dto.UserRequest;
import utc.edu.thesis.domain.dto.UserResponse;
import utc.edu.thesis.exception.request.BadRequestException;
import utc.edu.thesis.domain.entity.Role;
import utc.edu.thesis.domain.entity.User;
import utc.edu.thesis.repository.IRoleRepo;
import utc.edu.thesis.repository.IUserRepo;
import utc.edu.thesis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final IUserRepo userRepo;
    private final IRoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getById(Long id) {
        return userRepo.findById(id)
                .map(UserResponse::toDto)
                .orElseThrow(() -> {
                    throw new BadRequestException("UserId can not be found");
                });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(
                role -> authorities.add(new SimpleGrantedAuthority(role.getName()))
        );

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public UserResponse saveUser(UserRequest dto) {
        if (dto == null) {
            return null;
        }
        Role role = roleRepo.findByName("ROLE_USER");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(roles)
                .build();

        return UserResponse.toDto(userRepo.save(user));
    }

    @Override
    public UserResponse updateUser(UserRequest dto, Long id) {
        if (id == null) {
            throw new BadRequestException("User Id is null");
        }
        UserResponse responseDto = getById(id);

        if (dto != null) {
            User user = User.builder()
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .build();
            user.setId(responseDto.getId());

            return UserResponse.toDto(userRepo.save(user));
        }
        return null;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Boolean deleteUser(Long id) {
        if (id == null) {
            throw new BadRequestException("User Id is null");
        }
        // throw EntityNotfoundNotFound
        UserResponse responseDto = getById(id);
        if (responseDto != null) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String userName;
        if (principal instanceof UserDetails) {
            UserDetails userDetail = (UserDetails) principal;
            userName = userDetail.getUsername();
        } else {
            userName = principal.toString();
        }

        if (userName != null) {
            return UserResponse.toDto(userRepo.findByUsername(userName));
        }

        return null;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        UserResponse userDto = (UserResponse) getUserByUsername(username);
        User user = UserResponse.toEntity(userDto);

        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Page<UserResponse> getAll(int page, int size, String[] sorts) {
        return null;
    }

    @Override
    public UserDetails getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }
}
