package com.joaolucas.dramaJJ.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_actor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "bio")
    private String bio;

    @ManyToMany
    private List<User> followers = new ArrayList<>();

    @ManyToMany
    private List<Drama> dramas = new ArrayList<>();
}
