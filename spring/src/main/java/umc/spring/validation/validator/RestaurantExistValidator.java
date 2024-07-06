package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.service.RestaurantService.ReviewCommandServiceImpl;
import umc.spring.validation.annotation.ExistRestaurant;


@Component
@RequiredArgsConstructor
public class RestaurantExistValidator implements ConstraintValidator<ExistRestaurant, Long> {
    private final ReviewCommandServiceImpl reviewCommandService;

    @Override
    public void initialize(ExistRestaurant constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        //restaurantRepository에 해당 id가 존재한다면 isValid는 true를 반환한다.
        boolean isValid = reviewCommandService.restaurantExist(value);
        if(!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.RESTAURANT_NOT_FOUND.toString())
                    .addConstraintViolation();
        }
        return isValid;
    }
}
