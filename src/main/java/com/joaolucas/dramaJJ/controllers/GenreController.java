package com.joaolucas.dramaJJ.controllers;


import com.joaolucas.dramaJJ.domain.dto.ActorDTO;
import com.joaolucas.dramaJJ.domain.dto.GenreDTO;
import com.joaolucas.dramaJJ.services.ActorService;
import com.joaolucas.dramaJJ.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    @Autowired
    private GenreService genreService;


    @GetMapping
    public ResponseEntity<List<GenreDTO>> findAll(){
        return ResponseEntity.ok(genreService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(genreService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GenreDTO> create(@RequestBody GenreDTO genreDTO){
        return ResponseEntity.ok(genreService.create(genreDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> update(@PathVariable Long id, @RequestBody GenreDTO genreDTO){
        return ResponseEntity.ok(genreService.update(id, genreDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        genreService.delete(id);
        return ResponseEntity.ok().build();
    }

}
