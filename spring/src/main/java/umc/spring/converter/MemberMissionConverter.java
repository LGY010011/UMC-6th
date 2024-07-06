package umc.spring.converter;

import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MissionResponseDTO;

public class MemberMissionConverter {

    public static MissionResponseDTO.MemberMissionResultDTO toMemberMissionResultDTO(MemberMission memberMission) {
        return MissionResponseDTO.MemberMissionResultDTO.builder()
                .memberMissionId(memberMission.getId())
                .createdAt(memberMission.getCreatedAt())
                .build();
    }

    public static MemberMission toMemberMission(Member member, Mission mission) {
        // memberList를 돌면서 memberMissionList를 만들어 반환한다.
        // memberList의 member를 memberMission의 member에 넣고 dDate와 missionStatus는 null로 초기화한다.

        return MemberMission.builder()
                        .dDate(null)
                        .missionStatus(MissionStatus.CHALLENGING)
                        .build();
    }
}
