package com.joaolucas.dramaJJ.domain.dto;

import com.joaolucas.dramaJJ.domain.entities.Drama;
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
    private List<Double> rates = new ArrayList<>();

    public DramaDTO(){

    }

    public DramaDTO(Drama drama){

        setId(drama.getId());
        setName(drama.getName());
        setSynopsis(drama.getSynopsis());
        setReleaseDate(drama.getReleaseDate());
        setPosterImgUrl(drama.getPosterImgUrl());
        setEpisodeNumber(drama.getEpisodeNumber());
        drama.getGenres().forEach(genre -> genresId.add(genre.getId()));
        setRates(drama.getRates());
        drama.getCasting().forEach(actor -> castingId.add(actor.getId()));
        drama.getReviews().forEach(review -> reviewsId.add(review.getId()));

    }


}
