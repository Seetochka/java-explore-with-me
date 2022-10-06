package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.UserDto;
import ru.practicum.ewmservice.mapper.UserMapper;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.service.UserService;

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
    public HttpStatus delete(@PathVariable long userId) {
        userService.delete(userId);
        return HttpStatus.OK;
    }
}
