package org.example.contactmanager.dto;

import jakarta.validation.constraints.NotNull;
import org.example.contactmanager.validation.ValidComputerCenterEmail;
import jakarta.validation.constraints.NotBlank;

public class ContactDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @ValidComputerCenterEmail
    private String email;

    @NotNull(message = "Position is required")
    private Long positionId;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getPositionId() { return positionId; }
    public void setPositionId(Long positionId) { this.positionId = positionId; }
}