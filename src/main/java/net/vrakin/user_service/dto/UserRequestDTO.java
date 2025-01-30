package net.vrakin.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequestDTO {
    @Email
    private String email;

    @NotBlank
    private String name;

    private String phone;

    private Boolean activeStatus;

    private LocalDate startDate;

    private LocalDate birthDate;

    private String description;
}
