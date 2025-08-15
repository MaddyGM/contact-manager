package org.example.contactmanager.service;

import org.example.contactmanager.model.Contact;
import org.example.contactmanager.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public List<Contact> getAllContacts(){
        return repository.findAll();
    }

    public Optional<Contact> getContactById(Long id){
        return repository.findById(id);
    }

    public Contact saveContact(Contact contact){
        return repository.save(contact);
    }

    public void deleteContact(Long id){
        repository.deleteById(id);
    }
}
