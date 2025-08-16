package org.example.contactmanager.service;

import org.example.contactmanager.model.Contact;
import org.example.contactmanager.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllContacts() {
        List<Contact> contacts = Arrays.asList(
                new Contact(1L, "Max", "Mustermann", "max@example.com"),
                new Contact(2L, "Erika", "Doe", "jerika@example.com")
        );
        when(contactRepository.findAll()).thenReturn(contacts);

        List<Contact> result = contactService.getAllContacts();
        assertEquals(2, result.size());
        verify(contactRepository, times(1)).findAll();
    }

    @Test
    void testGetContactById() {
        Contact contact = new Contact(1L, "Max", "Mustermann", "max@example.com");
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        Optional<Contact> result = contactService.getContactById(1L);
        assertTrue(result.isPresent());
        assertEquals("Max", result.get().getFirstName());
    }

    @Test
    void testSaveContact() {
        Contact contact = new Contact(null, "Alice", "Smith", "alice@example.com");
        when(contactRepository.save(contact)).thenReturn(new Contact(3L, "Alice", "Smith", "alice@example.com"));

        Contact saved = contactService.saveContact(contact);
        assertNotNull(saved.getId());
        assertEquals("Alice", saved.getFirstName());
    }

    @Test
    void testDeleteContact() {
        Long id = 1L;
        doNothing().when(contactRepository).deleteById(id);
        contactService.deleteContact(id);
        verify(contactRepository, times(1)).deleteById(id);
    }

}
