package utc.edu.thesis.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import utc.edu.thesis.domain.entity.Role;
import utc.edu.thesis.domain.entity.User;
import utc.edu.thesis.util.Constants;

import java.util.Set;

@Getter
@Setter
@ToString
public class UserResponse {
    private Long id;
    private String username;
    private Set<Role> roles;

    public static User toEntity(UserResponse dto) {
        return Constants.map().convertValue(dto, User.class);
    }

    public static UserResponse toDto(User entity) {
        return Constants.map().convertValue(entity, UserResponse.class);
    }
}
