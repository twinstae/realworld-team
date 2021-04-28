package study.realWorld.entity;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="followeeRelations")
    private Profile fromProfile;

    @ManyToOne
    @JoinColumn(name="followerRelations")
    private Profile toProfile;
}