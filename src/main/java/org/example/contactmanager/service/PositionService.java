package org.example.contactmanager.service;

import org.example.contactmanager.model.Position;
import org.example.contactmanager.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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
        try {
            return repository.findAll();
        } catch (DataAccessException ex) {
            logger.error("Error fetching positions from database", ex);
            throw new RuntimeException("Unable to fetch positions. Please try again later.");
        }
    }

    public Optional<Position> findById(Long id) {
        logger.info("Fetching position with ID {}", id);
        try {
            return repository.findById(id);
        } catch (DataAccessException ex) {
            logger.error("Error fetching position with ID {}", id, ex);
            throw new RuntimeException("Unable to fetch position with ID " + id);
        }
    }

    public Position save(Position position) {
        logger.info("Saving position: {}", position.getName());
        try {
            return repository.save(position);
        } catch (DataAccessException ex) {
            logger.error("Error saving position: {}", position.getName(), ex);
            throw new RuntimeException("Unable to save position. Please check your data.");
        }
    }

    public void deleteById(Long id) {
        logger.info("Deleting position with ID {}", id);
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                logger.info("Position with ID {} deleted successfully", id);
            } else {
                logger.warn("Attempted to delete non-existing position with ID {}", id);
                throw new RuntimeException("Position with ID " + id + " does not exist.");
            }
        } catch (DataAccessException ex) {
            logger.error("Error deleting position with ID {}", id, ex);
            throw new RuntimeException("Unable to delete position with ID " + id);
        }
    }
}
