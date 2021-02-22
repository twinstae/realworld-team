package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.profilesDtos.ProfileCreateDto;
import study.realWorld.api.dto.userDtos.UserDto;
import study.realWorld.api.dto.userDtos.UserSignInDto;
import study.realWorld.api.dto.userDtos.UserSignUpDto;
import study.realWorld.api.dto.userDtos.UserWithTokenDto;
import study.realWorld.entity.Authority;
import study.realWorld.entity.Profile;
import study.realWorld.entity.User;
import study.realWorld.jwt.TokenProvider;
import study.realWorld.repository.AuthorityRepository;
import study.realWorld.repository.ProfilesRepository;
import study.realWorld.repository.UserRepository;
import study.realWorld.util.SecurityUtil;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final ProfilesRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public UserWithTokenDto signIn(UserSignInDto userSignInDto){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userSignInDto.getEmail(), userSignInDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        return UserWithTokenDto
                .builder()
                .email(authentication.getName())
                .token(jwt)
                .build();
    }

    @Override
    public UserDto signUp(UserSignUpDto userSignUpDto) {
        checkUserAlreadyExist(userSignUpDto);
        User user = userRepository.save(userSignUpDto.toEntity(passwordEncoder, getUserAuthorities()));
        profileRepository.save(ProfileCreateDto.toEntity(user));
        return UserDto.fromUser(user);
    }

    @Transactional(readOnly = true)
    public Set<Authority> getUserAuthorities(){
        Authority userAuthority = authorityRepository.findByAuthorityName("ROLE_USER")
                .orElseGet(this::initAndGetUserAuthority);
        return Collections.singleton(userAuthority);
    }

    public Authority initAndGetUserAuthority(){
        return authorityRepository.save(new Authority("ROLE_USER"));
    }

    private void checkUserAlreadyExist(UserSignUpDto userSignUpDto) {
        userRepository.findOneByEmail(userSignUpDto.getEmail())
                .ifPresent((user)->{
                    throw new RuntimeException(user.getEmail() + "은 가입되어 있는 이메일입니다.");
                });
    }
    
    @Override
    @Transactional(readOnly = true)
    public User getUserWithAuthoritiesByEmail(String email) {
        return userRepository.findOneWithAuthoritiesByEmail(email)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public String getMyUserName(){
        return SecurityUtil.getCurrentUsername()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User getMyUser() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneByEmail)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByEmail)
                .orElseThrow(RuntimeException::new);
    }
}
