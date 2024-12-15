package utc.edu.thesis.service.impl;

import org.junit.jupiter.api.Test;
import utc.edu.thesis.domain.dto.UserDto;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private final UserServiceImpl userService;

    UserServiceImplTest(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Test
    void saveUser() {
        var user = UserDto.builder()
                .username("admin")
                .password("123456")
                .email("asidsdamad1@gmail.com")
                .build();
        userService.saveUser(user);
    }
}