package org.example.contactmanager.repository;

import org.example.contactmanager.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    void testSaveAndFindContact() {
        Contact contact = new Contact(null, "Test", "User", "test@example.com");
        Contact saved = contactRepository.save(contact);

        assertThat(saved.getId()).isNotNull();
        assertThat(contactRepository.findById(saved.getId())).isPresent();
    }
}
