package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.controllers.ActorController;
import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.domain.entities.Actor;
import com.joaolucas.dramaJJ.domain.entities.Drama;
import com.joaolucas.dramaJJ.domain.entities.User;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import com.joaolucas.dramaJJ.utils.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DramaRepository dramaRepository;

    public List<ActorDTO> findAll(){
        List<ActorDTO> list = new ArrayList<>();
        actorRepository.findAll().forEach(actor -> list.add(new ActorDTO(actor)));

        list.forEach(actorDTO -> {
            actorDTO.add(linkTo(methodOn(ActorController.class).findById(actorDTO.getId())).withSelfRel());
        });

        return list;
    }

    public ActorDTO findById(Long id){
        ActorDTO actorDTO = new ActorDTO(actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", id))));

        actorDTO.add(linkTo(methodOn(ActorController.class).findById(id)).withSelfRel());

        return actorDTO;
    }

    public ActorDTO create(ActorDTO actorDTO){
        Actor actor = DTOMapper.toActor(actorDTO, List.of(), List.of());
        ActorDTO responseActorDTO = new ActorDTO(actorRepository.save(actor));

        responseActorDTO.add(linkTo(methodOn(ActorController.class).findById(responseActorDTO.getId())).withSelfRel());

        return responseActorDTO;
    }

    public ActorDTO update(Long id, ActorDTO actorDTO){
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", id)));
        if(actorDTO.getFirstName() != null) actor.setFirstName(actorDTO.getFirstName());
        if(actorDTO.getLastName() != null) actor.setLastName(actorDTO.getLastName());
        if(actorDTO.getSurname() != null) actor.setSurname(actorDTO.getSurname());
        if(actorDTO.getGender() != null) actor.setGender(actorDTO.getGender());
        if(actorDTO.getBirthDate() != null) actor.setBirthDate(actorDTO.getBirthDate());
        if(actorDTO.getPictureUrl() != null) actor.setPictureUrl(actorDTO.getPictureUrl());
        if(actorDTO.getNationality() != null) actor.setNationality(actorDTO.getNationality());
        if(actorDTO.getBio() != null) actor.setBio(actorDTO.getBio());

        ActorDTO updatedActorDTO = new ActorDTO(actorRepository.save(actor));

        updatedActorDTO.add(linkTo(methodOn(ActorController.class).findById(updatedActorDTO.getId())).withSelfRel());

        return updatedActorDTO;
    }

    public void delete(Long id){
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", id)));

        List<User> followers = actor.getFollowers();
        List<Drama> dramas = actor.getDramas();


        followers.forEach(follower -> {
            follower.getFollowingActors().remove(actor);
            userRepository.save(follower);
        });
        dramas.forEach(drama -> {
            drama.getCasting().remove(actor);
            dramaRepository.save(drama);
        });
        actorRepository.deleteById(id);
    }

}
