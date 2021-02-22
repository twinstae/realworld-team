package study.realWorld.service;

import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.profilesDtos.ProfileCreateDto;
import study.realWorld.api.dto.profilesDtos.ProfileDto;

public interface ProfilesService {

    ProfileDto findByUsername(String username);

    @Transactional
    ProfileDto addFollowByUsername(String username);

    // ProfileDto save(ProfileCreateDto profileCreateDto);
}
