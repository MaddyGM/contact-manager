package org.example.contactmanager.controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.example.contactmanager.dto.ContactDTO;
import org.example.contactmanager.model.Contact;
import org.example.contactmanager.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {
    private final ContactService service;
    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    public ContactController(ContactService service) {
        this.service = service;
    }

    /**
     * Returns all contacts.
     */
    @GetMapping
    public List<Contact> getAllContacts(){
        List<Contact> contacts = service.getAllContacts();
        logger.info("GET /api/contacts - Returned {} contacts", contacts.size());
        return contacts;
    }

    /**
     * Returns a contact by its ID.
     * @param id Contact ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id){
        logger.info("GET /api/contacts/{} - Request for contact ID {}", id, id);
        return service.getContactById(id)
                .map(contact -> {
                    logger.info("Contact found: id={}, firstName={}, lastName={}, email={}",
                            contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getEmail());
                    return ResponseEntity.ok(contact);
                })
                .orElseGet(() -> {
                    logger.warn("Contact with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Creates a new contact.
     * @param contactDTO Contact data transfer object
     */
    @PostMapping
    public Contact createContact(@Valid @RequestBody ContactDTO contactDTO) {
        logger.info("POST /api/contacts - Creating contact: firstName={}, lastName={}, email={}",
                contactDTO.getFirstName(), contactDTO.getLastName(), contactDTO.getEmail());
        Contact contact = new Contact();
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setEmail(contactDTO.getEmail());
        Contact savedContact = service.saveContact(contact);
        logger.info("Contact created with ID {}", savedContact.getId());
        return savedContact;
    }

    /**
     * Update contact by ID.
     * @param id
     * @param contactDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @Valid @RequestBody ContactDTO contactDTO) {
        logger.info("PUT /api/contacts/{} - Updating contact: firstName={}, lastName={}, email={}",
                id, contactDTO.getFirstName(), contactDTO.getLastName(), contactDTO.getEmail());
        return service.getContactById(id)
                .map(existingContact -> {
                    existingContact.setFirstName(contactDTO.getFirstName());
                    existingContact.setLastName(contactDTO.getLastName());
                    existingContact.setEmail(contactDTO.getEmail());
                    service.saveContact(existingContact);
                    logger.info("Contact with ID {} updated", id);
                    return ResponseEntity.ok(existingContact);
                })
                .orElseGet(() -> {
                    logger.warn("Contact with ID {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Delete contact by ID.
     * @param id Contact ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id){
        logger.info("DELETE /api/contacts/{} - Delete request for contact ID {}", id, id);
        return service.getContactById(id)
                .map(contact -> {
                    service.deleteContact(id);
                    logger.info("Contact with ID {} deleted", id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> {
                    logger.warn("Contact with ID {} not found for deletion", id);
                    return ResponseEntity.notFound().build();
                });
    }

}
