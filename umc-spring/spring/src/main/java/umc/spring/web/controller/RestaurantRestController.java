package umc.spring.web.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberMissionConverter;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.MissionService.MissionCommandService;
import umc.spring.service.RestaurantService.ReviewCommandService;
import umc.spring.converter.RestaurantConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Restaurant;
import umc.spring.domain.Review;
import umc.spring.service.RestaurantService.RestaurantCommandService;
import umc.spring.web.dto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantRestController {
    private final RestaurantCommandService restaurantCommandService;
    private final ReviewCommandService reviewCommandService;
    private final MissionCommandService missionCommandService;

    //가게 추가 API
    @PostMapping("/")
    public ApiResponse<RestaurantResponseDTO.JoinResultDTO> join(@RequestBody @Valid
                                                                 RestaurantRequestDTO.JoinRestaurantDTO request){
        Restaurant restaurant=restaurantCommandService.joinRestaurant(request);

        return ApiResponse.onSuccess(RestaurantConverter.toJoinResultDTO(restaurant));
    }

    //리뷰 추가 API
    @PostMapping("/review")
    public ApiResponse<ReviewResponseDTO.ReviewResultDTO> review(@RequestBody @Valid
                                                                    ReviewRequestDTO.JoinReviewDTO request){
        //리뷰 요청을 처리하는 메소드
        //리뷰에 있는 restaurantId를 이용하여 리뷰할 식당을 찾아 리뷰를 추가한다.
        Review review= reviewCommandService.joinReview(request);

        return ApiResponse.onSuccess(ReviewConverter.toReviewResultDTO(review));
    }

    //가게의 미션을 도전하는 API
    @PostMapping("/mission")
    public ApiResponse<MissionResponseDTO.MemberMissionResultDTO> mission(@RequestBody @Valid
                                        MissionRequestDTO.ChallengeMissionDTO request){

        MemberMission memberMission=missionCommandService.addMemberMission(request.getMissionId(), request.getMemberId());

        return ApiResponse.onSuccess(MemberMissionConverter.toMemberMissionResultDTO(memberMission));
    }

}
