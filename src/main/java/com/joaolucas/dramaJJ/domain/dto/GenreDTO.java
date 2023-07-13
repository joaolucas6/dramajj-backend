package com.joaolucas.dramaJJ.domain.dto;


import com.joaolucas.dramaJJ.domain.entities.Genre;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GenreDTO {

    private Long id;
    private String name;
    private List<Long> dramasId = new ArrayList<>();

    public GenreDTO(){

    }

    public GenreDTO(Genre genre){
        setId(genre.getId());
        setName(genre.getName());
        genre.getDramas().forEach(drama -> dramasId.add(drama.getId()));
    }
}
