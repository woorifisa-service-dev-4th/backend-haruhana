package site.haruhana.www.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = MonthValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface ValidMonth {
    String message() default "월은 1월부터 12월 사이여야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
