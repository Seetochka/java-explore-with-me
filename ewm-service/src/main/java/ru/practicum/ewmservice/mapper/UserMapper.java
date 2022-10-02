package ru.practicum.ewmservice.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.UserDto;
import ru.practicum.ewmservice.model.User;

/**
 * Маппер для пользователя
 */
@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(),user.getName(), user.getEmail());
    }

    public User toUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }
}
