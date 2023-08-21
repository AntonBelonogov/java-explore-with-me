package ru.practicum.request.service;

import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

public interface RequestService {

    RequestDto save(Long userId, Long eventId);

    RequestDto update(Long userId, Long requestId);

    List<RequestDto> findByUserId(Long userId);

    List<RequestDto> findAllRequestsByEventId(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId,
                                                       EventRequestStatusUpdateRequest requestStatusUpdateDto);
}
