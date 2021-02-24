package study.realWorld.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue
    @Column(name = "PROFILE_ID")
    private Long id;

    private String username;
    private String image;

    // ToDo : cascade user가 삭제되면 profile도 삭제
    @OneToOne
    @JoinColumn(name ="USER_ID")
    private User user;

    @Builder
    public Profile(String username, String image, User user, Set<Profile> followers) {
        this.username = username;
        this.image = image;
        this.user = user;
    }

    @OneToMany(mappedBy = "fromProfile", cascade = CascadeType.ALL) //이 Profile이 팔로우한 목록
    private List<Follow> followeeRelations = new ArrayList<>();

    public List<Profile> getFollowees(){
        return this.followeeRelations.stream()
                .map(Follow::getToProfile)
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "toProfile", cascade = CascadeType.ALL)// 이 Profile을 팔로우한 목록
    private final List<Follow> followerRelations = new ArrayList<>();

    @OneToMany
    private final List<Favorite> favoriteList = new ArrayList<>();

    public List<Profile> getFollowers(){
        return this.followerRelations.stream()
                .map(Follow::getFromProfile)
                .collect(Collectors.toList());
    }

    private void addFollower(Follow follow){
        this.followerRelations.add(follow);
    }

    private void removeFollower(Follow follow){
        this.followerRelations.remove(follow);
    }

    public void follow(Profile toProfile){
        Follow follow = Follow.builder()
                .fromProfile(this)
                .toProfile(toProfile)
                .build();
        this.followeeRelations.add(follow);
        toProfile.addFollower(follow);
    }

    public void unfollow(Profile toProfile){
        Follow follow = this.followeeRelations.stream().filter(it -> it.getToProfile().equals(toProfile))
                .findFirst()
                .orElseThrow(()->new RuntimeException("팔로우하고 있지 않습니다."));

        this.followeeRelations.remove(follow);
        toProfile.removeFollower(follow);
    }

    public boolean isFollow(Profile toProfile){
        return this.followeeRelations.stream() //이 Profile이 팔로우한 녀석들 중에 toProfile이 있니??
                .anyMatch(follow-> follow.getToProfile().equals(toProfile));
    }

    public boolean isFollowedBy(Profile fromProfile){
        return this.followerRelations.stream() //이 Profile을 팔로우한 녀석들 중에 fromProfile이 있니??
                .anyMatch(follow-> follow.getFromProfile().equals(fromProfile));
    }
}