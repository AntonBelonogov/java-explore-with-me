package ru.practicum.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    /**
     * Создание/сохранение подборки событий
     *
     * @param newCompilationDto экземпляр подборки
     * @return сохраненный экземпляр подборки
     */
    CompilationDto save(NewCompilationDto newCompilationDto);

    /**
     * Обновление данных подборки событий по ее id
     *
     * @param compId                   - идентификатор подборки
     * @param updateCompilationRequest - данные, которые необходимо обновить в подборке событий
     * @return экземпляр обновленной подборки событий
     */
    CompilationDto update(Long compId, UpdateCompilationRequest updateCompilationRequest);

    /**
     * Удаление подборки событий по ее id
     *
     * @param compId - идентификатор подборки
     */
    void delete(Long compId);

    /**
     * Поиск подборки событий по ее id
     *
     * @param compId - идентфиикатор подборки
     * @return найденный экземпляр подборки событий
     */
    CompilationDto findCompilationDtoById(Long compId);

    /**
     * Поиск всех подборок событий по закреплению подборки на главн. странице и параметрам пагинации
     *
     * @param pinned   - закреплена ли подборка на главной странице (true/false)
     * @param pageable - данные пагинации
     * @return список экземпляров подборок событий по заданным параметрам
     */
    List<CompilationDto> findAll(Boolean pinned, Pageable pageable);
}
