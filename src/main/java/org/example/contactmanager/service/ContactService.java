package org.example.contactmanager.service;

import org.example.contactmanager.model.Contact;
import org.example.contactmanager.model.Position;
import org.example.contactmanager.repository.ContactRepository;
import org.example.contactmanager.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    private final ContactRepository repository;
    private final PositionRepository positionRepository;
    private final Logger logger = LoggerFactory.getLogger(ContactService.class);

    public ContactService(ContactRepository repository, PositionRepository positionRepository) {
        this.repository = repository;
        this.positionRepository = positionRepository;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = repository.findAll();
        logger.info("Fetched {} contacts", contacts.size());
        return contacts;
    }

    public Optional<Contact> getContactById(Long id) {
        logger.info("Fetching contact with ID {}", id);
        Optional<Contact> contactOpt = repository.findById(id);
        contactOpt.ifPresent(contact -> logger.info("Contact found: id={}, firstName={}, lastName={}, email={}",
                contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getEmail()));
        return contactOpt;
    }

    public Contact saveContact(Contact contact) {
        Contact savedContact = repository.save(contact);
        logger.info("Contact saved: id={}, firstName={}, lastName={}, email={}",
                savedContact.getId(), savedContact.getFirstName(), savedContact.getLastName(), savedContact.getEmail());
        return savedContact;
    }

    //TODO: con la yuda de la ID del usuario?
    public Position getPositionById(Long id) {
        logger.info("Fetching position with ID {}", id);
        return positionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Position not found"));
    }

    public void deleteContact(Long id) {
        logger.info("Attempting to delete contact with ID {}", id);
        Contact contact = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found for deletion"));
        repository.deleteById(id);
        logger.info("Contact deleted: id={}, email={}", id, contact.getEmail());
    }
}
