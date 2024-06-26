package umc.spring.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import umc.spring.validation.annotation.ExistRestaurant;

public class ReviewRequestDTO {
    @Getter
    public static class JoinReviewDTO{

        @Size(min = 2, max = 50)
        String title;
        @Size(min = 5, max = 100)
        String content;
        @NotNull
        Float score;

        @ExistRestaurant
        Long restaurantId;

    }
}
