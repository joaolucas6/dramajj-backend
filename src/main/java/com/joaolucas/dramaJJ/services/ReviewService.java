package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.domain.dto.ReviewDTO;
import com.joaolucas.dramaJJ.domain.entities.Drama;
import com.joaolucas.dramaJJ.domain.entities.Review;
import com.joaolucas.dramaJJ.domain.entities.User;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.ReviewRepository;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import com.joaolucas.dramaJJ.utils.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DramaRepository dramaRepository;


    public List<ReviewDTO> findAll(){
        List<ReviewDTO> list = new ArrayList<>();
        reviewRepository.findAll().forEach(review -> list.add(new ReviewDTO(review)));
        return list;
    }

    public ReviewDTO findById(Long id){
        return new ReviewDTO(reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Review with ID %d was not found", id))));
    }

    public ReviewDTO create(Long authorId, Long dramaId, ReviewDTO reviewDTO){


        User user = userRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", authorId)));
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId)));
        Review review = DTOMapper.toReview(reviewDTO, user, drama);

        user.getReviews().add(review);
        drama.getReviews().add(review);

        return new ReviewDTO(reviewRepository.save(review));
    }

    public ReviewDTO update(Long reviewId, ReviewDTO reviewDTO){

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException(String.format("Review with ID %d was not found", reviewId)));

        if(reviewDTO.getTitle() != null) review.setTitle(reviewDTO.getTitle());
        if(reviewDTO.getText() != null) review.setText(reviewDTO.getText());
        if(reviewDTO.getRating() != null) review.setRating(reviewDTO.getRating());

        reviewRepository.save(review);
        


        return new ReviewDTO(review);
    }

    public void delete(Long id){

        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Review with ID %d was not found", id)));

        User author = review.getAuthor();
        Drama drama = review.getDrama();

        author.getReviews().remove(review);
        drama.getReviews().remove(review);


        userRepository.save(author);
        dramaRepository.save(drama);

        reviewRepository.delete(review);
    }
}
