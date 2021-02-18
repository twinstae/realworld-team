package study.realWorld.service;

import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.profilesDtos.ProfileDto;

public interface ProfilesService {

    ProfileDto findByUsername(String username);
}
