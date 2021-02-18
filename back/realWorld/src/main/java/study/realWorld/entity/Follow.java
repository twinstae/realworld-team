package study.realWorld.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="from_user") //DB에 FK 변수명이 된다.
    private Profile fromProfile;

    @ManyToOne
    @JoinColumn(name="to_user") //DB에 FK 변수명이 된다.
    private Profile toProfile;

}


