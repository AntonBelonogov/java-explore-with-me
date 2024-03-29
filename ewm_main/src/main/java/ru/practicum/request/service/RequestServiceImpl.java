package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.EventState;
import ru.practicum.enums.RequestStatus;
import ru.practicum.event.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public RequestDto save(Long userId, Long eventId) {
        final Event event = findEventById(eventId);
        final User requester = findUserById(userId);

        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ConflictException(String.format("Запрос на участие в событии с ID:%d уже создан", eventId));
        }
        if (userId.equals(event.getInitiator().getId())) {
            throw new ConflictException("Ошибка. Пользователь является инициатором события.");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException(String.format("Ошибка статуса: %s", event.getState()));
        }
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new ConflictException(String.format("Превышен лимит участников для события с ID:%d", eventId));
        }

        if (event.getParticipantLimit() != 0) {
            List<Request> requests = requestRepository.findAllByEventId(eventId);
            isParticipationLimitFull(event, requests);
        }

        Request request = new Request();
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }

        request.setEvent(event);
        request.setRequester(requester);
        request.setCreated(LocalDateTime.now());
        return requestMapper.requestToRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public RequestDto update(Long userId, Long requestId) {
        Request request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Запрос на участие ID:%d не найден",
                        requestId)));
        request.setStatus(RequestStatus.CANCELED);
        return requestMapper.requestToRequestDto(requestRepository.save(request));
    }

    @Override
    public List<RequestDto> findByUserId(Long userId) {
        findUserById(userId);
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(requestMapper::requestToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> findAllRequestsByEventId(Long userId, Long eventId) {
        findByUserId(userId);
        findEventById(eventId);
        return requestRepository.findAllByEventId(eventId).stream()
                .map(requestMapper::requestToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequestStatus(
            Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

        EventRequestStatusUpdateResult result = EventRequestStatusUpdateResult.builder()
                .confirmedRequests(new ArrayList<>())
                .rejectedRequests(new ArrayList<>())
                .build();

        Event event = findEventById(eventId);
        List<Request> requests = requestRepository.findAllByEventId(eventId);
        isParticipationLimitFull(event, requests);

        List<Request> requestListUpd = new ArrayList<>();
        for (Request request : requests) {
            if (eventRequestStatusUpdateRequest.getRequestIds().contains(request.getId())) {
                request.setStatus(eventRequestStatusUpdateRequest.getStatus());
                requestListUpd.add(request);
            }
        }
        for (Request request : requestListUpd) {
            if (request.getStatus().equals(RequestStatus.CONFIRMED)
                    || request.getStatus().equals(RequestStatus.REJECTED)
                    || request.getStatus().equals(RequestStatus.PENDING)) {
                eventRequestStatusUpdateResultConstructor(result, request);
                requestRepository.save(request);
            } else {
                throw new ConflictException(String.format("Ошибка статуса: %s", request.getStatus()));
            }
        }
        return result;
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("События с ID:%d нет в базе", eventId)));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("События с ID:%d нет в базе", userId)));
    }

    private void isParticipationLimitFull(Event event, List<Request> requests) {
        Integer confirmedRequests = 0;
        for (Request request : requests) {
            if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
                confirmedRequests += 1;
            }
        }
        if (event.getParticipantLimit() <= confirmedRequests) {
            throw new ConflictException(String.format("Превышен лимит участников события с ID:%d", event.getId()));
        }
    }

    private void eventRequestStatusUpdateResultConstructor(
            EventRequestStatusUpdateResult eventRequestStatusUpdateResult, Request request) {
        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            eventRequestStatusUpdateResult.getConfirmedRequests().add(requestMapper.requestToRequestDto(request));
        } else if (request.getStatus().equals(RequestStatus.REJECTED)) {
            eventRequestStatusUpdateResult.getRejectedRequests().add(requestMapper.requestToRequestDto(request));
        }
    }
}
