package umc.spring.web.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import umc.spring.validation.annotation.ExistCity;

public class RestaurantRequestDTO {
    @Getter
    public static class JoinRestaurantDTO{
        @Size(min = 2, max = 50)
        String name;
        @Size(min = 3, max = 12)
        String address;
        @ExistCity
        Long city;
    }


}
