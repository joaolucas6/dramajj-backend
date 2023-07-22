package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.controllers.GenreController;
import com.joaolucas.dramaJJ.domain.dto.GenreDTO;
import com.joaolucas.dramaJJ.domain.entities.Drama;
import com.joaolucas.dramaJJ.domain.entities.Genre;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
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
public class GenreService {
    private final GenreRepository genreRepository;
    private final DramaRepository dramaRepository;

    public List<GenreDTO> findAll(){
        List<GenreDTO> allGenresDTO = new ArrayList<>();

        genreRepository.findAll().forEach(genre -> allGenresDTO.add(new GenreDTO(genre)));

        allGenresDTO.forEach(genreDTO -> {
            genreDTO.add(linkTo(methodOn(GenreController.class).findById(genreDTO.getId())).withSelfRel());
        });

        return allGenresDTO;
    }

    public GenreDTO findById(Long id){
        GenreDTO genreDTO = new GenreDTO(genreRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Genre with ID %d was not found", id)))
        );

        genreDTO.add(linkTo(methodOn(GenreController.class).findById(id)).withSelfRel());

        return genreDTO;
    }

    public GenreDTO create(GenreDTO genreDTO){
        Genre genre = DTOMapper.toGenre(genreDTO, List.of());

        genreDTO = new GenreDTO(genreRepository.save(genre));

        genreDTO.add(linkTo(methodOn(GenreController.class).findById(genreDTO.getId())).withSelfRel());

        return genreDTO;
    }

    public GenreDTO update(Long id, GenreDTO genreDTO){
        Genre genre = genreRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Genre with ID %d was not found", id))
        );

        if(genreDTO.getName() != null) genre.setName(genreDTO.getName());

        GenreDTO updatedGenreDTO = new GenreDTO(genreRepository.save(genre));

        updatedGenreDTO.add(linkTo(methodOn(GenreController.class).findById(id)).withSelfRel());


        return updatedGenreDTO;
    }

    public void delete(Long id){
        Genre genre = genreRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Genre with ID %d was not found", id))
        );

        List<Drama> dramas = genre.getDramas();

        dramas.forEach(drama -> {
            drama.getGenres().remove(genre);
            dramaRepository.save(drama);
        });

        genreRepository.delete(genre);
    }

}
