package com.joaolucas.dramaJJ.controllers;

import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.services.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public ResponseEntity<List<ActorDTO>> findAll(){
        return ResponseEntity.ok(actorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(actorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ActorDTO> create(@RequestBody ActorDTO actorDTO){
        return ResponseEntity.ok(actorService.create(actorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorDTO> update(@PathVariable Long id, @RequestBody ActorDTO actorDTO){
        return ResponseEntity.ok(actorService.update(id, actorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        actorService.delete(id);
        return ResponseEntity.ok().build();
    }




}
