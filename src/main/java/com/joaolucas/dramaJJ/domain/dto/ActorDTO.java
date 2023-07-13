package com.joaolucas.dramaJJ.domain.dto;

import com.joaolucas.dramaJJ.domain.entities.Actor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ActorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String surname;
    private String gender;
    private Date birthDate;
    private String pictureUrl;
    private String nationality;
    private String bio;
    private List<Long> followersId = new ArrayList<>();
    private List<Long> dramasId = new ArrayList<>();

    public ActorDTO(Actor actor){

        setId(actor.getId());
        setFirstName(actor.getFirstName());
        setLastName(actor.getLastName());
        setSurname(actor.getSurname());
        setGender(actor.getGender());
        setBirthDate(actor.getBirthDate());
        setPictureUrl(actor.getPictureUrl());
        setNationality(actor.getNationality());
        setBio(actor.getBio());

        actor.getFollowers().forEach(follower -> followersId.add(follower.getId()));
        actor.getDramas().forEach(drama -> dramasId.add(drama.getId()));
        
    }

}
