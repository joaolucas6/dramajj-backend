package com.joaolucas.dramaJJ.models.dto;

import com.joaolucas.dramaJJ.models.entities.Review;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
public class ReviewDTO extends RepresentationModel<ReviewDTO> {

    private Long id;
    private String title;
    private String text;
    private Long authorId;
    private Long dramaId;
    private Double rating;
    private LocalDateTime instant;

    public ReviewDTO(){

    }

    public ReviewDTO(Review review){
        if(review.getId() != null) setId(review.getId());
        if(review.getTitle() != null) setTitle(review.getTitle());
        if(review.getText() != null) setText(review.getText());
        if(review.getAuthor() != null) setAuthorId(review.getAuthor().getId());
        if(review.getDrama() != null) setDramaId(review.getDrama().getId());
        if(review.getRating() != null) setRating(review.getRating());
        if(review.getInstant() != null) setInstant(review.getInstant());
    }

}
