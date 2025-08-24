package org.example.contactmanager.service;

import org.example.contactmanager.model.Contact;
import org.example.contactmanager.model.Position;
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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllContacts() {
        Position developer = new Position();
        developer.setId(1L);
        developer.setName("Developer");

        Contact contact1 = new Contact("Max", "Mustermann", "max@example.com");
        contact1.setId(1L);
        contact1.setPosition(developer);

        Contact contact2 = new Contact("Erika", "Doe", "erika@example.com");
        contact2.setId(2L);
        contact2.setPosition(developer);

        List<Contact> contacts = Arrays.asList(contact1, contact2);

        when(contactRepository.findAll()).thenReturn(contacts);

        List<Contact> result = contactService.getAllContacts();

        assertEquals(2, result.size());
        assertEquals("Developer", result.getFirst().getPosition().getName());
        verify(contactRepository, times(1)).findAll();
    }

    @Test
    void testGetContactById() {
        Position productOwner = new Position();
        productOwner.setId(1L);
        productOwner.setName("Product Owner");

        Contact contact = new Contact("Max", "Mustermann", "max@example.com");
        contact.setId(1L);
        contact.setPosition(productOwner);

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        Optional<Contact> result = contactService.getContactById(1L);

        assertTrue(result.isPresent());
        assertEquals("Max", result.get().getFirstName());
        assertEquals("Product Owner", result.get().getPosition().getName());
    }

    @Test
    void testSaveContact() {
        Position tester = new Position();
        tester.setId(1L);
        tester.setName("Tester");

        Contact contact = new Contact("Alice", "Smith", "alice@example.com");
        contact.setPosition(tester);

        Contact savedContact = new Contact("Alice", "Smith", "alice@example.com");
        savedContact.setId(3L);
        savedContact.setPosition(tester);

        when(contactRepository.save(contact)).thenReturn(savedContact);

        Contact saved = contactService.saveContact(contact);

        assertNotNull(saved.getId());
        assertEquals("Alice", saved.getFirstName());
        assertEquals("Tester", saved.getPosition().getName());
    }

    @Test
    void testDeleteContact() {
        Long id = 1L;

        Position tester = new Position();
        tester.setId(1L);
        tester.setName("Tester");

        Contact contact = new Contact("Alice", "Smith", "alice@example.com");
        contact.setId(id);
        contact.setPosition(tester);

        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));
        doNothing().when(contactRepository).deleteById(id);

        contactService.deleteContact(id);

        verify(contactRepository, times(1)).deleteById(id);
    }

}
