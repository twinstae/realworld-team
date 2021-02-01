package study.realWorld.api.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.realWorld.api.dto.ArticleListDto;
import study.realWorld.api.dto.ArticleResponseDto;
import study.realWorld.api.dto.LoginUserResponseDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginUserController {

    @GetMapping("/api/loginUser")
    public ResponseEntity getArticles(){
        LoginUserResponseDto user = LoginUserResponseDto
                .builder()
                .username("MemberA")
                .email("a@a")
                .build();


        return ResponseEntity.ok(new LoginUserResponseDto(user.getUsername(),user.getEmail()));
    }
}
