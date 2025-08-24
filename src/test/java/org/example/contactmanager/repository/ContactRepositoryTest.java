package org.example.contactmanager.repository;

import org.example.contactmanager.model.Contact;
import org.example.contactmanager.model.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Test
    void testSaveAndFindContactWithPosition() {
        Position developer = new Position();
        developer.setName("Developer");
        Position savedPosition = positionRepository.save(developer);

        Contact contact = new Contact("Test", "User", "test@example.com");
        contact.setPosition(savedPosition);
        Contact savedContact = contactRepository.save(contact);

        assertThat(savedContact.getId()).isNotNull();
        assertThat(savedContact.getPosition()).isNotNull();
        assertThat(savedContact.getPosition().getName()).isEqualTo("Developer");

        assertThat(contactRepository.findById(savedContact.getId())).isPresent()
                .get()
                .extracting(Contact::getPosition)
                .isEqualTo(savedPosition);
    }
}
