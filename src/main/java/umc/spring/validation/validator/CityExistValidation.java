package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.validation.annotation.ExistCategories;
import umc.spring.service.MemberService.MemberCommandServiceImpl;
import umc.spring.validation.annotation.ExistCity;

@Component
@RequiredArgsConstructor
public class CityExistValidation implements ConstraintValidator<ExistCity, Long> {
    private final MemberCommandServiceImpl memberCommandService;

    @Override
    public void initialize(ExistCity constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long longs, ConstraintValidatorContext context) {

        boolean isValid = memberCommandService.cityExist(longs);
        if(!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.CITY_NOT_FOUND.toString())
                    .addConstraintViolation();
        }

        return isValid;
    }
}
