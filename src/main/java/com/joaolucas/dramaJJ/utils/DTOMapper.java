package com.joaolucas.dramaJJ.utils;

import com.joaolucas.dramaJJ.domain.dto.*;
import com.joaolucas.dramaJJ.domain.entities.*;

import java.util.List;

public class DTOMapper {


    public static User toUser(
            UserDTO userDTO,
            String password,
            List<Drama> favoriteDramas,
            List<Drama> planToWatch,
            List<Review> reviews,
            List<User> followers,
            List<User> following,
            List<Actor> followingActor
    ){

        return User
                .builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .profilePicImgUrl(userDTO.getProfilePicImgUrl())
                .bio(userDTO.getBio())
                .gender(userDTO.getGender())
                .birthDate(userDTO.getBirthDate())
                .password(password)
                .favoriteDramas(favoriteDramas)
                .planToWatch(planToWatch)
                .reviews(reviews)
                .followers(followers)
                .following(following)
                .followingActors(followingActor)
                .build();
    }

    public static Review toReview(ReviewDTO reviewDTO, User author, Drama drama){

        return Review
                .builder()
                .id(reviewDTO.getId())
                .title(reviewDTO.getTitle())
                .text(reviewDTO.getText())
                .author(author)
                .drama(drama)
                .rating(reviewDTO.getRating())
                .instant(reviewDTO.getInstant())
                .build();
    }


    public static Drama toDrama(DramaDTO dramaDTO, List<Genre> genres, List<Actor> casting, List<Review> reviews){
        return Drama
                .builder()
                .id(dramaDTO.getId())
                .name(dramaDTO.getName())
                .synopsis(dramaDTO.getSynopsis())
                .releaseDate(dramaDTO.getReleaseDate())
                .posterImgUrl(dramaDTO.getPosterImgUrl())
                .episodeNumber(dramaDTO.getEpisodeNumber())
                .genres(genres)
                .casting(casting)
                .reviews(reviews)
                .rates(dramaDTO.getRates())
                .build();
    }


    public static Actor toActor(ActorDTO actorDTO, List<User> followers, List<Drama> dramas){
        return Actor
                .builder()
                .id(actorDTO.getId())
                .firstName(actorDTO.getFirstName())
                .lastName(actorDTO.getLastName())
                .surname(actorDTO.getSurname())
                .gender(actorDTO.getGender())
                .birthDate(actorDTO.getBirthDate())
                .pictureUrl(actorDTO.getPictureUrl())
                .nationality(actorDTO.getNationality())
                .bio(actorDTO.getBio())
                .followers(followers)
                .dramas(dramas)
                .build();
    }

    public static Genre toGenre(GenreDTO genreDTO, List<Drama> dramas){
        return Genre
                .builder()
                .id(genreDTO.getId())
                .name(genreDTO.getName())
                .dramas(dramas)
                .build();

    }


}
