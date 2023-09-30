package com.joaolucas.dramaJJ.services;


import com.joaolucas.dramaJJ.models.dto.ReviewDTO;
import com.joaolucas.dramaJJ.models.entities.Drama;
import com.joaolucas.dramaJJ.models.entities.Review;
import com.joaolucas.dramaJJ.models.entities.User;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.ReviewRepository;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private DramaRepository dramaRepository;
    @Mock
    private ReviewRepository reviewRepository;
    private ReviewService underTest;
    private User user;
    private Review review;
    private Drama drama;


    @BeforeEach
    void setUp() {
        underTest = new ReviewService(reviewRepository, userRepository, dramaRepository);

        review = new Review();
        review.setText("muito bom");

        user = new User();
        user.setUsername("usuario");

        drama = new Drama();
        drama.setName("A time called you");

        review.setAuthor(user);
        review.setDrama(drama);
    }

    @Test
    void itShouldFindAllReviews() {
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        var result = underTest.findAll();

        assertThat(result).isEqualTo(List.of(new ReviewDTO(review)));
    }

    @Test
    void itShouldFindReviewById() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        var result = underTest.findById(1L);

        assertThat(result).isEqualTo(new ReviewDTO(review));
    }

    @Test
    void itShouldCreateReview() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));
        when(reviewRepository.save(review)).thenReturn(review);

        var result = underTest.create(1L, 1L, new ReviewDTO(review));

        assertThat(result).isNotNull();
        assertThat(user.getReviews().contains(review));
    }

    @Test
    void itShouldUpdateReview() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        ReviewDTO toUpdate = new ReviewDTO(review);
        toUpdate.setText("muito ruim");

        var result = underTest.update(1L, toUpdate);

        assertThat(result.getText()).isEqualTo("muito ruim");
    }

    @Test
    void itShouldDeleteReview() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        underTest.delete(1L);
        verify(reviewRepository, times(1)).delete(review);
    }
}