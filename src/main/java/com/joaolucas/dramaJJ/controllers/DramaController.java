package com.joaolucas.dramaJJ.controllers;

import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.domain.dto.DramaDTO;
import com.joaolucas.dramaJJ.domain.dto.GenreDTO;
import com.joaolucas.dramaJJ.services.DramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dramas")
public class DramaController {
    @Autowired
    private DramaService dramaService;

    @GetMapping
    public ResponseEntity<List<DramaDTO>> findAll(){
        return ResponseEntity.ok(dramaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DramaDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(dramaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DramaDTO> create(@RequestBody DramaDTO dramaDTO){
        return ResponseEntity.ok(dramaService.create(dramaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DramaDTO> update(@PathVariable Long id, @RequestBody DramaDTO dramaDTO){
        return ResponseEntity.ok(dramaService.update(id, dramaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        dramaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/casting/{actorId}/{dramaId}")
    public ResponseEntity<List<ActorDTO>> addActor(@PathVariable Long actorId, @PathVariable Long dramaId) throws Exception {
        return ResponseEntity.ok(dramaService.addActor(actorId, dramaId));
    }

    @DeleteMapping("/casting/{actorId}/{dramaId}")
    public ResponseEntity<List<ActorDTO>> removeActor(@PathVariable Long actorId, @PathVariable Long dramaId) throws Exception {
        return ResponseEntity.ok(dramaService.removeActor(actorId, dramaId));
    }

    @PostMapping("/genres/{genreId}/{dramaId}")
    public ResponseEntity<List<GenreDTO>> addGenre(@PathVariable Long genreId, @PathVariable Long dramaId) throws Exception {
        return ResponseEntity.ok(dramaService.addGenre(genreId, dramaId));
    }

    @DeleteMapping("/genres/{genreId}/{dramaId}")
    public ResponseEntity<List<GenreDTO>> removeGenre(@PathVariable Long genreId, @PathVariable Long dramaId) throws Exception {
        return ResponseEntity.ok(dramaService.removeGenre(genreId, dramaId));
    }
}
