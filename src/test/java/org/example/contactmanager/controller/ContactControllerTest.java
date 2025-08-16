package org.example.contactmanager.controller;

import org.example.contactmanager.model.Contact;
import org.example.contactmanager.service.ContactService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetContactById() {
        Contact contact = new Contact(1L, "John", "Doe", "john@example.com");
        when(contactService.getContactById(1L)).thenReturn(Optional.of(contact));

        ResponseEntity<Contact> response = contactController.getContactById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstName());
    }

    @Test
    void testGetContactById_NotFound() {
        when(contactService.getContactById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Contact> response = contactController.getContactById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
