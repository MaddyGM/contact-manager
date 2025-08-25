package org.example.contactmanager.controller;

import org.example.contactmanager.model.Position;
import org.example.contactmanager.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private static final Logger logger = LoggerFactory.getLogger(PositionController.class);
    private final PositionService service;

    public PositionController(PositionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        logger.info("GET /api/positions - Fetching all positions");
        List<Position> positions = service.findAll();
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id) {
        logger.info("GET /api/positions/{} - Fetching position", id);
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Position> createPosition(@RequestBody Position position) {
        logger.info("POST /api/positions - Creating position: {}", position.getName());
        Position saved = service.save(position);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id, @RequestBody Position position) {
        logger.info("PUT /api/positions/{} - Updating position to {}", id, position.getName());
        return service.findById(id)
                .map(existing -> {
                    existing.setName(position.getName());
                    Position updated = service.save(existing);
                    logger.info("Position with ID {} updated successfully", id);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        logger.info("DELETE /api/positions/{} - Deleting position", id);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
