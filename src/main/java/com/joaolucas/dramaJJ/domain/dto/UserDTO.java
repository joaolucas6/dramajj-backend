package com.joaolucas.dramaJJ.domain.dto;

import com.joaolucas.dramaJJ.domain.entities.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class UserDTO extends RepresentationModel<UserDTO> {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String profilePicImgUrl;
    private String bio;
    private String gender;
    private Date birthDate;
    private List<Long> favoriteDramasId = new ArrayList<>();
    private List<Long> planToWatchId = new ArrayList<>();
    private List<Long> reviewsId = new ArrayList<>();
    private List<Long> followersId = new ArrayList<>();
    private List<Long> followingId = new ArrayList<>();
    private List<Long> followingActorsId = new ArrayList<>();

    public UserDTO(){

    }

    public UserDTO(User user){

        setId(user.getId());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setUsername(user.getUsername());
        setProfilePicImgUrl(user.getProfilePicImgUrl());
        setBio(user.getBio());
        setGender(user.getGender());
        setBirthDate(user.getBirthDate());

        user.getFavoriteDramas().forEach(drama -> favoriteDramasId.add(drama.getId()));
        user.getPlanToWatch().forEach(drama -> planToWatchId.add(drama.getId()));
        user.getReviews().forEach(review -> reviewsId.add(review.getId()));
        user.getFollowers().forEach(follower -> followersId.add(follower.getId()));
        user.getFollowing().forEach(following -> followingId.add(following.getId()));
        user.getFollowingActors().forEach(followingActor -> followingActorsId.add(followingActor.getId()));

    }

}
