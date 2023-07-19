package com.joaolucas.dramaJJ.controllers;

import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.domain.dto.DramaDTO;
import com.joaolucas.dramaJJ.domain.dto.UserDTO;
import com.joaolucas.dramaJJ.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){

        List<UserDTO> userDTOS = userService.findAll();




        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/favorite-dramas/{userId}/{dramaId}")
    public ResponseEntity<List<DramaDTO>> addFavoriteDrama(@PathVariable Long userId, @PathVariable Long dramaId) throws Exception {
        return ResponseEntity.ok(userService.addFavoriteDrama(userId, dramaId));
    }

    @DeleteMapping("/favorite-dramas/{userId}/{dramaId}")
    public ResponseEntity<List<DramaDTO>> removeFavoriteDrama(@PathVariable Long userId, @PathVariable Long dramaId) throws Exception {
        return ResponseEntity.ok(userService.removeFavoriteDrama(userId, dramaId));
    }

    @PostMapping("/plan-to-watch/{userId}/{dramaId}")
    public ResponseEntity<List<DramaDTO>> addPlanToWatch(@PathVariable Long userId, @PathVariable Long dramaId) throws Exception {
        return ResponseEntity.ok(userService.addPlanToWatch(userId, dramaId));
    }

    @DeleteMapping("/plan-to-watch/{userId}/{dramaId}")
    public ResponseEntity<List<DramaDTO>> removePlanToWatch(@PathVariable Long userId, @PathVariable Long dramaId) throws Exception {
        return ResponseEntity.ok(userService.removePlanToWatch(userId, dramaId));
    }

    @PostMapping("/follow/{followerId}/{followedId}")
    public ResponseEntity<List<UserDTO>> follow(@PathVariable Long followerId, @PathVariable Long followedId) throws Exception {
        return ResponseEntity.ok(userService.follow(followerId, followedId));
    }

    @DeleteMapping("/unfollow/{unfollowingId}/{unfollowedId}")
    public ResponseEntity<List<UserDTO>> unfollow(@PathVariable Long unfollowingId, @PathVariable Long unfollowedId) throws Exception {
        return ResponseEntity.ok(userService.unfollow(unfollowingId, unfollowedId));
    }

    @PostMapping("/follow-actor/{followerId}/{actorId}")
    public ResponseEntity<List<ActorDTO>> followActor(@PathVariable Long followerId, @PathVariable Long actorId){
        return ResponseEntity.ok(userService.followActor(followerId, actorId));
    }

    @DeleteMapping("/unfollow-actor/{unfollowingId}/{actorId}")
    public ResponseEntity<List<ActorDTO>> unfollowActor(@PathVariable Long unfollowingId, @PathVariable Long actorId){
        return ResponseEntity.ok(userService.unfollowActor(unfollowingId, actorId));
    }

}
