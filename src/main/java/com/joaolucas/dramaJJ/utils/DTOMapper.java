package com.joaolucas.dramaJJ.utils;

import com.joaolucas.dramaJJ.domain.dto.UserDTO;
import com.joaolucas.dramaJJ.domain.entities.Actor;
import com.joaolucas.dramaJJ.domain.entities.Drama;
import com.joaolucas.dramaJJ.domain.entities.Review;
import com.joaolucas.dramaJJ.domain.entities.User;

import java.util.List;

public class DTOMapper {

    public static User toUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setProfilePicImgUrl(userDTO.getProfilePicImgUrl());
        user.setBio(userDTO.getBio());
        user.setGender(userDTO.getGender());
        user.setBirthDate(userDTO.getBirthDate());
        return user;
    }

    public static User toUser(
            UserDTO userDTO,
            List<Drama> favoriteDramas,
            List<Drama> planToWatch,
            List<Review> reviews,
            List<User> followers,
            List<User> following,
            List<Actor> followingActor
    ){
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setProfilePicImgUrl(userDTO.getProfilePicImgUrl());
        user.setBio(userDTO.getBio());
        user.setGender(userDTO.getGender());
        user.setBirthDate(userDTO.getBirthDate());

        user.setFavoriteDramas(favoriteDramas);
        user.setPlanToWatch(planToWatch);
        user.setReviews(reviews);
        user.setFollowers(followers);
        user.setFollowing(following);
        user.setFollowingActors(followingActor);

        return user;
    }
    
}
