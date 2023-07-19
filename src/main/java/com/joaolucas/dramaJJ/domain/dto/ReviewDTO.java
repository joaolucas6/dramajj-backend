package com.joaolucas.dramaJJ.domain.dto;

import com.joaolucas.dramaJJ.domain.entities.Review;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class ReviewDTO extends RepresentationModel<ReviewDTO> {

    private Long id;
    private String title;
    private String text;
    private Long authorId;
    private Long dramaId;
    private Double rating;
    private Date instant;

    public ReviewDTO(){

    }

    public ReviewDTO(Review review){
        setId(review.getId());
        setTitle(review.getTitle());
        setText(review.getText());
        setAuthorId(review.getAuthor().getId());
        setDramaId(review.getDrama().getId());
        setRating(review.getRating());
        setInstant(review.getInstant());
    }

}
