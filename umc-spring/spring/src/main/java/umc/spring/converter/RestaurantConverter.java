package umc.spring.converter;

import umc.spring.domain.Restaurant;
import umc.spring.web.dto.RestaurantRequestDTO;
import umc.spring.web.dto.RestaurantResponseDTO;

import java.util.ArrayList;

public class RestaurantConverter {

    public static RestaurantResponseDTO.JoinResultDTO toJoinResultDTO(Restaurant restaurant){
        return RestaurantResponseDTO.JoinResultDTO.builder()
                .restaurantId(restaurant.getId())
                .createdAt(restaurant.getCreatedAt())
                .build();
    }

    public static Restaurant toRestaurant(RestaurantRequestDTO.JoinRestaurantDTO request){
        return Restaurant.builder()
                .name(request.getName())
                .address(request.getAddress())
                .reviewList(new ArrayList<>())
                .build();
    }

}
