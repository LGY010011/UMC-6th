package umc.spring.service.RestaurantService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.converter.RestaurantConverter;
import umc.spring.domain.City;
import umc.spring.domain.Restaurant;
import umc.spring.repository.CityRepository;
import umc.spring.repository.RestaurantRepository;
import umc.spring.web.dto.RestaurantRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantCommandServiceImpl implements RestaurantCommandService{
    private final RestaurantRepository restaurantRepository;
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public Restaurant joinRestaurant(RestaurantRequestDTO.JoinRestaurantDTO request) {

        Restaurant newRestaurant = RestaurantConverter.toRestaurant(request);

        City city = cityRepository.findById(request.getCity()).orElseThrow();
        newRestaurant.setCity(city); //Restaurant에 City를 set한다.


        return restaurantRepository.save(newRestaurant);

    }


}
