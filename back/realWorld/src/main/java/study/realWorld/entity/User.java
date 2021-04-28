package study.realWorld.entity;


import com.sun.istack.NotNull;
import lombok.*;
import study.realWorld.api.dto.profilesDtos.ProfileCreateDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(length = 50, unique = true)
    private String userName;

    @NotNull
    @Column(unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @Builder
    public User(String userName, String email, String password, Set<Authority> authorities) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.activated = true;
        this.authorities = authorities;
    }

    @Column
    private boolean activated;

    public void initProfile(Profile profile){
        this.profile = profile;
    }

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
