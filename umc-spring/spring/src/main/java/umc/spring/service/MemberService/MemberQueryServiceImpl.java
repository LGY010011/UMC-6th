package umc.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Restaurant;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.domain.Review;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.MissionRepository;
import umc.spring.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService{
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    @Override
    public Optional<Member> findMember(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Page<Review> getReviewList(Long memberId, Integer page) {
        Member member = memberRepository.findById(memberId).get();//member 가져오기
        Page<Review> memberReviewPage=reviewRepository.findAllByMember(member, PageRequest.of(page, 10));
        return memberReviewPage;
    }

    @Override
    public Page<Mission> getChallengeMissionList(Long memberId, Integer page) {
        Member member = memberRepository.findById(memberId).get();//member 가져오기
        //member로 MemberMission들 가져오기
        Page<MemberMission> memberMissions = memberMissionRepository.findAllByMemberAndMissionStatus(member, MissionStatus.CHALLENGING,PageRequest.of(page, 10));

        //가져온 memberMission들로 missionID들 가져오기
        List<Long> missionIds = memberMissions.stream()
                .map(MemberMission::getMission)
                .map(Mission::getId)
                .collect(Collectors.toList()); //List로 받기

        //가져온 missionID들로 mission 가져오기
        Page<Mission> missionPage=missionRepository.findAllByIdIn(missionIds, PageRequest.of(page, 10));
        return missionPage;

    }


}
