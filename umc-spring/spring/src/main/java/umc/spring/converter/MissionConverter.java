package umc.spring.converter;

import org.springframework.data.domain.Page;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.web.dto.MissionRequestDTO;
import umc.spring.web.dto.MissionResponseDTO;
import umc.spring.web.dto.ReviewResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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



    public static MissionResponseDTO.MissionPreViewListDTO toMissionPreViewListDTO(Page<Mission> missionList){

        List<MissionResponseDTO.MissionPreViewDTO> missionPreViewDTOList = missionList.stream()
                .map(MissionConverter::toMissionViewDTO)
                .collect(Collectors.toList()  );

        return MissionResponseDTO.MissionPreViewListDTO.builder()
                .isLast(missionList.isLast())
                .isFirst(missionList.isFirst())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(missionPreViewDTOList.size())
                .missionList(missionPreViewDTOList)
                .build();
    }

    public static MissionResponseDTO.MissionPreViewDTO toMissionViewDTO(Mission mission){

        return MissionResponseDTO.MissionPreViewDTO.builder()
                .restaurantName(mission.getRestaurant().getName())
                .point(mission.getPoint())
                .body(mission.getDescription())
                .createdAt(mission.getCreatedAt().toLocalDate())
                .build();
    }
}
