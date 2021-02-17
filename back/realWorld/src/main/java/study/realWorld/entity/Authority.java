package study.realWorld.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Authority {

    @Id
    @Column(name="authority_name", length=50, unique = true)
    private String authorityName;
}
