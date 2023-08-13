package com.joaolucas.dramaJJ.controllers;

import com.joaolucas.dramaJJ.models.dto.DramaDTO;
import com.joaolucas.dramaJJ.services.DramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dramas")
@RequiredArgsConstructor
public class DramaController {

    private final DramaService dramaService;

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
    public ResponseEntity<Void> delete(@PathVariable Long id){
        dramaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/casting/{actorId}/{dramaId}")
    public ResponseEntity<Void> addActor(@PathVariable Long actorId, @PathVariable Long dramaId) throws Exception {
        dramaService.addActor(actorId, dramaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/casting/{actorId}/{dramaId}")
    public ResponseEntity<Void> removeActor(@PathVariable Long actorId, @PathVariable Long dramaId) throws Exception {
        dramaService.removeActor(actorId, dramaId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/genres/{genreId}/{dramaId}")
    public ResponseEntity<Void> addGenre(@PathVariable Long genreId, @PathVariable Long dramaId) throws Exception {
        dramaService.addGenre(genreId, dramaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/genres/{genreId}/{dramaId}")
    public ResponseEntity<Void> removeGenre(@PathVariable Long genreId, @PathVariable Long dramaId) throws Exception {
        dramaService.removeGenre(genreId, dramaId);
        return ResponseEntity.ok().build();
    }
}
