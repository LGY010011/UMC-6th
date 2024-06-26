package umc.spring.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;
import umc.spring.apiPayload.code.BaseErrorCode;
import umc.spring.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    //추가한 거
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST,"TEMP4001","테스트ing"),

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST,"MEMBER4001","사용자가 없습니다"),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST,"MEMBER4002","닉네임은 필수입니다"),

    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND,"ARTICLE4001","게시물이 없습니다"),

    FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"CATEGORY4001","해당하는 카테고리가 존재하지 않습니다."),

    CITY_NOT_FOUND(HttpStatus.NOT_FOUND,"CITY4001","해당하는 지역이 없습니다"),

    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND,"RESTAURANT4001","해당하는 식당이 없습니다"),
    REVIEW_NOT_APPEND(HttpStatus.BAD_REQUEST,"REVIEW4001","리뷰가 추가되지 않았습니다."),
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND,"MISSION4002","해당하는 미션이 없습니다"),
    ;



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .message(message)
                .code(code)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .message(message)
                .code(code)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}