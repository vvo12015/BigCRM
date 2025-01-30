package net.vrakin.user_service.mapper;

import net.vrakin.user_service.dto.UserRequestDTO;
import net.vrakin.user_service.dto.UserResponseDTO;
import net.vrakin.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Мапінг з UserRequestDTO до User (створення користувача).
     *
     * @param userRequestDTO DTO із вхідними даними.
     * @return Об'єкт User.
     */
    User toEntity(UserRequestDTO userRequestDTO);

    /**
     * Мапінг з User до UserResponseDTO (відповідь API).
     *
     * @param user Об'єкт User.
     * @return Об'єкт UserResponseDTO.
     */
    UserResponseDTO toResponseDTO(User user);

    /**
     * Мапінг списку User до списку UserResponseDTO.
     *
     * @param users Список об'єктів User.
     * @return Список UserResponseDTO.
     */
    List<UserResponseDTO> toResponseDTOList(List<User> users);
}
