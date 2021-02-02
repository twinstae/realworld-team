package study.realWorld.api.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.realWorld.api.dto.LoginUserResponseDto;

@RestController
public class LoginUserController {

    @GetMapping("/api/loginUser")
    public ResponseEntity getArticles() {
        LoginUserResponseDto user = LoginUserResponseDto
                .builder()
                .username("MemberA")
                .email("a@a")
                .build();


        return ResponseEntity.ok(user);
    }
}
