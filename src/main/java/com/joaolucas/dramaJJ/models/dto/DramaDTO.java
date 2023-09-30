package com.joaolucas.dramaJJ.models.dto;

import com.joaolucas.dramaJJ.models.entities.Drama;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class DramaDTO extends RepresentationModel<DramaDTO> {


    private Long id;
    private String name;
    private String synopsis;
    private LocalDate releaseDate;
    private String posterImgUrl;
    private Integer episodeNumber;
    private List<Long> genresId = new ArrayList<>();
    private List<Long> castingId = new ArrayList<>();
    private List<Long> reviewsId = new ArrayList<>();

    public DramaDTO(){

    }

    public DramaDTO(Drama drama){

        if(drama.getId() != null) setId(drama.getId());
        if(drama.getName() != null) setName(drama.getName());
        if(drama.getSynopsis() != null) setSynopsis(drama.getSynopsis());
        if(drama.getReleaseDate() != null) setReleaseDate(drama.getReleaseDate());
        if(drama.getPosterImgUrl() != null) setPosterImgUrl(drama.getPosterImgUrl());
        if(drama.getEpisodeNumber() != null) setEpisodeNumber(drama.getEpisodeNumber());
        drama.getGenres().forEach(genre -> genresId.add(genre.getId()));
        drama.getCasting().forEach(actor -> castingId.add(actor.getId()));
        drama.getReviews().forEach(review -> reviewsId.add(review.getId()));

    }


}
