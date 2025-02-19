package site.haruhana.www.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.haruhana.www.validation.Email;
import site.haruhana.www.validation.Name;
import site.haruhana.www.validation.Password;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @Name
    private String name;

    @Email
    private String email;

    @Password
    private String password;
}
