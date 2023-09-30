package com.joaolucas.dramaJJ.services;


import com.joaolucas.dramaJJ.models.dto.GenreDTO;
import com.joaolucas.dramaJJ.models.entities.Genre;
import com.joaolucas.dramaJJ.repositories.GenreRepository;
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
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;
    private GenreService underTest;

    private Genre genre;

    @BeforeEach
    void setUp() {
        underTest = new GenreService(genreRepository);
        genre = new Genre();
        genre.setName("Ação");
    }

    @Test
    void itShouldFindAllGenres() {
        when(genreRepository.findAll()).thenReturn(List.of(genre));

        var result = underTest.findAll();

        assertThat(result).isEqualTo(List.of(new GenreDTO(genre)));
    }

    @Test
    void itShouldFindGenreById() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        var result = underTest.findById(1L);

        assertThat(result).isEqualTo(new GenreDTO(genre));
    }

    @Test
    void itShouldCreateGenre() {
        when(genreRepository.save(genre)).thenReturn(genre);

        var result = underTest.create(new GenreDTO(genre));

        assertThat(result).isNotNull();
    }

    @Test
    void itShouldUpdateGenre() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(genreRepository.save(genre)).thenReturn(genre);

        GenreDTO toUpdate = new GenreDTO(genre);
        toUpdate.setName("SLA");

        var result = underTest.update(1L, toUpdate);

        assertThat(result.getName()).isEqualTo("SLA");
    }

    @Test
    void itShouldDeleteGenre() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        underTest.delete(1L);

        verify(genreRepository, times(1)).delete(genre);
    }

}