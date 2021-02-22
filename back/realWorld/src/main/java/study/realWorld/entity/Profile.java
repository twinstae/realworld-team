package study.realWorld.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    @OneToMany(mappedBy = "fromProfile")
    private List<Follow> followeeRelations = new ArrayList<>();

    public List<Profile> getFollowees(){
        return this.followeeRelations.stream()
                .map(Follow::getToProfile)
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "toProfile")
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
    }

    public void unfollow(Profile toProfile){
        this.followeeRelations = this.followeeRelations.stream()
                .filter(follow-> !follow.getToProfile().equals(toProfile))
                .collect(Collectors.toList());
    }

    public boolean isFollow(Profile toProfile){
        return this.followeeRelations.stream()
                .anyMatch(follow-> follow.getToProfile().equals(toProfile));
    }
}
