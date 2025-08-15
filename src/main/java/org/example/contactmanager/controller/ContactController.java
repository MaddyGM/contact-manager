package org.example.contactmanager.controller;

import org.apache.coyote.Response;
import org.example.contactmanager.model.Contact;
import org.example.contactmanager.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contact> getAllContacts(){
        return service.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id){
        return service.getContactById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contact createContact(@RequestBody Contact contact){
        return service.saveContact(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) {
        return service.getContactById(id)
                .map(contact -> {
                    contact.setFirstName(updatedContact.getFirstName());
                    contact.setLastName(updatedContact.getLastName());
                    contact.setEmail(updatedContact.getEmail());
                    service.saveContact(contact);
                    return ResponseEntity.ok(contact);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id){
        return service.getContactById(id)
                .map(contact -> {
                    service.deleteContact(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
