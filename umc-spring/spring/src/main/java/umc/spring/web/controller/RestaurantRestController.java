package umc.spring.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberMissionConverter;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.MissionService.MissionCommandService;
import umc.spring.service.RestaurantService.RestaurantQueryService;
import umc.spring.service.RestaurantService.ReviewCommandService;
import umc.spring.converter.RestaurantConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Restaurant;
import umc.spring.domain.Review;
import umc.spring.service.RestaurantService.RestaurantCommandService;
import umc.spring.validation.annotation.CheckPage;
import umc.spring.validation.annotation.ExistRestaurant;
import umc.spring.web.dto.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/restaurants")
public class RestaurantRestController {
    private final RestaurantCommandService restaurantCommandService;
    private final ReviewCommandService reviewCommandService;
    private final MissionCommandService missionCommandService;
    private final RestaurantQueryService restaurantQueryService;

    //가게 추가 API
    @PostMapping("/")
    public ApiResponse<RestaurantResponseDTO.JoinResultDTO> join(@RequestBody @Valid
                                                                 RestaurantRequestDTO.JoinRestaurantDTO request){
        Restaurant restaurant=restaurantCommandService.joinRestaurant(request);

        return ApiResponse.onSuccess(RestaurantConverter.toJoinResultDTO(restaurant));
    }

    //리뷰 추가 API
    @PostMapping("/{restaurantId}/review")
    @Parameters({
            @Parameter(name="restaurantId",description = "가게의 id, path variable 입니다")
    })
    public ApiResponse<ReviewResponseDTO.ReviewResultDTO> review( @PathVariable(name = "restaurantId") @ExistRestaurant
                                                                     Long restaurantId,
                                                                     @RequestBody @Valid
                                                                    ReviewRequestDTO.JoinReviewDTO request){
        //리뷰 요청을 처리하는 메소드
        //리뷰에 있는 restaurantId를 이용하여 리뷰할 식당을 찾아 리뷰를 추가한다.
        Review review= reviewCommandService.joinReview(request,restaurantId);

        return ApiResponse.onSuccess(ReviewConverter.toReviewResultDTO(review));
    }

    //미션 추가 API
    @PostMapping("/{restaurantId}/newMission")
    @Parameters({
            @Parameter(name="restaurantId",description = "가게의 id, path variable 입니다")
    })
    public ApiResponse<MissionResponseDTO.JoinResultDTO> join( @PathVariable(name = "restaurantId") @ExistRestaurant
                                                                   Long restaurantId,
                                                               @RequestBody @Valid
                                                              MissionRequestDTO.JoinMissionDTO request){
        Mission mission=missionCommandService.addMission(request,restaurantId);

        return ApiResponse.onSuccess(MissionConverter.toJoinResultDTO(mission));
    }

    //가게의 미션을 도전하는 API
    @PostMapping("/tryMission")
    public ApiResponse<MissionResponseDTO.MemberMissionResultDTO> mission(@RequestBody @Valid
                                        MissionRequestDTO.ChallengeMissionDTO request){

        MemberMission memberMission=missionCommandService.addMemberMission(request.getMissionId(), request.getMemberId());

        return ApiResponse.onSuccess(MemberMissionConverter.toMemberMissionResultDTO(memberMission));
    }


    //리뷰 목록 조회 API
    @GetMapping("/{restaurantId}/reviews")
    @Operation(summary="특정 가게의 리뷰 목록 조회 API",description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({ //Api 응답들을 담는다. 이때 에러 상황은 content를 지정하였고, 성공은 content 지정 없이 ApiResponse를 반환한다.
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name="restaurantId",description = "가게의 id, path variable 입니다"),
            @Parameter(name="page",description = "페이지 번호, 0번이 1 페이지 입니다.")
    })
    public ApiResponse<ReviewResponseDTO.ReviewPreViewListDTO> getReviews( @PathVariable(name = "restaurantId") @ExistRestaurant Long restaurantId, @RequestParam(name = "page") @CheckPage  Integer page){
        restaurantQueryService.getReviewList(restaurantId,page-1);
        //확인용 반환값
        //return ApiResponse.onSuccess(ReviewConverter.toReviewPreViewListDTO(restaurantQueryService.getReviewList(restaurantId,page-1)));
        return null;
    }


    //미션 목록 조회 API
    @GetMapping("/{restaurantId}/missions")
    @Operation(summary="특정 가게의 미션 목록 조회 API",description = "특정 가게의 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({ //Api 응답들을 담는다. 이때 에러 상황은 content를 지정하였고, 성공은 content 지정 없이 ApiResponse를 반환한다.
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name="restaurantId",description = "가게의 id, path variable 입니다"),
            @Parameter(name="page",description = "페이지 번호, 0번이 1 페이지 입니다.")
    })
    public ApiResponse<MissionResponseDTO.MissionPreViewListDTO> getMissions( @PathVariable(name = "restaurantId") @ExistRestaurant Long restaurantId, @RequestParam(name = "page") @CheckPage  Integer page){
        restaurantQueryService.getMissionList(restaurantId, page-1);
        //확인용 반환값
        return ApiResponse.onSuccess(MissionConverter.toMissionPreViewListDTO(restaurantQueryService.getMissionList(restaurantId,page-1)));
        //return null;
    }


}
