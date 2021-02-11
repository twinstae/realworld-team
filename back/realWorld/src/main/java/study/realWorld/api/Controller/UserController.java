package study.realWorld.api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.realWorld.api.dto.userDtos.UserDto;
import study.realWorld.api.dto.userDtos.UserResponseDto;
import study.realWorld.api.dto.userDtos.UserSignInDto;
import study.realWorld.api.dto.userDtos.UserSignUpDto;
import study.realWorld.service.UserService;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserSignUpDto userSignUpDto){
        UserDto userDto = userService.save(userSignUpDto);

        return createdResponseWithDto(userDto);
    }

    private ResponseEntity<UserResponseDto> createdResponseWithDto(UserDto userDto) {
        return new ResponseEntity<>(
                new UserResponseDto(userDto),
                new HttpHeaders(),
                HttpStatus.CREATED
        );
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> signIn(@RequestBody UserSignInDto userSignInDto){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userSignInDto.getUsername(), userSignInDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return ResponseEntity.ok(userWithTokenDto);
    }
}

// rest