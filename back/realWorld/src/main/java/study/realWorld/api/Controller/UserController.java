package study.realWorld.api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.realWorld.api.dto.userDtos.*;
import study.realWorld.jwt.TokenProvider;
import study.realWorld.service.UserService;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> signUp(
            @RequestBody UserSignUpDto userSignUpDto){
        UserDto userDto = userService.signUp(userSignUpDto);
        return new ResponseEntity<>(
                new UserResponseDto(userDto),
                HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDto> signIn(
            @RequestBody UserSignInDto userSignInDto){
        UserWithTokenDto userWithTokenDto = userService.signIn(userSignInDto);
        return ResponseEntity.ok(new TokenResponseDto(userWithTokenDto));
    }
}
