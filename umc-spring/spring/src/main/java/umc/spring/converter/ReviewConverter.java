package umc.spring.converter;

import umc.spring.domain.Review;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

public class ReviewConverter {

        public static ReviewResponseDTO.ReviewResultDTO toReviewResultDTO(Review review){
            return ReviewResponseDTO.ReviewResultDTO.builder()
                    .reviewId(review.getId())
                    .createdAt(review.getCreatedAt())
                    .build();
        }

        public static Review toReview(ReviewRequestDTO.JoinReviewDTO request){
            return Review.builder()
                    .title(request.getTitle())
                    .body(request.getContent())
                    .score(request.getScore())
                    .build();

        }
}
