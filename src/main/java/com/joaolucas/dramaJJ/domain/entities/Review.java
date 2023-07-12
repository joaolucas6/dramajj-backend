package com.joaolucas.dramaJJ.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "tb_review")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    private User author;

    @ManyToOne
    private Drama drama;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "rating")
    private Date instant;


}
