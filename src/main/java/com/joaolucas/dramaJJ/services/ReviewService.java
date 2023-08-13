package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.controllers.ReviewController;
import com.joaolucas.dramaJJ.models.dto.ReviewDTO;
import com.joaolucas.dramaJJ.models.entities.Drama;
import com.joaolucas.dramaJJ.models.entities.Review;
import com.joaolucas.dramaJJ.models.entities.User;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.ReviewRepository;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import com.joaolucas.dramaJJ.utils.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final DramaRepository dramaRepository;


    public List<ReviewDTO> findAll(){
        return reviewRepository.findAll().stream().map(review -> new ReviewDTO(review).add(linkTo(methodOn(ReviewController.class).findById(review.getId())).withSelfRel())).toList();
    }

    public ReviewDTO findById(Long id){
        ReviewDTO reviewDTO = new ReviewDTO(
                reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Review with ID %d was not found", id)))
        );

        reviewDTO.add(linkTo(methodOn(ReviewController.class).findById(id)).withSelfRel());

        return reviewDTO;
    }

    public ReviewDTO create(Long authorId, Long dramaId, ReviewDTO reviewDTO){
        User user = userRepository.findById(authorId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", authorId))
        );
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );
        Review review = DTOMapper.toReview(reviewDTO, user, drama);

        user.getReviews().add(review);
        drama.getReviews().add(review);

        ReviewDTO responseReviewDTO = new ReviewDTO(reviewRepository.save(review));

        responseReviewDTO.add(linkTo(methodOn(ReviewController.class).findById(responseReviewDTO.getId())).withSelfRel());

        return responseReviewDTO;
    }

    public ReviewDTO update(Long reviewId, ReviewDTO reviewDTO){
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Review with ID %d was not found", reviewId))
        );

        if(reviewDTO.getTitle() != null) review.setTitle(reviewDTO.getTitle());
        if(reviewDTO.getText() != null) review.setText(reviewDTO.getText());
        if(reviewDTO.getRating() != null) review.setRating(reviewDTO.getRating());

        reviewRepository.save(review);
        
        ReviewDTO updatedReviewDTO = new ReviewDTO(review);

        updatedReviewDTO.add(linkTo(methodOn(ReviewController.class).findById(reviewId)).withSelfRel());

        return updatedReviewDTO;
    }

    public void delete(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Review with ID %d was not found", id))
        );
        reviewRepository.delete(review);
    }
}
