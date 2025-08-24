package org.example.contactmanager.controller;

import org.example.contactmanager.model.Position;
import org.example.contactmanager.service.PositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PositionControllerTest {

    @Mock
    private PositionService positionService;

    @InjectMocks
    private PositionController positionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPositions() {
        Position p1 = new Position("Developer");
        Position p2 = new Position("Tester");
        when(positionService.findAll()).thenReturn(Arrays.asList(p1, p2));

        ResponseEntity<List<Position>> response = positionController.getAllPositions();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetPositionById_Found() {
        Position p = new Position("Scrum Master");
        p.setId(1L);
        when(positionService.findById(1L)).thenReturn(Optional.of(p));

        ResponseEntity<Position> response = positionController.getPositionById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Scrum Master", response.getBody().getName());
    }

    @Test
    void testGetPositionById_NotFound() {
        when(positionService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Position> response = positionController.getPositionById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreatePosition() {
        Position p = new Position("Product Owner");
        when(positionService.save(p)).thenReturn(p);

        ResponseEntity<Position> response = positionController.createPosition(p);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product Owner", response.getBody().getName());
    }

    @Test
    void testUpdatePosition_Found() {
        Position existing = new Position("Old Name");
        existing.setId(1L);
        Position updatedData = new Position("New Name");

        when(positionService.findById(1L)).thenReturn(Optional.of(existing));
        when(positionService.save(existing)).thenReturn(existing);

        ResponseEntity<Position> response = positionController.updatePosition(1L, updatedData);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Name", response.getBody().getName());
    }

    @Test
    void testUpdatePosition_NotFound() {
        Position updatedData = new Position("New Name");
        when(positionService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Position> response = positionController.updatePosition(1L, updatedData);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeletePosition() {
        doNothing().when(positionService).deleteById(1L);

        ResponseEntity<Void> response = positionController.deletePosition(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(positionService, times(1)).deleteById(1L);
    }
}
