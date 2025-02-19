package site.haruhana.www.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import site.haruhana.www.entity.AuthProvider;
import site.haruhana.www.entity.Role;
import site.haruhana.www.entity.User;

@Getter
public class UserDto {

    private final Long id;

    private final String name;

    private final String email;

    private final AuthProvider provider;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String profileImageUrl;

    private final Role role;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.provider = user.getProvider();
        this.profileImageUrl = user.getProfileImageUrl();
        this.role = user.getRole();
    }
}
