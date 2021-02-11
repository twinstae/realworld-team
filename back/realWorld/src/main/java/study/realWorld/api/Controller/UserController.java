package study.realWorld.api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleResponseDto;
import study.realWorld.api.dto.userDtos.UserSignUpDto;
import study.realWorld.entity.User;
import study.realWorld.service.ArticlesService;
import study.realWorld.service.UserService;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public User create(  @RequestBody UserSignUpDto userSignUpDto
    ){
        boolean success = userService.save(userSignUpDto);

        if(success) {

        return new ResponseEntity<>(
                new HttpHeaders(),
                HttpStatus.CREATED);
    }
}
