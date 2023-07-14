package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.domain.dto.DramaDTO;
import com.joaolucas.dramaJJ.domain.dto.UserDTO;
import com.joaolucas.dramaJJ.domain.entities.Drama;
import com.joaolucas.dramaJJ.domain.entities.User;
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


    public List<UserDTO> findAll() {
        List<UserDTO> list = new ArrayList<>();
        userRepository.findAll().forEach(user -> list.add(new UserDTO(user)));
        return list;
    }

    public UserDTO findById(Long id){
        User user = userRepository.findById(id).orElseThrow();
        return new UserDTO(user);
    }

    public UserDTO update(Long id, UserDTO userDTO){

        User user = userRepository.findById(id).orElseThrow();

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
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

    public List<DramaDTO> addFavoriteDrama(Long userId, Long dramaId) throws Exception {


        User user = userRepository.findById(userId).orElseThrow();
        Drama drama = dramaRepository.findById(dramaId).orElseThrow();

        if(user.getFavoriteDramas().contains(drama)) throw new Exception("");

        user.getFavoriteDramas().add(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getFavoriteDramas().forEach(favoriteDrama -> list.add(new DramaDTO(favoriteDrama)));

        return list;
    }

    public List<DramaDTO> removeFavoriteDrama(Long userId, Long dramaId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        Drama drama = dramaRepository.findById(dramaId).orElseThrow();

        if(!user.getFavoriteDramas().contains(drama)) throw new Exception("");

        user.getFavoriteDramas().remove(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getFavoriteDramas().forEach(favoriteDrama -> list.add(new DramaDTO(favoriteDrama)));

        return list;
    }

    public List<DramaDTO> addPlanToWatch(Long userId, Long dramaId) throws Exception {

        User user = userRepository.findById(userId).orElseThrow();
        Drama drama = dramaRepository.findById(dramaId).orElseThrow();

        if(user.getPlanToWatch().contains(drama)) throw new Exception("");

        user.getPlanToWatch().add(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getPlanToWatch().forEach(planToWatchDrama -> list.add(new DramaDTO(planToWatchDrama)));

        return list;

    }

    public List<DramaDTO> removePlanToWatch(Long userId, Long dramaId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        Drama drama = dramaRepository.findById(dramaId).orElseThrow();

        if(!user.getPlanToWatch().contains(drama)) throw new Exception("");

        user.getPlanToWatch().remove(drama);
        userRepository.save(user);

        List<DramaDTO> list = new ArrayList<>();

        user.getPlanToWatch().forEach(planToWatchDrama -> list.add(new DramaDTO(planToWatchDrama)));

        return list;
    }

    public List<User> follow(Long followerId, Long followedId) throws Exception {

        User follower = userRepository.findById(followerId).orElseThrow();
        User followed = userRepository.findById(followedId).orElseThrow();

        if(follower.getFollowing().contains(followed) || followed.getFollowers().contains(follower)) throw new Exception("");

        follower.getFollowing().add(followed);
        followed.getFollowers().add(follower);

        userRepository.save(follower);
        userRepository.save(followed);

        return follower.getFollowing();

    }

    public List<User> unfollow(Long followerId, Long followedId) throws Exception {

        User unfollowing = userRepository.findById(followerId).orElseThrow();
        User unfollowed = userRepository.findById(followedId).orElseThrow();

        if(!unfollowing.getFollowing().contains(unfollowed) || !unfollowed.getFollowers().contains(unfollowing)) throw new Exception("");

        unfollowing.getFollowing().remove(unfollowed);
        unfollowed.getFollowers().remove(unfollowing);

        userRepository.save(unfollowing);
        userRepository.save(unfollowed);

        return unfollowing.getFollowing();

    }


}
