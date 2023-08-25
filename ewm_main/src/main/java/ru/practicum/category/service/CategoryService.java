package ru.practicum.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto categoryDto);

    CategoryDto update(Long categoryId, NewCategoryDto newCategoryDto);

    void delete(Long categoryId);

    CategoryDto findCategoryDtoById(Long categoryId);

    List<CategoryDto> findAll(Pageable pageable);
}
