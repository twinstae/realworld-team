package study.realWorld.entity;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="PROFILE_ID", insertable=false, updatable=false)
    private Profile fromProfile;

    @ManyToOne
    @JoinColumn(name="PROFILE_ID", insertable=false, updatable=false)
    private Profile toProfile;
}