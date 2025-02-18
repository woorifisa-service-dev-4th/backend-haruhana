package site.haruhana.www.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import site.haruhana.www.entity.AuthProvider;
import site.haruhana.www.entity.Role;
import site.haruhana.www.entity.User;

@Getter
@JsonPropertyOrder({"id", "name", "email", "provider", "profileImageUrl", "role", "tokens"})
public class LoginResponseDto {
    // 사용자 정보
    private final Long id;
    private final String name;
    private final String email;
    private final AuthProvider provider;
    private final String profileImageUrl;
    private final Role role;

    // 토큰 정보
    private final TokenDto tokens;

    public LoginResponseDto(User user, TokenDto tokens) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.provider = user.getProvider();
        this.profileImageUrl = user.getProfileImageUrl();
        this.role = user.getRole();
        this.tokens = tokens;
    }
}
