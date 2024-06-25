package umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.MissionHandler;
import umc.spring.converter.MemberMissionConverter;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Restaurant;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.MissionRepository;
import umc.spring.repository.RestaurantRepository;
import umc.spring.web.dto.MissionRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionCommandServiceImpl implements MissionCommandService{
    private final RestaurantRepository restaurantRepository;
    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;

    @Override
    @Transactional
    public Mission addMission(MissionRequestDTO.JoinMissionDTO request) {
        Mission mission = MissionConverter.toMission(request);
        //가게 찾아서 mission 추가
        Restaurant restaurant=restaurantRepository.findById(request.getRestaurantId()).orElseThrow(
                () -> new MissionHandler(ErrorStatus.RESTAURANT_NOT_FOUND)
        );
        restaurant.addMission(mission);

        return missionRepository.save(mission);
    }

    @Override
    @Transactional
    public MemberMission addMemberMission(Long missionId, Long memberId) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new MissionHandler(ErrorStatus.MISSION_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MissionHandler(ErrorStatus.RESTAURANT_NOT_FOUND));

        //memberMission 생성
        MemberMission memberMission = MemberMissionConverter.toMemberMission(member, mission);

        memberMission.addMemberMission(mission, member);

        return memberMissionRepository.save(memberMission);
    }


}
