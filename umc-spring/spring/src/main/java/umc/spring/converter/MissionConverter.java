package umc.spring.converter;

import umc.spring.domain.Mission;
import umc.spring.web.dto.MissionRequestDTO;
import umc.spring.web.dto.MissionResponseDTO;

import java.util.ArrayList;


public class MissionConverter {

    public static MissionResponseDTO.JoinResultDTO toJoinResultDTO(Mission mission){
        return MissionResponseDTO.JoinResultDTO.builder()
                .missionId(mission.getId())
                .createdAt(mission.getCreatedAt())
                .build();
    }

    public static Mission toMission(MissionRequestDTO.JoinMissionDTO request){
        //MissionRequestDTO를 Mission으로 변환한다.
        return Mission.builder()
                .point(request.getPoint())
                .description(request.getDescription())
                .memberMissionList(new ArrayList<>())
                .build();
    }

}
