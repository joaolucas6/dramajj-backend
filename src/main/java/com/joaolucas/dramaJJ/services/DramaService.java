package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.controllers.DramaController;
import com.joaolucas.dramaJJ.exceptions.BadRequestException;
import com.joaolucas.dramaJJ.models.dto.DramaDTO;
import com.joaolucas.dramaJJ.models.entities.Actor;
import com.joaolucas.dramaJJ.models.entities.Drama;
import com.joaolucas.dramaJJ.models.entities.Genre;
import com.joaolucas.dramaJJ.exceptions.ConflictException;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.GenreRepository;
import com.joaolucas.dramaJJ.utils.DTOMapper;
import com.joaolucas.dramaJJ.utils.DataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class DramaService {

    private final DramaRepository dramaRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;

    public List<DramaDTO> findAll(){
        return dramaRepository.findAll().stream().map(drama -> new DramaDTO(drama).add(linkTo(methodOn(DramaController.class).findById(drama.getId())).withSelfRel())).toList();
    }

    public DramaDTO findById(Long id){

        DramaDTO dramaDTO = new DramaDTO(
                dramaRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", id))
                )
        );

        dramaDTO.add(linkTo(methodOn(DramaController.class).findById(id)).withSelfRel());

        return dramaDTO;
    }

    public DramaDTO create(DramaDTO dramaDTO){

        if(!DataValidation.isDramaInfoValid(dramaDTO)) throw new BadRequestException("Invalid drama info");

        Drama drama = DTOMapper.toDrama(
                dramaDTO, List.of(), List.of(),  List.of()
        );

        Drama savedDrama = dramaRepository.save(drama);


        return new DramaDTO(savedDrama).add(linkTo(methodOn(DramaController.class).findById(savedDrama.getId())).withSelfRel());
    }

    public DramaDTO update(Long dramaId, DramaDTO dramaDTO){
        if(!DataValidation.isDramaInfoValid(dramaDTO)) throw new BadRequestException("Invalid drama info");

        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );

        if(dramaDTO.getName() != null) drama.setName(dramaDTO.getName());
        if(dramaDTO.getSynopsis() != null) drama.setSynopsis(dramaDTO.getSynopsis());
        if(dramaDTO.getReleaseDate() != null) drama.setReleaseDate(dramaDTO.getReleaseDate());
        if(dramaDTO.getPosterImgUrl() != null) drama.setPosterImgUrl(dramaDTO.getPosterImgUrl());
        if(dramaDTO.getEpisodeNumber() != null) drama.setEpisodeNumber(dramaDTO.getEpisodeNumber());

        DramaDTO updatedDramaDTO = new DramaDTO(dramaRepository.save(drama));

        updatedDramaDTO.add(linkTo(methodOn(DramaController.class).findById(dramaId)).withSelfRel());

        return updatedDramaDTO;

    }

    public void delete(Long id){
        Drama drama = dramaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", id))
        );

        dramaRepository.delete(drama);
    }

    public void addActor(Long actorId, Long dramaId){

        Actor actor = actorRepository.findById(actorId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", actorId))
        );
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );

        if(actor.getDramas().contains(drama) || drama.getCasting().contains(actor)) throw new ConflictException("Actor already in casting");

        actor.getDramas().add(drama);
        drama.getCasting().add(actor);

        actorRepository.save(actor);
        dramaRepository.save(drama);

    }

    public void removeActor(Long actorId, Long dramaId) {

        Actor actor = actorRepository.findById(actorId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", actorId))
        );
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );

        if(!actor.getDramas().contains(drama) || !drama.getCasting().contains(actor)) throw new ConflictException("Actor is was not found in casting");

        actor.getDramas().remove(drama);
        drama.getCasting().remove(actor);

        actorRepository.save(actor);
        dramaRepository.save(drama);
    }

    public void addGenre(Long genreId, Long dramaId){
        Genre genre = genreRepository.findById(genreId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Genre with ID %d was not found", genreId))
        );
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );

        if(genre.getDramas().contains(drama) || drama.getGenres().contains(genre)) throw new ConflictException("Genre is already in drama");
        genre.getDramas().add(drama);
        drama.getGenres().add(genre);

        genreRepository.save(genre);
        dramaRepository.save(drama);

    }

    public void removeGenre(Long genreId, Long dramaId){
        Genre genre = genreRepository.findById(genreId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Genre with ID %d was not found", genreId))
        );
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );
        if(!genre.getDramas().contains(drama) || !drama.getGenres().contains(genre)) throw new ConflictException("Genre was not found in drama");
        genre.getDramas().remove(drama);
        drama.getGenres().remove(genre);

        genreRepository.save(genre);
        dramaRepository.save(drama);

    }


}
