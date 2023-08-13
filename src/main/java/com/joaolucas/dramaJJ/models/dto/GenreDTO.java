package com.joaolucas.dramaJJ.models.dto;


import com.joaolucas.dramaJJ.models.entities.Genre;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class GenreDTO extends RepresentationModel<GenreDTO> {

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
