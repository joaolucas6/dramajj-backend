package com.joaolucas.dramaJJ.domain.dto;

import com.joaolucas.dramaJJ.domain.entities.Drama;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DramaDTO {


    private Long id;
    private String name;
    private String synopsis;
    private Date releaseDate;
    private String posterImgUrl;
    private Integer episodeNumber;
    private Long genreId;
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
        setGenreId(drama.getGenre().getId());
        setRates(drama.getRates());
        drama.getCasting().forEach(actor -> castingId.add(actor.getId()));
        drama.getReviews().forEach(review -> reviewsId.add(review.getId()));

    }


}
