package site.haruhana.www.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MonthValidator implements ConstraintValidator<ValidMonth, Integer> {

    @Override
    public boolean isValid(Integer month, ConstraintValidatorContext context) {
        // null 여부는 별도의 @NotNull 등으로 처리할 수 있으므로, 여기서는 null이면 유효하다고 처리합니다.
        if (month == null) {
            return true;
        }
        return month >= 1 && month <= 12;
    }
}

