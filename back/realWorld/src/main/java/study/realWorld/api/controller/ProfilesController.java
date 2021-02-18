package study.realWorld.api.controller;


import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleResponseDto;
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

}
