package study.realWorld.entity;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "author")
    private final List<Articles> articlesList = new ArrayList<>();

    @Builder
    public User(String userName, String email, String password, Set<Authority> authorities) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.activated = true;
        this.authorities = authorities;
    }

    public void addArticleList(Articles articles){
        articlesList.add(articles);
        articles.setAuthor(this);
    }

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
