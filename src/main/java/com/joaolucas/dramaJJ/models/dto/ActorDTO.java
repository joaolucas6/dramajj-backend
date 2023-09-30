package com.joaolucas.dramaJJ.models.dto;

import com.joaolucas.dramaJJ.models.entities.Actor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class ActorDTO extends RepresentationModel<ActorDTO> {

    private Long id;
    private String firstName;
    private String lastName;
    private String surname;
    private String gender;
    private LocalDate birthDate;
    private String pictureUrl;
    private String nationality;
    private String bio;
    private List<Long> followersId = new ArrayList<>();
    private List<Long> dramasId = new ArrayList<>();

    public ActorDTO(){

    }

    public ActorDTO(Actor actor){

        if(actor.getId() != null) setId(actor.getId());
        if(actor.getFirstName() != null) setFirstName(actor.getFirstName());
        if(actor.getLastName() != null) setLastName(actor.getLastName());
        if(actor.getSurname() != null) setSurname(actor.getSurname());
        if(actor.getGender() != null) setGender(actor.getGender());
        if(actor.getBirthDate() != null) setBirthDate(actor.getBirthDate());
        if(actor.getPictureUrl() != null) setPictureUrl(actor.getPictureUrl());
        if(actor.getNationality() != null) setNationality(actor.getNationality());
        if(actor.getBio() != null) setBio(actor.getBio());

        actor.getFollowers().forEach(follower -> followersId.add(follower.getId()));
        actor.getDramas().forEach(drama -> dramasId.add(drama.getId()));

    }

}
