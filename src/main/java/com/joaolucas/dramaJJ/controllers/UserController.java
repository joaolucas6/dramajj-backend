package com.joaolucas.dramaJJ.controllers;

import com.joaolucas.dramaJJ.domain.dto.UserDTO;
import com.joaolucas.dramaJJ.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        return ResponseEntity.ok(userService.findAll());
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
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/favorite-dramas/{userId}/{dramaId}")
    public ResponseEntity<Void> addFavoriteDrama(@PathVariable Long userId, @PathVariable Long dramaId) throws Exception {
        userService.addFavoriteDrama(userId, dramaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/favorite-dramas/{userId}/{dramaId}")
    public ResponseEntity<Void> removeFavoriteDrama(@PathVariable Long userId, @PathVariable Long dramaId) throws Exception {
        userService.removeFavoriteDrama(userId, dramaId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/plan-to-watch/{userId}/{dramaId}")
    public ResponseEntity<Void> addPlanToWatch(@PathVariable Long userId, @PathVariable Long dramaId) throws Exception {
        userService.addPlanToWatch(userId, dramaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/plan-to-watch/{userId}/{dramaId}")
    public ResponseEntity<Void> removePlanToWatch(@PathVariable Long userId, @PathVariable Long dramaId) throws Exception {
        userService.removePlanToWatch(userId, dramaId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/follow/{followerId}/{followedId}")
    public ResponseEntity<Void> follow(@PathVariable Long followerId, @PathVariable Long followedId) throws Exception {
        userService.follow(followerId, followedId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unfollow/{unfollowingId}/{unfollowedId}")
    public ResponseEntity<Void> unfollow(@PathVariable Long unfollowingId, @PathVariable Long unfollowedId) throws Exception {
        userService.unfollow(unfollowingId, unfollowedId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/follow-actor/{followerId}/{actorId}")
    public ResponseEntity<Void> followActor(@PathVariable Long followerId, @PathVariable Long actorId){
        userService.followActor(followerId, actorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unfollow-actor/{unfollowingId}/{actorId}")
    public ResponseEntity<Void> unfollowActor(@PathVariable Long unfollowingId, @PathVariable Long actorId){
        userService.unfollowActor(unfollowingId, actorId);
        return ResponseEntity.ok().build();
    }

}
