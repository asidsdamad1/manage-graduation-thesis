package utc.edu.thesis.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "class")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

}
