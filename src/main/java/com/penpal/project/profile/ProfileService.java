package com.penpal.project.profile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileService {

	private final ProfileRepository profileRepository;
	
	public Page<Profile> getList(int page, String kw, String location, String country) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));	//나중에 lastDate로 변경 필요
		Pageable pageable = PageRequest.of(page, 6, Sort.by(sorts));
		Page<Profile> searchList = this.profileRepository.findAllByKeywordCategory(kw, pageable, country, location);

		return searchList;
	}

}
