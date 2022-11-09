package com.penpal.project.profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.penpal.project.board.DataNotFoundException;
import com.penpal.project.list.CountryList;
import com.penpal.project.list.LocationList;
import com.penpal.project.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileService {

	private final ProfileRepository profileRepository;

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
    		String language1, String language2, String language3
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
        
        this.profileRepository.save(p);
    }
    
 // 프로필 수정
    public void modify(
    		String nickname, String gender, int age,
    		String comment, Profile profile,
    		LocationList location, CountryList country,
    		String sns1, String sns2, String sns3,
    		String favorite1, String favorite2, String favorite3,
    		String language1, String language2, String language3
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
        
        this.profileRepository.save(p);
    }

}
