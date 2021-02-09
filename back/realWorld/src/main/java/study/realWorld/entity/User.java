package study.realWorld.entity;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 50, unique = true)
    private String userName;

    //null불가능, Unique하게..
    @NotNull
    @Column(name="user_email",unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    // activated;
    @Column
    private boolean activated;
    //bio??
    //img??는 경로설정을 하니까 String으로 쓰는 것인가..?
    //token??은 잘 모르겠으니 일단 pass

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
