package umc.spring.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import umc.spring.validation.annotation.ExistCategories;

import java.util.List;

public class MemberRequestDTO {
    @Getter
    public static class JoinDTO{
        @NotBlank
        String name;
        @NotNull
        Integer gender;
        @NotNull
        Integer birthYear;
        @NotNull
        Integer age;
        @Size(min = 3, max = 12)
        String address;

        @NotNull
        Long userCity;

        @ExistCategories
        List<Long> foodCategory;
    }
}
