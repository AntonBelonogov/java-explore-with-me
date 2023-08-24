package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final EventMapper eventMapper;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public CompilationDto save(NewCompilationDto newCompilationDto) {
        Set<Event> events = new HashSet<>();
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
        }

        Set<EventShortDto> eventShortDtoList = new HashSet<>();
        if (!events.isEmpty()) {
            eventShortDtoList = events.stream()
                    .map(eventMapper::eventToEventShortDto)
                    .collect(Collectors.toSet());
        }

        Compilation compilation = Compilation.builder()
                .events(events)
                .pinned(newCompilationDto.getPinned() != null ? newCompilationDto.getPinned() : false)
                .title(newCompilationDto.getTitle())
                .build();
        compilation = compilationRepository.save(compilation);

        return CompilationDto.builder()
                .id(compilation.getId())
                .events(eventShortDtoList)
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }

    @Override
    @Transactional
    public CompilationDto update(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = findById(compId);
        if (updateCompilationRequest.getTitle() != null && !(updateCompilationRequest.getTitle().isBlank())) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (updateCompilationRequest.getEvents() != null) {
            compilation.setEvents(eventRepository.findAllByIdIn(updateCompilationRequest.getEvents()));
        }
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        return compilationMapper.compilationToCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        findById(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto findCompilationDtoById(Long compId) {
        Compilation compilation = findById(compId);
        return compilationMapper.compilationToCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> findAll(Boolean pinned, Pageable pageable) {
        List<Compilation> compilations;
        if (pinned != null) {
            compilations = compilationRepository.findAllByPinned(pinned, pageable);
        } else {
            compilations = compilationRepository.findAll(pageable).toList();
        }

        return compilations.stream()
                .map(compilationMapper::compilationToCompilationDto)
                .collect(Collectors.toList());
    }

    private Compilation findById(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException(String.format("Подборки событий с ID:%d нет в базе", compId)));
    }
}
