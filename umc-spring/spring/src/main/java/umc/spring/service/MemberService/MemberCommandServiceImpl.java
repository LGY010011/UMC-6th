package umc.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.converter.MemberCategoryConverter;
import umc.spring.converter.MemberConverter;
import umc.spring.domain.City;
import umc.spring.domain.FoodCategory;
import umc.spring.domain.Member;
import umc.spring.domain.mapping.MemberCategory;
import umc.spring.repository.CityRepository;
import umc.spring.repository.FoodCategoryRepository;
import umc.spring.repository.MemberRepository;
import umc.spring.web.dto.MemberRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public Member joinMember(MemberRequestDTO.JoinDTO request) {
        Member newMember = MemberConverter.toMember(request);
        // 요청에서 받은 FoodCategory 리스트를 엔티티로 변환 (예외처리 없이 바로 변환)
        List<FoodCategory> foodCategoryList = request.getFoodCategory().stream()//요청에서 받은 FoodCategory 리스트를 엔티티로 변환
                .map(foodCategoryId -> foodCategoryRepository.getOne(foodCategoryId))//foodCategoryId를 통해 foodCategory를 찾는다.
                .collect(Collectors.toList());//리스트로 변환

        List<MemberCategory> memberCategoryList= MemberCategoryConverter.toMemberCategoryList(foodCategoryList);
        memberCategoryList.forEach(memberCategory -> {memberCategory.setMember(newMember);});

        return memberRepository.save(newMember);
    }

    public boolean categoriesExist(List<Long> categoryIds) {
        return categoryIds.stream()
                .allMatch(foodCategoryRepository::existsById);
    }

    public boolean cityExist(Long cityId) {
        return cityRepository.existsById(cityId);
    }

    public boolean memberExist(Long memberId) {
        return memberRepository.existsById(memberId);
    }

}
