package net.vrakin.user_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDTO {

    private Long id;
    private String email;
    private String name;
    private String phone;
    private Boolean activeStatus;
    private LocalDate startDate;
    private LocalDate birthDate;
    private String description;
}
