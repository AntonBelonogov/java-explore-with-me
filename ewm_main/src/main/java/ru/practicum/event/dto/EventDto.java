package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.enums.EventState;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.user.dto.UserDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    @JsonFormat
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat
    private LocalDateTime eventDate;
    private UserDto initiator;
    private LocationDto location;
    private boolean paid;
    private Long participantLimit;
    private Long confirmedRequests;
    @JsonFormat
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private EventState state;
    private String title;
    private Long views;
}
