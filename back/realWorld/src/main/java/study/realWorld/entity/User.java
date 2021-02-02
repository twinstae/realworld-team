package study.realWorld.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private String userName;

    @Column(nullable = false, unique = true) //null불가능, Unique하게..
    private String email;

    //bio??
    //img??는 경로설정을 하니까 String으로 쓰는 것인가..?
    //token??은 잘 모르겠으니 일단 pass
}
