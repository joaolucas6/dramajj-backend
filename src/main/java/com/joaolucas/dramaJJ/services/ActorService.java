package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.domain.entities.Actor;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.utils.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public List<ActorDTO> findAll(){
        List<ActorDTO> list = new ArrayList<>();
        actorRepository.findAll().forEach(actor -> list.add(new ActorDTO(actor)));
        return list;
    }

    public ActorDTO findById(Long id){
        return new ActorDTO(actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", id))));
    }

    public ActorDTO create(ActorDTO actorDTO){
        Actor actor = DTOMapper.toActor(actorDTO, List.of(), List.of());
        return new ActorDTO(actorRepository.save(actor));
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

        actorRepository.save(actor);

        return new ActorDTO(actor);
    }

    public void delete(Long id){
        actorRepository.deleteById(id);
    }

}
