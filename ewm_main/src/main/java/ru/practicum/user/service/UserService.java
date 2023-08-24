package ru.practicum.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto);

    void delete(Long userId);

    List<UserDto> findAll(List<Long> usersIds, Pageable pageable);
}
