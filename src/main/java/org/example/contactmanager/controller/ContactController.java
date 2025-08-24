package org.example.contactmanager.controller;

import jakarta.validation.Valid;
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
        logger.info("Request: GET /api/contacts");
        return service.getAllContacts();
    }

    /**
     * Returns a contact by its ID.
     * @param id Contact ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id){
        logger.info("Request: GET /api/contacts/{}", id);
        return service.getContactById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new contact.
     * @param contactDTO Contact data transfer object
     */
    @PostMapping
    public Contact createContact(@Valid @RequestBody ContactDTO contactDTO) {
        logger.info("Request: POST /api/contacts");
        Contact contact = new Contact();
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPosition(service.getPositionById(contactDTO.getPositionId()));
        return service.saveContact(contact);
    }

    /**
     * Update contact by ID.
     * @param id
     * @param contactDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @Valid @RequestBody ContactDTO contactDTO) {
        logger.info("Request: PUT /api/contacts/{}", id);
        return service.getContactById(id)
                .map(existingContact -> {
                    existingContact.setFirstName(contactDTO.getFirstName());
                    existingContact.setLastName(contactDTO.getLastName());
                    existingContact.setEmail(contactDTO.getEmail());
                    existingContact.setPosition(service.getPositionById(contactDTO.getPositionId()));
                    return ResponseEntity.ok(service.saveContact(existingContact));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete contact by ID.
     * @param id Contact ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id){
        logger.info("Request: DELETE /api/contacts/{}", id);
        return service.getContactById(id)
                .map(contact -> {
                    service.deleteContact(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
