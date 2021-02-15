package study.realWorld.service;

import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.userDtos.UserDto;
import study.realWorld.api.dto.userDtos.UserSignUpDto;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.User;

import java.util.Optional;

public interface UserService {
    UserDto signUp(UserSignUpDto userSignUpDto);

    User getMyUserWithAuthorities();
}
