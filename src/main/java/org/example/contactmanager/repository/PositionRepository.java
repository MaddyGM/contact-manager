package org.example.contactmanager.repository;

import org.example.contactmanager.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
