package org.example.contactmanager.service;

import org.example.contactmanager.model.Position;
import org.example.contactmanager.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {

    private static final Logger logger = LoggerFactory.getLogger(PositionService.class);
    private final PositionRepository repository;

    public PositionService(PositionRepository repository) {
        this.repository = repository;
    }

    public List<Position> findAll() {
        logger.info("Fetching all positions...");
        return repository.findAll();
    }

    public Optional<Position> findById(Long id) {
        logger.info("Fetching position with ID {}", id);
        return repository.findById(id);
    }

    public Position save(Position position) {
        logger.info("Saving position: {}", position.getName());
        return repository.save(position);
    }

    public void deleteById(Long id) {
        logger.info("Deleting position with ID {}", id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Position with ID " + id + " does not exist.");
        }
        repository.deleteById(id);
        logger.info("Position with ID {} deleted successfully", id);
    }
}
