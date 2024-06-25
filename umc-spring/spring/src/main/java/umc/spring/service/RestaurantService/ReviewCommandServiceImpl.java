package umc.spring.service.RestaurantService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Restaurant;
import umc.spring.domain.Review;
import umc.spring.repository.RestaurantRepository;
import umc.spring.web.dto.ReviewRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewCommandServiceImpl implements ReviewCommandService {
    private final RestaurantRepository restaurantRepository;


    @Override
    @Transactional
    public Review joinReview(ReviewRequestDTO.JoinReviewDTO request) {
        Review newReview = ReviewConverter.toReview(request);
        Restaurant restaurant=restaurantRepository.findById(request.getRestaurantId()).orElseThrow();
        restaurant.addReview(newReview); //addReview하면 Review에 Restaurant가 set된다.

        return newReview;
    }

    public boolean restaurantExist(Long restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }
}
