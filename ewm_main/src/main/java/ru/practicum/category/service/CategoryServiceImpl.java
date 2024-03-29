package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto save(CategoryDto categoryDto) {
        log.info(String.format("Сохранение категории:%s", categoryDto.getName()));

        Category category = categoryMapper.categoryDtoToCategory(categoryDto);
        return categoryMapper.categoryToCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto update(Long categoryId, NewCategoryDto newCategoryDto) {
        log.info(String.format("Обновление категории с ID:%d", categoryId));

        Category categoryForUpd = findById(categoryId);
        categoryMapper.updateCategory(categoryForUpd, newCategoryDto);
        return categoryMapper.categoryToCategoryDto(categoryRepository.save(categoryForUpd));
    }

    @Override
    @Transactional
    public void delete(Long categoryId) {
        log.info(String.format("Удаление категории с ID:%d", categoryId));

        findById(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryDto findCategoryDtoById(Long categoryId) {
        log.info(String.format("Поиск категории с ID:%d", categoryId));

        return categoryMapper.categoryToCategoryDto(findById(categoryId));
    }

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        log.info("Поиск всех категорий.");

        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    private Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException(String.format("Категории с ID:%d нет в базе", categoryId)));
    }
}
