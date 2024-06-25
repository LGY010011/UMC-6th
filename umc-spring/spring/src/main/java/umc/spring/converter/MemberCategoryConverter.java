package umc.spring.converter;

import umc.spring.domain.FoodCategory;
import umc.spring.domain.mapping.MemberCategory;

import java.util.List;
import java.util.stream.Collectors;

public class MemberCategoryConverter {
    public static List<MemberCategory> toMemberCategoryList(List<FoodCategory> foodCategoryList){
        return foodCategoryList.stream()
                .map(foodCategory ->
                    MemberCategory.builder()
                            .foodCategory(foodCategory)
                            .build()
                ).collect(Collectors.toList());
    }
}
