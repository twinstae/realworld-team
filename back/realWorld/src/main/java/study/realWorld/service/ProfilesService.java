package study.realWorld.service;

import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.profilesDtos.ProfileCreateDto;
import study.realWorld.api.dto.profilesDtos.ProfileDto;

public interface ProfilesService {

    ProfileDto findByUsername(String username);

   // ProfileDto save(ProfileCreateDto profileCreateDto);
}
