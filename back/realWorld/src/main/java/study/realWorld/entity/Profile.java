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

    @ManyToMany(mappedBy = "followers")
    private List<Profile> followings = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "FOLLOW",
            joinColumns = @JoinColumn(name = "FROM_ID"),
            inverseJoinColumns = @JoinColumn(name = "TO_ID"))
    private final List<Profile> followers = new ArrayList<>();

    public void follow(Profile toProfile){
        if (this.equals(toProfile)){
            throw new RuntimeException("자신을 follow할 수는 없습니다");
        }

        toProfile.followers.add(this);
        this.followings.add(toProfile);
    }

    public void unfollow(Profile toProfile){
        toProfile.followers.remove(this);
        this.followings.remove(toProfile);
    }

    public boolean isFollow(Profile toProfile){
        return this.followings.contains(toProfile);
    }
}