package study.realWorld.entity;


import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String userName;

    //null불가능, Unique하게..
    @NotNull
    @Column(name="user_email",unique = true)
    private String email;

    //bio??
    //img??는 경로설정을 하니까 String으로 쓰는 것인가..?
    //token??은 잘 모르겠으니 일단 pass



    @Builder
    public User(Long id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }
}
