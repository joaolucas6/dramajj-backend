package com.joaolucas.dramaJJ.utils;

import com.joaolucas.dramaJJ.models.dto.*;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class DataValidation {

    public static boolean isUserInfoValid(UserDTO request){

        if(isAllFieldsNull(request)) return false;
        if(request.getFirstName() != null && request.getFirstName().isBlank() || request.getFirstName() != null && request.getFirstName().length() > 25) return false;
        if(request.getLastName() != null && request.getLastName().isBlank() || request.getLastName() != null && request.getLastName().length() > 25) return false;
        if(request.getUsername() != null && request.getUsername().isBlank() || request.getUsername() != null && request.getUsername().length() > 15) return false;
        if(request.getBio() != null && request.getBio().isBlank() || request.getBio() != null && request.getBio().length() > 160) return false;
        if(request.getBirthDate() != null && request.getBirthDate().isAfter(LocalDate.now().minusYears(12))) return false;
        if(request.getProfilePicImgUrl() != null && request.getProfilePicImgUrl().isBlank()) return false;

        return true;
    }

    public static boolean isReviewInfoValid(ReviewDTO request){
        if(isAllFieldsNull(request)) return false;
        if(request.getRating() > 10 || request.getRating() < 0) return false;
        if(request.getTitle() != null && request.getTitle().isBlank() || request.getTitle() != null && request.getTitle().length() > 300) return false;
        if(request.getText() != null && request.getText().isBlank() || request.getText() != null && request.getText().length() > 4500) return false;
        return true;
    }

    public static boolean isGenreInfoValid(GenreDTO request){
        if(isAllFieldsNull(request)) return false;
        if(request.getName() != null && request.getName().isBlank() || request.getName() != null && request.getName().length() > 30) return false;
        return true;
    }

    public static boolean isDramaInfoValid(DramaDTO request){

        if(isAllFieldsNull(request)) return false;
        if(request.getName() != null && request.getName().isBlank() || request.getName() != null && request.getName().length() > 30) return false;
        if(request.getSynopsis() != null && request.getSynopsis().isBlank() || request.getSynopsis() != null && request.getSynopsis().length() > 1000) return false;
        if(request.getReleaseDate().isAfter(LocalDate.now())) return false;
        if(request.getEpisodeNumber() != null && request.getEpisodeNumber() < 0) return false;
        if(request.getPosterImgUrl() != null && request.getPosterImgUrl().isBlank()) return false;

        return true;
    }

    public static boolean isActorInfoValid(ActorDTO request){
        if(isAllFieldsNull(request)) return false;
        if(request.getFirstName() != null && request.getFirstName().isBlank() || request.getFirstName() != null && request.getFirstName().length() > 25) return false;
        if(request.getLastName() != null && request.getLastName().isBlank() || request.getLastName() != null && request.getLastName().length() > 25) return false;
        if(request.getSurname() != null && request.getSurname().isBlank() || request.getSurname() != null && request.getSurname().length() > 30) return false;
        if(request.getBirthDate() != null && request.getBirthDate().isAfter(LocalDate.now())) return false;
        if(request.getPictureUrl() != null && request.getPictureUrl().isBlank()) return false;
        if(request.getNationality() != null && request.getNationality().length() > 30 || request.getNationality() != null && request.getNationality().isBlank()) return false;
        if(request.getBio() != null && request.getBio().isBlank() || request.getBio() != null && request.getBio().length() > 160) return false;

        return true;
    }

    public static boolean isRegisterRequestValid(RegisterRequest request){
        if(isAllFieldsNull(request)) return false;
        if(request.firstName() == null || request.firstName().isBlank() || request.firstName().length() > 30) return false;
        if(request.lastName() == null || request.lastName().isBlank() || request.lastName().length() > 30) return false;
        if(request.username() == null || request.username().isBlank() || request.username().length() > 30) return false;
        if(request.password() == null || request.password().isBlank() || request.password().length() > 60) return false;

        return true;
    }

    private static boolean isAllFieldsNull(Object object)  {

        Field[] fields = object.getClass().getDeclaredFields();

        for(Field field : fields){
            field.setAccessible(true);

            try {
                if(field.get(object) != null) return false;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }

        return true;
    }
}
