package ru.practicum.category.controller.anonymous;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{catId}")
    public CategoryDto findById(@PathVariable Long catId) {
        return categoryService.findCategoryDtoById(catId);
    }

    @GetMapping
    public List<CategoryDto> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                     @Positive @RequestParam(defaultValue = "10") int size) {
        return categoryService.findAll(PageRequest.of(from / size, size));
    }
}
