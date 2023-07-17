package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.domain.dto.GenreDTO;
import com.joaolucas.dramaJJ.domain.entities.Drama;
import com.joaolucas.dramaJJ.domain.entities.Genre;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.GenreRepository;
import com.joaolucas.dramaJJ.utils.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private DramaRepository dramaRepository;

    public List<GenreDTO> findAll(){
        List<GenreDTO> list = new ArrayList<>();

        genreRepository.findAll().forEach(genre -> list.add(new GenreDTO(genre)));

        return list;
    }

    public GenreDTO findById(Long id){
        return new GenreDTO(genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Genre with ID %d was not found", id))));
    }

    public GenreDTO create(GenreDTO genreDTO){
        Genre genre = DTOMapper.toGenre(genreDTO, List.of());

        return new GenreDTO(genreRepository.save(genre));
    }

    public GenreDTO update(Long id, GenreDTO genreDTO){
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Genre with ID %d was not found", id)));
        if(genreDTO.getName() != null) genre.setName(genreDTO.getName());

        return new GenreDTO(genreRepository.save(genre));
    }

    public void delete(Long id){
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Genre with ID %d was not found", id)));

        List<Drama> dramas = genre.getDramas();

        dramas.forEach(drama -> {
            drama.getGenres().remove(genre);
            dramaRepository.save(drama);
        });
        
        genreRepository.delete(genre);
    }

}
