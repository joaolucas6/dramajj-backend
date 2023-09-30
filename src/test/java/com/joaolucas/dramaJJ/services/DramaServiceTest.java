package com.joaolucas.dramaJJ.services;


import com.joaolucas.dramaJJ.models.dto.DramaDTO;
import com.joaolucas.dramaJJ.models.entities.Actor;
import com.joaolucas.dramaJJ.models.entities.Drama;
import com.joaolucas.dramaJJ.models.entities.Genre;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
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
class DramaServiceTest {

    @Mock
    private DramaRepository dramaRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private ActorRepository actorRepository;
    private DramaService underTest;

    private Drama drama;
    private Genre genre;
    private Actor actor;

    @BeforeEach
    void setUp() {
        underTest = new DramaService(dramaRepository, actorRepository, genreRepository);

        drama = new Drama();
        drama.setName("A time called you");

        genre = new Genre();
        genre.setName("Mistério");

        actor = new Actor();
        actor.setSurname("Jeon Yeobeen");
    }

    @Test
    void itShouldFindAllDramas() {
        when(dramaRepository.findAll()).thenReturn(List.of(drama));

        var result = underTest.findAll();

        assertThat(result).isEqualTo(List.of(new DramaDTO(drama)));
    }

    @Test
    void itShouldFindDramaById() {
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));

        var result = underTest.findById(1L);

        assertThat(result).isEqualTo(new DramaDTO(drama));
    }

    @Test
    void itShouldCreateDrama() {
        when(dramaRepository.save(drama)).thenReturn(drama);

        var result = underTest.create(new DramaDTO(drama));

        assertThat(drama).isNotNull();
    }

    @Test
    void itShouldUpdateDrama() {
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));
        when(dramaRepository.save(drama)).thenReturn(drama);

        DramaDTO toUpdate = new DramaDTO(drama);
        toUpdate.setName("Our beloved summer");

        var result = underTest.update(1L, toUpdate);

        assertThat(result.getName()).isEqualTo("Our beloved summer");
    }

    @Test
    void itShouldDeleteDrama() {
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));
        underTest.delete(1L);
        verify(dramaRepository, times(1)).delete(drama);
    }

    @Test
    void itShouldAddActorToCasting() {
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));

        underTest.addActor(1L, 1L);

        assertThat(actor.getDramas().contains(drama) && drama.getCasting().contains(actor)).isTrue();
    }

    @Test
    void itShouldRemoveActorFromCasting() {
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));

        actor.getDramas().add(drama);
        drama.getCasting().add(actor);

        underTest.removeActor(1L, 1L);

        assertThat(!actor.getDramas().contains(drama) && !drama.getCasting().contains(actor)).isTrue();
    }

    @Test
    void itShouldAddGenre() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));

        underTest.addGenre(1L, 1L);

        assertThat(genre.getDramas().contains(drama) && drama.getGenres().contains(genre)).isTrue();
    }

    @Test
    void itShouldRemoveGenre() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));

        genre.getDramas().add(drama);
        drama.getGenres().add(genre);

        underTest.removeGenre(1L, 1L);

        assertThat(!genre.getDramas().contains(drama) && !drama.getGenres().contains(genre)).isTrue();
    }
}