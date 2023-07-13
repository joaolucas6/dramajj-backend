package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.domain.dto.GenreDTO;
import com.joaolucas.dramaJJ.domain.entities.Genre;
import com.joaolucas.dramaJJ.repositories.GenreRepository;
import com.joaolucas.dramaJJ.utils.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<GenreDTO> findAll(){
        List<GenreDTO> list = new ArrayList<>();

        genreRepository.findAll().forEach(genre -> list.add(new GenreDTO(genre)));

        return list;
    }

    public GenreDTO findById(Long id){
        return new GenreDTO(genreRepository.findById(id).orElseThrow());
    }

    public GenreDTO create(GenreDTO genreDTO){
        Genre genre = DTOMapper.toGenre(genreDTO, List.of());

        return new GenreDTO(genreRepository.save(genre));
    }

    public GenreDTO update(Long id, GenreDTO genreDTO){
        Genre genre = genreRepository.findById(id).orElseThrow();
        if(genreDTO.getName() != null) genre.setName(genreDTO.getName());

        return new GenreDTO(genreRepository.save(genre));
    }

    public void delete(Long id){
        genreRepository.deleteById(id);
    }

}
