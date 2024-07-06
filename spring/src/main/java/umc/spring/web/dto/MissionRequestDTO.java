package umc.spring.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import umc.spring.validation.annotation.ExistRestaurant;

public class MissionRequestDTO {

    @Getter
    public static class JoinMissionDTO {
        @NotNull
        Integer point;

        @Size(min = 3, max = 50)
        String description;

    }

    @Getter
    public static class ChallengeMissionDTO {
        @NotNull
        Long memberId;

        @NotNull
        Long missionId;

    }
}
