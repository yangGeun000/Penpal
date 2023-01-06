package com.penpal.project.profile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.penpal.project.board.DataNotFoundException;
import com.penpal.project.list.CountryList;
import com.penpal.project.list.LocationList;
import com.penpal.project.member.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


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
				Join<Profile, LocationList> locationTable = profile.join("location", JoinType.INNER);
				Join<Profile, CountryList> countryTable = profile.join("country", JoinType.INNER);
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
    public void create(
    		String nickname, String gender, int age,
    		String comment, Member member,
    		LocationList location, CountryList country,
    		String sns1, String sns2, String sns3,
    		String favorite1, String favorite2, String favorite3,
    		String language1, String language2, String language3,
    		MultipartFile picture
    		) {
    	System.out.println("service");
        Profile p = new Profile();
        p.setNickname(nickname);
        p.setGender(gender);
        p.setAge(age);
        p.setComment(comment);
        p.setMember(member);
        p.setLocation(location);
        p.setCountry(country);
        p.setSns1(sns1);
        p.setSns2(sns2);
        p.setSns3(sns3);
        p.setFavorite1(favorite1);
        p.setFavorite2(favorite2);
        p.setFavorite3(favorite3);
        p.setLanguage1(language1);
        p.setLanguage2(language2);
        p.setLanguage3(language3);
        p.setLastDate(LocalDateTime.now());
        if(!picture.isEmpty()) {
        	p.setUrl(savePicture(picture));
        	log.info(p.getUrl());
        }
        this.profileRepository.save(p);
    }
    
 // 프로필 수정
    public void modify(
    		String nickname, String gender, int age,
    		String comment, Profile profile,
    		LocationList location, CountryList country,
    		String sns1, String sns2, String sns3,
    		String favorite1, String favorite2, String favorite3,
    		String language1, String language2, String language3,
    		MultipartFile picture
    		) {
    	System.out.println("service");
        Profile p = profile;
        p.setNickname(nickname);
        p.setGender(gender);
        p.setAge(age);
        p.setComment(comment);
        p.setLocation(location);
        p.setCountry(country);
        p.setSns1(sns1);
        p.setSns2(sns2);
        p.setSns3(sns3);
        p.setFavorite1(favorite1);
        p.setFavorite2(favorite2);
        p.setFavorite3(favorite3);
        p.setLanguage1(language1);
        p.setLanguage2(language2);
        p.setLanguage3(language3);
        if(!picture.isEmpty()) {
        	p.setUrl(savePicture(picture));
        	log.info(p.getUrl());
        }
        
        this.profileRepository.save(p);
    }
    
    public String savePicture(MultipartFile picture) {
    	
    	// 원래 파일 이름 추출
        String origName = picture.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

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
        	log.info("파일 저장 오류");
        }
        
        return savedPath;
    }
    
    // by 구양근, 최근 프로필 5개 
    public List<Profile> recentProfile(){
    	List<Profile> profileList = this.profileRepository.findTop5ByOrderByIdDesc();
    	return profileList;
    }

}
