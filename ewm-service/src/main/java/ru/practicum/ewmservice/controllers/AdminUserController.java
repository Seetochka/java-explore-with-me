package ru.practicum.ewmservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.UserDto;
import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.mappers.UserMapper;
import ru.practicum.ewmservice.models.User;
import ru.practicum.ewmservice.services.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер админки отвечающий за действия с пользователем
 */
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        User user = userService.create(userMapper.toUser(userDto));

        return userMapper.toUserDto(user);
    }

    @GetMapping
    public Collection<UserDto> getByIds(@RequestParam Collection<Long> ids,
                            @RequestParam(defaultValue = "0") int from,
                            @RequestParam(defaultValue = "10") int size) {
        return userService.getByIds(ids, from, size)
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) throws ObjectNotFountException {
        userService.delete(userId);
    }
}
