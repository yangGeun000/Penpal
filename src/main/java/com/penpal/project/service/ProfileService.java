package com.penpal.project.service;

import com.penpal.project.DataNotFoundException;
import com.penpal.project.domain.Member;
import com.penpal.project.domain.Profile;
import com.penpal.project.domain.list.Country;
import com.penpal.project.domain.list.Location;
import com.penpal.project.dto.ProfileForm;
import com.penpal.project.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
@Slf4j
public class ProfileService {

	private final ProfileRepository profileRepository;
	
	@Value("${upload.path}")
	private String uploadPath;

	// by 장유란, 프로필 검색 기능
	public Page<Profile> getList(int page, String kw, String location, String country) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id")); // 날짜 관련(lastDate)으로 변경예정
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Profile> spec = search(kw, location, country);

		return this.profileRepository.findAll(spec, pageable);
	}

	private Specification<Profile> search(String kw, String location, String country) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;

			@Override //프로필 생성문제로 검색기능 유연화
			public Predicate toPredicate(Root<Profile> profile, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복을 제거
				Join<Profile, Member> memberTable = profile.join("member", JoinType.INNER);
				Join<Profile, Location> locationTable = profile.join("location", JoinType.INNER);
				Join<Profile, Country> countryTable = profile.join("country", JoinType.INNER);
				return cb.and(cb.like(memberTable.get("name"), "%" + kw + "%"), // 멤버명
						cb.like(locationTable.get("name"), "%" + location + "%"), // location
						cb.like(countryTable.get("name"), "%" + country + "%")); // country
			}
		};
	}
	

	// 프로필 상세 조회
	public Profile getProfile(Integer id) {
		Optional<Profile> profile = this.profileRepository.findById(id);
		if (profile.isPresent()) {
			return profile.get();
		} else {
			throw new DataNotFoundException("유효하지 않은 프로필입니다.");
		}
	}
	
	// 프로필 생성
    public void create(ProfileForm profileForm, Member member, Location location, Country country) {
        Profile profile = profileForm.convertFormToProfile();
		profile.setMember(member);
		profile.setLocation(location);
		profile.setCountry(country);

		if(!profileForm.getPicture().isEmpty()) {
			profile.setUrl(savePicture(profileForm.getPicture()));
		}

        this.profileRepository.save(profile);
    }
    
 // 프로필 수정
    public void modify(ProfileForm profileForm, Profile profile, Location location, Country country) {
		profile.modify(profileForm);
		profile.setLocation(location);
		profile.setCountry(country);

        if(!profileForm.getPicture().isEmpty()) {
        	profile.setUrl(savePicture(profileForm.getPicture()));
        }
        
        this.profileRepository.save(profile);
    }

	// by 구양근, 프로필 이미지 저장
    public String savePicture(MultipartFile picture) {
    	
    	// 원래 파일 이름 추출
        String originalFilename = picture.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath =  uploadPath + savedName;

        // 실제로 로컬에 uuid를 파일명으로 저장
        try {
        	picture.transferTo(new File(savedPath));
        }
        catch(Exception e){
        	e.printStackTrace();
        	log.error("프로필 이미지 저장 오류");
        }
        // db 저장은 이름만 저장 (경로는 숨김)
        return savedName;
    }
    
    // by 구양근, 최근 프로필 5개 
    public List<Profile> recentProfile(){
    	List<Profile> profileList = this.profileRepository.findTop5ByOrderByIdDesc();
    	return profileList;
    }

}
