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
    @PostMapping("/{username}/follow")
    public ResponseEntity<ProfileResponseDto> followByUsername(
            @PathVariable String username
    ){
        ProfileDto followingDto = profilesService.followByUsername(username);
        return ResponseEntity.ok(new ProfileResponseDto(followingDto));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{username}/follow")
    public ResponseEntity<ProfileResponseDto> unfollowByUsername(
            @PathVariable String username
    ){
        ProfileDto followingDto = profilesService.unFollowByUsername(username);
        return ResponseEntity.ok(new ProfileResponseDto(followingDto));
    }
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{username}/followers")
    public ResponseEntity<ProfileListDto> getFollowersByUsername(
            @PathVariable String username
    ){
        ProfileListDto followersDto = profilesService.findProfilesFollowersByUsername(username);
        return ResponseEntity.ok(followersDto);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{username}/followees")
    public ResponseEntity<ProfileListDto> getFolloweeByUsername(
            @PathVariable String username
    ){
        ProfileListDto followeesDto = profilesService.findProfilesFolloweesByUsername(username);
        return ResponseEntity.ok(followeesDto);
    }
}
