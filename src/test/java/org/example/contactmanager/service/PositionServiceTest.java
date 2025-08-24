package org.example.contactmanager.service;

import org.example.contactmanager.model.Position;
import org.example.contactmanager.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class PositionServiceTest {

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private PositionService positionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPositions() {
        Position dev = new Position("Developer");
        Position po = new Position("Product Owner");
        when(positionRepository.findAll()).thenReturn(Arrays.asList(dev, po));

        List<Position> positions = positionService.findAll();
        assertEquals(2, positions.size());
        assertEquals("Developer", positions.get(0).getName());
        assertEquals("Product Owner", positions.get(1).getName());
    }

    @Test
    void testGetPositionById() {
        Position dev = new Position("Developer");
        dev.setId(1L);
        when(positionRepository.findById(1L)).thenReturn(Optional.of(dev));

        Optional<Position> position = positionService.findById(1L);
        assertTrue(position.isPresent());
        assertEquals("Developer", position.get().getName());
    }
}
