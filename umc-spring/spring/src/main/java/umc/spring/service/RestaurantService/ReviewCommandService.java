package umc.spring.service.RestaurantService;

import umc.spring.domain.Review;
import umc.spring.web.dto.ReviewRequestDTO;

public interface ReviewCommandService {
    public Review joinReview(ReviewRequestDTO.JoinReviewDTO request,Long restaurantId);
}
