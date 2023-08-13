package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.controllers.ActorController;
import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.domain.entities.Actor;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.utils.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;

    public List<ActorDTO> findAll(){
        return actorRepository.findAll().stream().map(actor -> new ActorDTO(actor).add(linkTo(methodOn(ActorController.class).findById(actor.getId())).withSelfRel())).toList();
    }

    public ActorDTO findById(Long id){
        ActorDTO actorDTO = new ActorDTO(actorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", id)))
        );

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
        Actor actor = actorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", id))
        );

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
        Actor actor = actorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", id))
        );

        actorRepository.delete(actor);
    }

}
