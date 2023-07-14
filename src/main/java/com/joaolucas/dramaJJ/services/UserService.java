package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.domain.dto.DramaDTO;
import com.joaolucas.dramaJJ.domain.dto.UserDTO;
import com.joaolucas.dramaJJ.domain.entities.Actor;
import com.joaolucas.dramaJJ.domain.entities.Drama;
import com.joaolucas.dramaJJ.domain.entities.User;
import com.joaolucas.dramaJJ.exceptions.ConflictException;
import com.joaolucas.dramaJJ.exceptions.ResourceNotFoundException;
import com.joaolucas.dramaJJ.repositories.ActorRepository;
import com.joaolucas.dramaJJ.repositories.DramaRepository;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DramaRepository dramaRepository;

    @Autowired
    private ActorRepository actorRepository;


    public List<UserDTO> findAll() {
        List<UserDTO> list = new ArrayList<>();
        userRepository.findAll().forEach(user -> list.add(new UserDTO(user)));
        return list;
    }

    public UserDTO findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", id)));
        return new UserDTO(user);
    }

    public UserDTO update(Long id, UserDTO userDTO){

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", id)));

        if(userDTO.getFirstName() != null) user.setFirstName(userDTO.getFirstName());
        if(userDTO.getLastName() != null) user.setLastName(userDTO.getLastName());
        if(userDTO.getUsername() != null) user.setUsername(userDTO.getUsername());
        if(userDTO.getProfilePicImgUrl() != null) user.setProfilePicImgUrl(userDTO.getProfilePicImgUrl());
        if(userDTO.getBio() != null) user.setBio(userDTO.getBio());
        if(userDTO.getGender() != null) user.setGender(userDTO.getGender());
        if(userDTO.getBirthDate() != null) user.setBirthDate(userDTO.getBirthDate());

        userRepository.save(user);

        return new UserDTO(user);


    }

    public void delete(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", id)));
        userRepository.delete(user);
    }

    public List<DramaDTO> addFavoriteDrama(Long userId, Long dramaId) throws Exception {


        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", userId)));
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId)));

        if(user.getFavoriteDramas().contains(drama)) throw new Exception("Drama is already in Favorite Dramas list");

        user.getFavoriteDramas().add(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getFavoriteDramas().forEach(favoriteDrama -> list.add(new DramaDTO(favoriteDrama)));

        return list;
    }

    public List<DramaDTO> removeFavoriteDrama(Long userId, Long dramaId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", userId)));
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId)));

        if(!user.getFavoriteDramas().contains(drama)) throw new ConflictException("Drama was not found in Favorite Dramas list");

        user.getFavoriteDramas().remove(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getFavoriteDramas().forEach(favoriteDrama -> list.add(new DramaDTO(favoriteDrama)));

        return list;
    }

    public List<DramaDTO> addPlanToWatch(Long userId, Long dramaId) throws Exception {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", userId)));
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId)));

        if(user.getPlanToWatch().contains(drama)) throw new Exception("Drama is already in Plan to Watch list");

        user.getPlanToWatch().add(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getPlanToWatch().forEach(planToWatchDrama -> list.add(new DramaDTO(planToWatchDrama)));

        return list;

    }

    public List<DramaDTO> removePlanToWatch(Long userId, Long dramaId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", userId)));
        Drama drama = dramaRepository.findById(dramaId).orElseThrow(() -> new ResourceNotFoundException(String.format("Drama with ID %d was not found", dramaId)));


        if(!user.getPlanToWatch().contains(drama)) throw new Exception("Drama was not found in Favorite Dramas list");

        user.getPlanToWatch().remove(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getPlanToWatch().forEach(planToWatchDrama -> list.add(new DramaDTO(planToWatchDrama)));

        return list;
    }

    public List<User> follow(Long followerId, Long followedId) throws Exception {

        User follower = userRepository.findById(followerId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", followerId)));
        User followed = userRepository.findById(followedId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", followedId)));

        if(follower.getFollowing().contains(followed) || followed.getFollowers().contains(follower)) throw new ConflictException("User is already following");

        follower.getFollowing().add(followed);
        followed.getFollowers().add(follower);

        userRepository.save(follower);
        userRepository.save(followed);

        return follower.getFollowing();

    }

    public List<ActorDTO> followActor(Long followerId, Long actorId){
        User follower = userRepository.findById(followerId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", followerId)));
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", actorId)));

        if(follower.getFollowingActors().contains(actor) || actor.getFollowers().contains(follower)) throw new ConflictException("User is already following");

        follower.getFollowingActors().add(actor);
        actor.getFollowers().add(follower);

        userRepository.save(follower);
        actorRepository.save(actor);

        List<ActorDTO> list = new ArrayList<>();
        follower.getFollowingActors().forEach(listActor -> list.add(new ActorDTO(listActor)));

        return list;
    }


    public List<User> unfollow(Long unfollowingId, Long unfollowedId) throws Exception {

        User unfollowing = userRepository.findById(unfollowingId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", unfollowingId)));
        User unfollowed = userRepository.findById(unfollowedId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", unfollowedId)));

        if(!unfollowing.getFollowing().contains(unfollowed) || !unfollowed.getFollowers().contains(unfollowing)) throw new ConflictException("User is not following");

        unfollowing.getFollowing().remove(unfollowed);
        unfollowed.getFollowers().remove(unfollowing);

        userRepository.save(unfollowing);
        userRepository.save(unfollowed);

        return unfollowing.getFollowing();

    }

    public List<ActorDTO> unfollowActor(Long unfollowingId, Long actorId){
        User follower = userRepository.findById(unfollowingId).orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d was not found", unfollowingId)));
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with ID %d was not found", actorId)));

        if(!follower.getFollowingActors().contains(actor) || !actor.getFollowers().contains(follower)) throw new ConflictException("User is not following provided actor");

        follower.getFollowingActors().remove(actor);
        actor.getFollowers().remove(follower);

        userRepository.save(follower);
        actorRepository.save(actor);

        List<ActorDTO> list = new ArrayList<>();
        follower.getFollowingActors().forEach(listActor -> list.add(new ActorDTO(listActor)));

        return list;
    }


}
