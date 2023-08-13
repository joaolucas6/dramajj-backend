package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.controllers.UserController;
import com.joaolucas.dramaJJ.models.dto.UserDTO;
import com.joaolucas.dramaJJ.models.entities.Actor;
import com.joaolucas.dramaJJ.models.entities.Drama;
import com.joaolucas.dramaJJ.models.entities.User;
import com.joaolucas.dramaJJ.exceptions.ConflictException;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final DramaRepository dramaRepository;

    private final ActorRepository actorRepository;


    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user -> new UserDTO(user).add(linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel())).toList();
    }

    public UserDTO findById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", id))
        );

        UserDTO userDTO = new UserDTO(user);

        userDTO.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());

        return userDTO;
    }

    public UserDTO update(Long id, UserDTO userDTO){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("User with ID %d was not found", id)
                )
        );

        if(userDTO.getFirstName() != null) user.setFirstName(userDTO.getFirstName());
        if(userDTO.getLastName() != null) user.setLastName(userDTO.getLastName());
        if(userDTO.getUsername() != null) user.setUsername(userDTO.getUsername());
        if(userDTO.getProfilePicImgUrl() != null) user.setProfilePicImgUrl(userDTO.getProfilePicImgUrl());
        if(userDTO.getBio() != null) user.setBio(userDTO.getBio());
        if(userDTO.getGender() != null) user.setGender(userDTO.getGender());
        if(userDTO.getBirthDate() != null) user.setBirthDate(userDTO.getBirthDate());

        UserDTO updatedUserDTO = new UserDTO(userRepository.save(user));

        updatedUserDTO.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());

        return updatedUserDTO;

    }

    public void delete(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", id))
        );

        userRepository.delete(user);
    }

    public void addFavoriteDrama(Long userId, Long dramaId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("User with ID %d was not found", userId)
                )
        );

        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Drama with ID %d was not found", dramaId)
                )
        );

        if(user.getPlanToWatch().contains(drama)) throw new ConflictException("Drama has already been added to the Plan to Watch list");

        if(user.getFavoriteDramas().contains(drama)) throw new ConflictException(
                "Drama has already been added to the Favorite Drama list"
        );

        user.getFavoriteDramas().add(drama);
        userRepository.save(user);
    }

    public void removeFavoriteDrama(Long userId, Long dramaId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", userId))
        );
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );

        if(!user.getFavoriteDramas().contains(drama)) throw new ConflictException("Drama was not found in Favorite Dramas list");

        user.getFavoriteDramas().remove(drama);
        userRepository.save(user);
    }

    public void addPlanToWatch(Long userId, Long dramaId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", userId))
        );

        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );

        if(user.getFavoriteDramas().contains(drama)) throw new ConflictException("The drama has already been added to the Favorite Dramas list");
        if(user.getPlanToWatch().contains(drama)) throw new ConflictException("Drama has already been added to the Favorite Drama list");

        user.getPlanToWatch().add(drama);
        userRepository.save(user);
    }

    public void removePlanToWatch(Long userId, Long dramaId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", userId))
        );

        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );


        if(!user.getPlanToWatch().contains(drama)) throw new ConflictException("Drama was not found in Favorite Dramas list");

        user.getPlanToWatch().remove(drama);
        userRepository.save(user);
    }

    public void follow(Long followerId, Long followedId) {

        if(Objects.equals(followerId, followedId)) throw new ConflictException("Users can not follow or unfollow themselves");

        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", followerId))
        );

        User followed = userRepository.findById(followedId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", followedId))
        );

        if(follower.getFollowing().contains(followed) || followed.getFollowers().contains(follower)) throw new ConflictException("User is already following");

        follower.getFollowing().add(followed);
        followed.getFollowers().add(follower);

        userRepository.save(follower);
        userRepository.save(followed);
    }

    public void followActor(Long followerId, Long actorId){
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", followerId))
        );
        Actor actor = actorRepository.findById(actorId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", actorId))
        );

        if(follower.getFollowingActors().contains(actor) || actor.getFollowers().contains(follower)) throw new ConflictException("User is already following");

        follower.getFollowingActors().add(actor);
        actor.getFollowers().add(follower);

        userRepository.save(follower);
        actorRepository.save(actor);
    }


    public void unfollow(Long unfollowingId, Long unfollowedId) {

        if(Objects.equals(unfollowingId, unfollowedId)) throw new ConflictException("Users can not follow or unfollow themselves");

        User unfollowing = userRepository.findById(unfollowingId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", unfollowingId))
        );
        User unfollowed = userRepository.findById(unfollowedId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", unfollowedId))
        );

        if(!unfollowing.getFollowing().contains(unfollowed) || !unfollowed.getFollowers().contains(unfollowing)) throw new ConflictException("User is not following");

        unfollowing.getFollowing().remove(unfollowed);
        unfollowed.getFollowers().remove(unfollowing);

        userRepository.save(unfollowing);
        userRepository.save(unfollowed);
    }

    public void unfollowActor(Long unfollowingId, Long actorId){
        User follower = userRepository.findById(unfollowingId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", unfollowingId))
        );
        Actor actor = actorRepository.findById(actorId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", actorId))
        );

        if(!follower.getFollowingActors().contains(actor) || !actor.getFollowers().contains(follower)) throw new ConflictException("User is not following provided actor");

        follower.getFollowingActors().remove(actor);
        actor.getFollowers().remove(follower);

        userRepository.save(follower);
        actorRepository.save(actor);
    }


}
