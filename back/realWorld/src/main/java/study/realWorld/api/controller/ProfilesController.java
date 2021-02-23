package study.realWorld.api.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.api.dto.profilesDtos.ProfileListDto;
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

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{username}/followers")
    public ResponseEntity<ProfileListDto> getFollowersByUsername(
            @PathVariable String username
    ){
        ProfileListDto followersDto = profilesService.findByFollowsByUsername(username);
        return ResponseEntity.ok(followersDto);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{username}/followees")
    public ResponseEntity<ProfileListDto> getFolloweeByUsername(
            @PathVariable String username
    ){
        ProfileListDto followeesDto = profilesService.findByFollowsByUsername(username);
        return ResponseEntity.ok(followeesDto);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{username}/follows/count")
    public ResponseEntity<ProfileListDto> getFollowsCountByUsername(
            @PathVariable String username
    ){
        ProfileListDto followsCountDto = profilesService.findByFollowsByUsername(username);
        return ResponseEntity.ok(followsCountDto);
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
