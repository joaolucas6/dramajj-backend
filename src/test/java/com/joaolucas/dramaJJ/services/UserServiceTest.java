package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.models.dto.UserDTO;
import com.joaolucas.dramaJJ.models.entities.Actor;
import com.joaolucas.dramaJJ.models.entities.Drama;
import com.joaolucas.dramaJJ.models.entities.User;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private DramaRepository dramaRepository;
    @Mock
    private ActorRepository actorRepository;
    private UserService underTest;
    private User user;
    private User user2;
    private Drama drama;

    private Actor actor;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, dramaRepository, actorRepository);

        user = new User();
        user.setUsername("Test");

        user2 = new User();
        user.setUsername("Test 2");

        drama = new Drama();
        drama.setName("A time called you");

        actor = new Actor();
        actor.setSurname("Jeon Yeobeen");
    }

    @Test
    void itShouldFindAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        var result = underTest.findAll();

        assertThat(result).isEqualTo(List.of(new UserDTO(user)));
    }

    @Test
    void itShouldFindUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        var result = underTest.findById(1L);

        assertThat(result).isEqualTo(new UserDTO(user));
    }

    @Test
    void itShouldUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Test 2");

        var result = underTest.update(1L, userDTO);

        assertThat(result.getUsername()).isEqualTo("Test 2");
    }

    @Test
    void itShouldDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        underTest.delete(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void itShouldAddFavoriteDrama() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));

        underTest.addFavoriteDrama(1L, 1L);

        assertThat(user.getFavoriteDramas().contains(drama)).isTrue();
    }

    @Test
    void itShouldRemoveFavoriteDrama() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));

        user.getFavoriteDramas().add(drama);

        underTest.removeFavoriteDrama(1L, 1L);

        assertThat(user.getFavoriteDramas().contains(drama)).isFalse();
    }


    @Test
    void itShouldAddPlanToWatchDrama() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));

        underTest.addPlanToWatch(1L, 1L);

        assertThat(user.getPlanToWatch().contains(drama)).isTrue();
    }

    @Test
    void itShouldRemovePlanToWatchDrama() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dramaRepository.findById(1L)).thenReturn(Optional.of(drama));

        user.getPlanToWatch().add(drama);

        underTest.removePlanToWatch(1L, 1L);

        assertThat(user.getPlanToWatch().contains(drama)).isFalse();
    }

    @Test
    void itShouldFollowUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        underTest.follow(1L, 2L);

        assertThat(user.getFollowing().contains(user2) && user2.getFollowers().contains(user)).isTrue();
    }

    @Test
    void itShouldUnfollowUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));

        user.getFollowing().add(user2);
        user2.getFollowers().add(user);

        underTest.unfollow(1L, 2L);

        assertThat(!user.getFollowing().contains(user2) && !user2.getFollowers().contains(user)).isTrue();
    }

    @Test
    void itShouldFollowActor() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));

        underTest.followActor(1L, 1L);

        assertThat(user.getFollowingActors().contains(actor) && actor.getFollowers().contains(user)).isTrue();
    }

    @Test
    void itShouldUnfollowActor() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));

        user.getFollowingActors().add(actor);
        actor.getFollowers().add(user);

        underTest.unfollowActor(1L, 1L);

        assertThat(!user.getFollowingActors().contains(actor) && !actor.getFollowers().contains(user)).isTrue();
    }
}