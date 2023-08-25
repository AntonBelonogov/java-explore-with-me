package ru.practicum.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    CompilationDto save(NewCompilationDto newCompilationDto);

    CompilationDto update(Long compId, UpdateCompilationRequest updateCompilationRequest);

    void delete(Long compId);

    CompilationDto findCompilationDtoById(Long compId);

    List<CompilationDto> findAll(Boolean pinned, Pageable pageable);
}
