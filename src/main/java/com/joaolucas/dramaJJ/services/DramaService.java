package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.controllers.ActorController;
import com.joaolucas.dramaJJ.controllers.DramaController;
import com.joaolucas.dramaJJ.controllers.GenreController;
import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.domain.dto.DramaDTO;
import com.joaolucas.dramaJJ.domain.dto.GenreDTO;
import com.joaolucas.dramaJJ.domain.entities.Actor;
import com.joaolucas.dramaJJ.domain.entities.Drama;
import com.joaolucas.dramaJJ.domain.entities.Genre;
import com.joaolucas.dramaJJ.domain.entities.Review;
import com.joaolucas.dramaJJ.exceptions.ConflictException;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.GenreRepository;
import com.joaolucas.dramaJJ.utils.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class DramaService {
    private final DramaRepository dramaRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private final ReviewService reviewService;

    public List<DramaDTO> findAll(){

        List<DramaDTO> allDramasDTO = new ArrayList<>();

        dramaRepository.findAll().forEach(drama -> allDramasDTO.add(new DramaDTO(drama)));

        allDramasDTO.forEach(dramaDTO -> {
            dramaDTO.add(linkTo(methodOn(DramaController.class).findById(dramaDTO.getId())).withSelfRel());
        });

        return allDramasDTO;
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

        Drama drama = DTOMapper.toDrama(
                dramaDTO, null, List.of(),  List.of(), List.of()
        );

        dramaRepository.save(drama);

        DramaDTO responseDramaDTO = new DramaDTO(drama);

        responseDramaDTO.add(linkTo(methodOn(DramaController.class).findById(responseDramaDTO.getId())).withSelfRel());

        return responseDramaDTO;
    }

    public DramaDTO update(Long dramaId, DramaDTO dramaDTO){
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

        List<Actor> casting = drama.getCasting();
        List<Review> reviews = drama.getReviews();
        List<Genre> genres = drama.getGenres();

        casting.forEach(actor -> {
            actor.getDramas().remove(drama);
            actorRepository.save(actor);
        });

        reviews.forEach(review -> {
            reviewService.delete(review.getId());
        });

        genres.forEach(genre -> {
            genre.getDramas().remove(drama);
            genreRepository.save(genre);
        });

        dramaRepository.delete(drama);
    }

    public List<ActorDTO> addActor(Long actorId, Long dramaId){

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

        List<ActorDTO> list = new ArrayList<>();

        drama.getCasting().forEach(castingActor -> list.add(new ActorDTO(castingActor)));

        list.forEach(actorDTO -> {
            actorDTO.add(linkTo(methodOn(ActorController.class).findById(actorDTO.getId())).withSelfRel());
        });

        return list;

    }

    public List<ActorDTO> removeActor(Long actorId, Long dramaId) {

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

        List<ActorDTO> list = new ArrayList<>();

        drama.getCasting().forEach(
                castingActor -> list.add(new ActorDTO(castingActor))
        );

        list.forEach(actorDTO -> {
            actorDTO.add(linkTo(methodOn(ActorController.class).findById(actorDTO.getId())).withSelfRel());
        });

        return list;

    }

    public List<GenreDTO> addGenre(Long genreId, Long dramaId) throws Exception {
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

        List<GenreDTO> list = new ArrayList<>();

        drama.getGenres().forEach(listGenre -> list.add(new GenreDTO(listGenre)));

        list.forEach(genreDTO -> {
            genreDTO.add(linkTo(methodOn(GenreController.class).findById(genreDTO.getId())).withSelfRel());
        });

        return list;

    }

    public List<GenreDTO> removeGenre(Long genreId, Long dramaId) throws Exception {
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

        List<GenreDTO> list = new ArrayList<>();

        drama.getGenres().forEach(listGenre -> list.add(new GenreDTO(listGenre)));

        list.forEach(genreDTO -> {
            genreDTO.add(linkTo(methodOn(GenreController.class).findById(genreDTO.getId())).withSelfRel());
        });

        return list;

    }

}
