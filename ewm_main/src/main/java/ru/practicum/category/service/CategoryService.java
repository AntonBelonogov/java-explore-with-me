package ru.practicum.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    /**
     * Создание/сохранение новой категории
     *
     * @param categoryDto экземпляр категории
     * @return сохраненный экземпляр категории
     */
    CategoryDto save(CategoryDto categoryDto);

    /**
     * Обновить данные категории по ее id
     *
     * @param categoryId     - идентификатор обновляемой категории
     * @param newCategoryDto - экземпляр категории, содержащий изменения
     * @return экземпляр обновленной категории
     */
    CategoryDto update(Long categoryId, NewCategoryDto newCategoryDto);

    /**
     * Удаление категории по ее id
     *
     * @param categoryId - идентификатор категории
     */
    void delete(Long categoryId);

    /**
     * Поиск категории по ее id
     *
     * @param categoryId - идентификатор категории
     * @return экземпляр категории по заданному id
     */
    CategoryDto findCategoryDtoById(Long categoryId);

    /**
     * Поиск всех категорий по заданным параметрам пагинации
     *
     * @param pageable - параметры пагинации
     * @return список экземпляров категорий
     */
    List<CategoryDto> findAll(Pageable pageable);
}
