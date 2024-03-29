package com.joaolucas.dramaJJ.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate releaseDate;

    @Column(name = "poster_img_url")
    private String posterImgUrl;

    @Column(name = "episode_number")
    private Integer episodeNumber;

    @ManyToMany(mappedBy = "dramas")
    private List<Genre> genres = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "drama_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> casting = new ArrayList<>();

    @OneToMany(mappedBy = "drama", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

}
