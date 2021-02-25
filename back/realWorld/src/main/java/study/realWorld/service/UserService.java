package study.realWorld.service;

import study.realWorld.api.dto.userDtos.UserDto;
import study.realWorld.api.dto.userDtos.UserSignInDto;
import study.realWorld.api.dto.userDtos.UserSignUpDto;
import study.realWorld.api.dto.userDtos.UserWithTokenDto;
import study.realWorld.entity.Authority;
import study.realWorld.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    UserWithTokenDto signIn(UserSignInDto userSignInDto);
    UserDto signUp(UserSignUpDto userSignUpDto);

    Set<Authority> getUserAuthorities();

    User getMyUser();
    Optional<String> getMyUserName();
    User getMyUserWithAuthorities();
    User getUserWithAuthoritiesByEmail(String email);
}
