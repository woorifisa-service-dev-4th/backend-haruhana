package site.haruhana.www.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@NotBlank(message = "이름은 2자 이상 20자 이하로 입력해주세요.")
@Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface Name {
    String message() default "이름 형식이 올바르지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
