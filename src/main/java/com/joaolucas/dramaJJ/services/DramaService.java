package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.domain.dto.DramaDTO;
import com.joaolucas.dramaJJ.domain.dto.GenreDTO;
import com.joaolucas.dramaJJ.domain.entities.Actor;
import com.joaolucas.dramaJJ.domain.entities.Drama;
import com.joaolucas.dramaJJ.domain.entities.Genre;
import com.joaolucas.dramaJJ.exceptions.ConflictException;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.GenreRepository;
import com.joaolucas.dramaJJ.utils.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DramaService {

    @Autowired
    private DramaRepository dramaRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private GenreRepository genreRepository;

    public List<DramaDTO> findAll(){

        List<DramaDTO> list = new ArrayList<>();

        dramaRepository.findAll().forEach(drama -> list.add(new DramaDTO(drama)));

        return list;
    }

    public DramaDTO findById(Long id){
        return new DramaDTO(dramaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", id))));
    }

    public DramaDTO create(DramaDTO dramaDTO){

        Drama drama = DTOMapper.toDrama(dramaDTO, null, List.of(),  List.of(), List.of());

        dramaRepository.save(drama);

        return new DramaDTO(drama);
    }

    public DramaDTO update(Long dramaId, DramaDTO dramaDTO){
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId)));

        if(dramaDTO.getName() != null) drama.setName(dramaDTO.getName());
        if(dramaDTO.getSynopsis() != null) drama.setSynopsis(dramaDTO.getSynopsis());
        if(dramaDTO.getReleaseDate() != null) drama.setReleaseDate(dramaDTO.getReleaseDate());
        if(dramaDTO.getPosterImgUrl() != null) drama.setPosterImgUrl(dramaDTO.getPosterImgUrl());
        if(dramaDTO.getEpisodeNumber() != null) drama.setEpisodeNumber(dramaDTO.getEpisodeNumber());

        dramaRepository.save(drama);

        return new DramaDTO(drama);

    }

    public void delete(Long id){
        dramaRepository.deleteById(id);
    }

    public List<ActorDTO> addActor(Long actorId, Long dramaId) throws Exception {

        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", actorId)));
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId)));

        if(actor.getDramas().contains(drama) || drama.getCasting().contains(actor)) throw new ConflictException("Actor already in casting");

        actor.getDramas().add(drama);
        drama.getCasting().add(actor);

        actorRepository.save(actor);
        dramaRepository.save(drama);

        List<ActorDTO> list = new ArrayList<>();

        drama.getCasting().forEach(castingActor -> list.add(new ActorDTO(castingActor)));

        return list;

    }

    public List<ActorDTO> removeActor(Long actorId, Long dramaId) throws Exception {

        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", actorId)));
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId)));

        if(!actor.getDramas().contains(drama) || !drama.getCasting().contains(actor)) throw new ConflictException("Actor is was not found in casting");

        actor.getDramas().remove(drama);
        drama.getCasting().remove(actor);

        actorRepository.save(actor);
        dramaRepository.save(drama);

        List<ActorDTO> list = new ArrayList<>();

        drama.getCasting().forEach(castingActor -> list.add(new ActorDTO(castingActor)));

        return list;

    }

    public List<GenreDTO> addGenre(Long genreId, Long dramaId) throws Exception {
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new ResourceNotFoundException(String.format("Genre with ID %d was not found", genreId)));
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId)));
        if(genre.getDramas().contains(drama) || drama.getGenres().contains(genre)) throw new ConflictException("Genre is already in drama");
        genre.getDramas().add(drama);
        drama.getGenres().add(genre);

        genreRepository.save(genre);
        dramaRepository.save(drama);

        List<GenreDTO> list = new ArrayList<>();

        drama.getGenres().forEach(listGenre -> list.add(new GenreDTO(listGenre)));

        return list;

    }

    public List<GenreDTO> removeGenre(Long genreId, Long dramaId) throws Exception {
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new ResourceNotFoundException(String.format("Genre with ID %d was not found", genreId)));
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId)));
        if(!genre.getDramas().contains(drama) || !drama.getGenres().contains(genre)) throw new ConflictException("Genre was not found in drama");
        genre.getDramas().remove(drama);
        drama.getGenres().remove(genre);

        genreRepository.save(genre);
        dramaRepository.save(drama);

        List<GenreDTO> list = new ArrayList<>();

        drama.getGenres().forEach(listGenre -> list.add(new GenreDTO(listGenre)));

        return list;

    }

}
