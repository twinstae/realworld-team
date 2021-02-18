package study.realWorld.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "from_user")
	private final List<Follow> from_user = new ArrayList<>();

	@OneToMany(mappedBy = "to_user")
	private final List<Follow> to_user = new ArrayList<>();


}
