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
import umc.spring.converter.MemberConverter;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Member;
import umc.spring.service.MemberService.MemberCommandService;
import umc.spring.service.MemberService.MemberQueryService;
import umc.spring.validation.annotation.CheckPage;
import umc.spring.validation.annotation.ExistMember;
import umc.spring.validation.annotation.ExistRestaurant;
import umc.spring.web.dto.MemberRequestDTO;
import umc.spring.web.dto.MemberResponseDTO;
import umc.spring.web.dto.MissionResponseDTO;
import umc.spring.web.dto.ReviewResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class MemberRestController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid
                                                             MemberRequestDTO.JoinDTO request){
        Member member=memberCommandService.joinMember(request);

        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    //리뷰 목록 조회 API
    @GetMapping("/{memberId}/reviews")
    @Operation(summary="작성한 리뷰 목록 조회 API",description = "회원이 작성한 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({ //Api 응답들을 담는다. 이때 에러 상황은 content를 지정하였고, 성공은 content 지정 없이 ApiResponse를 반환한다.
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters ({
        @Parameter(name="memberId",description = "회원의 id, path variable 입니다"),
        @Parameter (name = "page", description = "페이지 번호, 0번이 1 페이지 입니다.")
    })
    public ApiResponse<ReviewResponseDTO.ReviewPreViewListDTO> getReviews( @PathVariable(name="memberId") @ExistMember Long memberId, @RequestParam(name = "page")  @CheckPage Integer page){
        memberQueryService.getReviewList(memberId,page-1);
        return ApiResponse.onSuccess(ReviewConverter.toReviewPreViewListDTO(memberQueryService.getReviewList(memberId,page-1)));
    }

    //도전 중인 미션 목록 조회 API
    @GetMapping("/{memberId}/myMissions")
    @Operation(summary="도전 중인 미션 목록 조회 API",description = "회원이 도전 중인 미션의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({ //Api 응답들을 담는다. 이때 에러 상황은 content를 지정하였고, 성공은 content 지정 없이 ApiResponse를 반환한다.
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters ({
            @Parameter(name="memberId",description = "회원의 id, path variable 입니다"),
            @Parameter (name = "page", description = "페이지 번호, 0번이 1 페이지 입니다.")
    })
    public ApiResponse<MissionResponseDTO.MissionPreViewListDTO> getChallengeMissions(@PathVariable(name="memberId") @ExistMember Long memberId, @RequestParam(name = "page")  @CheckPage Integer page){

        memberQueryService.getChallengeMissionList(memberId,page-1);
        return ApiResponse.onSuccess(MissionConverter.toMissionPreViewListDTO(memberQueryService.getChallengeMissionList(memberId,page-1)));
    }

}
