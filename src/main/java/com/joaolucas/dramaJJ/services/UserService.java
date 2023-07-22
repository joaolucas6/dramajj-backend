package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.controllers.ActorController;
import com.joaolucas.dramaJJ.controllers.DramaController;
import com.joaolucas.dramaJJ.controllers.UserController;
import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.domain.dto.DramaDTO;
import com.joaolucas.dramaJJ.domain.dto.UserDTO;
import com.joaolucas.dramaJJ.domain.entities.Actor;
import com.joaolucas.dramaJJ.domain.entities.Drama;
import com.joaolucas.dramaJJ.domain.entities.Review;
import com.joaolucas.dramaJJ.domain.entities.User;
import com.joaolucas.dramaJJ.exceptions.ConflictException;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.ReviewRepository;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final DramaRepository dramaRepository;

    private final ActorRepository actorRepository;

    private final ReviewRepository reviewRepository;


    public List<UserDTO> findAll() {
        List<UserDTO> allUsersDTO = new ArrayList<>();

        userRepository.findAll().forEach(user -> allUsersDTO.add(new UserDTO(user)));

        allUsersDTO.forEach(userDTO -> {
            userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getId())).withSelfRel());
        });

        return allUsersDTO;
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

        List<Review> reviews = user.getReviews();
        List<User> followers = user.getFollowers();
        List<User> followings = user.getFollowing();
        List<Actor> followingActor = user.getFollowingActors();

        reviews.forEach(reviewRepository::delete);

        followers.forEach(follower -> {
            follower.getFollowing().remove(user);
            userRepository.save(follower);
        });

        followings.forEach(following -> {
            following.getFollowers().remove(user);
            userRepository.save(following);
        });

        followingActor.forEach(actor -> {
            actor.getFollowers().remove(user);
            actorRepository.save(actor);
        });

        userRepository.delete(user);
    }

    public List<DramaDTO> addFavoriteDrama(Long userId, Long dramaId){
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

        if(user.getFavoriteDramas().contains(drama)) throw new ConflictException(
                "Drama is already in Favorite Dramas list"
        );

        user.getFavoriteDramas().add(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getFavoriteDramas().forEach(favoriteDrama -> list.add(
                new DramaDTO(favoriteDrama)
        ));

        list.forEach(dramaDTO -> {
            dramaDTO.add(linkTo(methodOn(DramaController.class)
                    .findById(dramaDTO.getId())).withSelfRel());
        });

        return list;
    }

    public List<DramaDTO> removeFavoriteDrama(Long userId, Long dramaId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", userId))
        );
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );

        if(!user.getFavoriteDramas().contains(drama)) throw new ConflictException("Drama was not found in Favorite Dramas list");

        user.getFavoriteDramas().remove(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getFavoriteDramas().forEach(
                favoriteDrama -> list.add(new DramaDTO(favoriteDrama))
        );

        list.forEach(dramaDTO -> {
            dramaDTO
                    .add(linkTo(methodOn(DramaController.class)
                    .findById(dramaDTO.getId())).withSelfRel());
        });

        return list;
    }

    public List<DramaDTO> addPlanToWatch(Long userId, Long dramaId) throws Exception {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", userId))
        );

        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );

        if(user.getPlanToWatch().contains(drama)) throw new Exception("Drama is already in Plan to Watch list");

        user.getPlanToWatch().add(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getPlanToWatch().forEach(planToWatchDrama -> list.add(new DramaDTO(planToWatchDrama)));

        list.forEach(dramaDTO -> {
            dramaDTO.add(linkTo(methodOn(DramaController.class).findById(dramaDTO.getId())).withSelfRel());
        });

        return list;

    }

    public List<DramaDTO> removePlanToWatch(Long userId, Long dramaId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with ID %d was not found", userId))
        );

        Drama drama = dramaRepository.findById(dramaId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId))
        );


        if(!user.getPlanToWatch().contains(drama)) throw new Exception("Drama was not found in Favorite Dramas list");

        user.getPlanToWatch().remove(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getPlanToWatch().forEach(
                planToWatchDrama -> list.add(new DramaDTO(planToWatchDrama))
        );

        list.forEach(dramaDTO -> {
            dramaDTO.add(linkTo(methodOn(DramaController.class).findById(dramaDTO.getId())).withSelfRel());
        });

        return list;
    }

    public List<UserDTO> follow(Long followerId, Long followedId) throws Exception {

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

        List<UserDTO> list = new ArrayList<>();
        follower.getFollowing().forEach(user -> list.add(new UserDTO(user)));

        list.forEach(userDTO -> {
            userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getId())).withSelfRel());
        });

        return list;

    }

    public List<ActorDTO> followActor(Long followerId, Long actorId){
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

        List<ActorDTO> list = new ArrayList<>();
        follower.getFollowingActors().forEach(
                listActor -> list.add(new ActorDTO(listActor))
        );

        list.forEach(actorDTO -> {
            actorDTO.add(linkTo(methodOn(ActorController.class).findById(actorDTO.getId())).withSelfRel());
        });

        return list;
    }


    public List<UserDTO> unfollow(Long unfollowingId, Long unfollowedId) throws Exception {

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

        List<UserDTO> list = new ArrayList<>();
        unfollowing.getFollowing().forEach(user -> list.add(new UserDTO(user)));

        list.forEach(userDTO -> {
            userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getId())).withSelfRel());
        });

        return list;

    }

    public List<ActorDTO> unfollowActor(Long unfollowingId, Long actorId){
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

        List<ActorDTO> list = new ArrayList<>();
        follower.getFollowingActors().forEach(
                listActor -> list.add(new ActorDTO(listActor))
        );

        list.forEach(actorDTO -> {
            actorDTO.add(linkTo(methodOn(ActorController.class).findById(actorDTO.getId())).withSelfRel());
        });

        return list;
    }


}
