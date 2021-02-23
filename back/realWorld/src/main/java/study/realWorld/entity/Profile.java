package study.realWorld.entity;


import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
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

    @OneToMany(mappedBy = "fromProfile") //나를 팔로우한..
    private List<Follow> followeeRelations = new ArrayList<>();

    public List<Profile> getFollowees(){
        return this.followeeRelations.stream()
                .map(Follow::getToProfile)
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "toProfile")// 내가 팔로우 한..
    private final List<Follow> followerRelations = new ArrayList<>();

    public List<Profile> getFollowers(){
        return this.followerRelations.stream()
                .map(Follow::getFromProfile)
                .collect(Collectors.toList());
    }


    public void follow(Profile toProfile){
        Follow follow = Follow.builder()
                .fromProfile(this)
                .toProfile(toProfile)
                .build();

        this.followeeRelations.add(follow);

        List<Follow> newFollowerRelations = toProfile.getFollowerRelations();
        newFollowerRelations.add(follow);
        toProfile.setFolloweeRelations(newFollowerRelations);
    }

    public void unfollow(Profile toProfile){
        this.followeeRelations = this.followeeRelations.stream()
                .filter(follow-> !follow.getToProfile().equals(toProfile))
                .collect(Collectors.toList());

        toProfile.setFolloweeRelations(
                toProfile.getFolloweeRelations().stream()
                .filter(follow-> !follow.getFromProfile().equals(this))
                .collect(Collectors.toList()));
    }

    public boolean isFollow(Profile toProfile){
        return this.followeeRelations.stream()
                .anyMatch(follow-> follow.getToProfile().equals(toProfile));
    }
}
