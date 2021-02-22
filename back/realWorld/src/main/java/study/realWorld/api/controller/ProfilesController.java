package study.realWorld.api.controller;


import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleResponseDto;
import study.realWorld.api.dto.profilesDtos.ProfileCreateDto;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.api.dto.profilesDtos.ProfileResponseDto;
import study.realWorld.service.ProfilesService;

@Api(tags = {"3.Profiles"})
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/profiles")
public class ProfilesController {

    private final ProfilesService profilesService;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponseDto> getProfileByUsername(
            @PathVariable String username
    ){
        ProfileDto profileDto = profilesService.findByUsername(username);
        return ResponseEntity.ok(new ProfileResponseDto(profileDto));
    }

//    @PostMapping("/{username}/follow")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public ResponseEntity<ArticleResponseDto> createArticle(
//            @RequestBody ProfileCreateDto ProfileCreateDto
//    ){
//        ProfileDto profileDto = profilesService.save(ProfileCreateDto);
//
//        return new ResponseEntity<>(
//                new ArticleResponseDto(articleDto),
//                HttpStatus.CREATED);
//    }
}
