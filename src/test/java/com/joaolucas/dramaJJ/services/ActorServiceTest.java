package com.joaolucas.dramaJJ.services;


import com.joaolucas.dramaJJ.models.dto.ActorDTO;
import com.joaolucas.dramaJJ.models.entities.Actor;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
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
class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;
    private ActorService underTest;
    private Actor actor;

    @BeforeEach
    void setUp() {
        underTest = new ActorService(actorRepository);
        actor = new Actor();
        actor.setSurname("Jeon Yeobeen");
    }

    @Test
    void itShouldFindAllActors() {
        when(actorRepository.findAll()).thenReturn(List.of(actor));

        var result = underTest.findAll();

        assertThat(result).isEqualTo(List.of(new ActorDTO(actor)));
    }

    @Test
    void itShouldFindActorById() {
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));

        var result = underTest.findById(1L);

        assertThat(result).isEqualTo(new ActorDTO(actor));
    }

    @Test
    void itShouldCreateActor() {
        when(actorRepository.save(actor)).thenReturn(actor);

        var result = underTest.create(new ActorDTO(actor));

        assertThat(result).isNotNull();
    }

    @Test
    void itShouldUpdateActor() {
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));
        when(actorRepository.save(actor)).thenReturn(actor);

        ActorDTO toUpdate = new ActorDTO(actor);
        toUpdate.setSurname("Park Minyoung");

        var result = underTest.update(1L, toUpdate);

        assertThat(result.getSurname()).isEqualTo("Park Minyoung");
    }

    @Test
    void itShouldDeleteActor() {
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));

        underTest.delete(1L);

        verify(actorRepository, times(1)).delete(actor);
    }
}