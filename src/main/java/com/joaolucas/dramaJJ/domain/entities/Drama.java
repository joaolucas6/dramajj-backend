package com.joaolucas.dramaJJ.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionType;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_drama")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Drama{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "poster_img_url")
    private String posterImgUrl;

    @Column(name = "episode_number")
    private Integer episodeNumber;

    @ManyToMany(mappedBy = "dramas")
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "drama_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> casting = new ArrayList<>();

    @OneToMany(mappedBy = "drama")
    private List<Review> reviews = new ArrayList<>();

    @ElementCollection
    private List<Double> rates = new ArrayList<>();

}
