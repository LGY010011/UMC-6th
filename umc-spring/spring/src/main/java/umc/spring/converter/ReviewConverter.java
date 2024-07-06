package umc.spring.converter;

import org.springframework.data.domain.Page;
import umc.spring.domain.Review;
import umc.spring.web.dto.RestaurantResponseDTO;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

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

        public static ReviewResponseDTO.ReviewPreViewListDTO toReviewPreViewListDTO(Page<Review> reviewList){

            List<ReviewResponseDTO.ReviewPreViewDTO> reviewPreViewDTOList = reviewList.stream()
                    .map(ReviewConverter::toReviewPreViewDTO)
                    .collect(Collectors.toList()  );

            return ReviewResponseDTO.ReviewPreViewListDTO.builder()
                    .isLast(reviewList.isLast())
                    .isFirst(reviewList.isFirst())
                    .totalPage(reviewList.getTotalPages())
                    .totalElements(reviewList.getTotalElements())
                    .listSize(reviewPreViewDTOList.size())
                    .reviewList(reviewPreViewDTOList)
                    .build();
        }

        public static ReviewResponseDTO.ReviewPreViewDTO toReviewPreViewDTO(Review review){

            return ReviewResponseDTO.ReviewPreViewDTO.builder()
                    .ownerNickname(review.getMember().getName())
                    .score(review.getScore())
                    .body(review.getBody())
                    .createdAt(review.getCreatedAt().toLocalDate())
                    .build();
        }


}
