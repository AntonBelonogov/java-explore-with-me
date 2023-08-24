package ru.practicum.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.enums.EventState;
import ru.practicum.event.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    /**
     * Создание/сохранение нового события
     *
     * @param userId      - идентификатор пользователя
     * @param newEventDto - экземпляр нового события
     * @return экземпляр сохраненного события
     */
    EventDto save(Long userId, NewEventDto newEventDto);

    /**
     * Обновление данных события, добавленного пользователем
     *
     * @param userId                 - идентификатор пользователя
     * @param eventId                - идентификатор события
     * @param updateEventUserRequest - данные для обновления
     * @return экземпляр обновленного события
     */
    EventFullDto updateForUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    /**
     * Получить информацию о событии, добавленном пользователем
     *
     * @param userId  - идентификатор пользователя
     * @param eventId - идентификатор события
     * @return - данные о событии
     */
    EventDto findByIdAndUser(Long userId, Long eventId);

    /**
     * Получить события, добавленные пользователем по его id и параметрам пагинации
     *
     * @param userId   - идентификатор пользователя
     * @param pageable - параметры пагинации
     * @return список экземпляров событий
     */
    List<EventShortDto> findAllForUser(Long userId, Pageable pageable);

    /**
     * Изменение данных события и его статуса администратором
     *
     * @param eventId                 - идентификатор события
     * @param updateEventAdminRequest - данные для изменения
     * @return обновленное событие
     */
    EventFullDto updateForAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    /**
     * Поиск событий администратором по заданным параметрам(фильтрам)
     *
     * @param usersIds      - список идентификаторов пользователей, чьи события нужно найти
     * @param states        - список статусов событий
     * @param categoriesIds - список идентификаторов категорий событий
     * @param rangeStart    - минимальная дата начала
     * @param rangeEnd      - максимальная дата окончания
     * @param pageable      - параметры пагинации
     * @return список событий по заданным параметрам
     */
    List<EventFullDto> findAllForAdmin(List<Long> usersIds, List<EventState> states, List<Long> categoriesIds,
                                       LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    /**
     * Поиск события по id
     *
     * @param id      - идентификатор события
     * @param request - запрос для статистики
     * @return экземпляр события
     */
    EventDto findEventDtoById(Long id, HttpServletRequest request);

    /**
     * Поиск событий по заданным параметрам
     *
     * @param text       - текст для поиска в аннотации и описании события
     * @param categories - список идентификаторов категорий
     * @param paid       - платное или бесплатное событие (true/false)
     * @param rangeStart - минимальная дата начала
     * @param rangeEnd   - максимальная дата окончания
     * @param pageable   - параметры пагинации
     * @param request    - запрос для статистики
     * @return список событий по заданным параметрам
     */
    List<EventFullDto> findAllWithParams(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, Pageable pageable, HttpServletRequest request);
}
