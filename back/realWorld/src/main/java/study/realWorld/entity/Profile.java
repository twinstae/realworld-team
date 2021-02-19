package study.realWorld.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Profile {


    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    private String username;
    private String image;

    @OneToOne
    @JoinColumn(name ="USER_ID")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "TEST", name = "USER_RELATIONS",
            joinColumns = @JoinColumn(name = "FOLLOWED_ID"),
            inverseJoinColumns = @JoinColumn(name = "FOLLOWER_ID"))
    private Set<Profile> followers =  new HashSet<Profile>();;



    @Builder
    public Profile(String username, String image, User user, Set<Profile> followers) {
        this.username = username;
        this.image = image;
        this.user = user;
        this.followers =followers;
    }

    public boolean checkFollow(Profile profile) {
        return this.followers.contains(profile);
    }


    private void addFollower(Profile follower) {
        followers.add(follower);
    }

}
