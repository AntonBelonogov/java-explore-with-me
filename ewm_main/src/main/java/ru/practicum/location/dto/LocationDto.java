package ru.practicum.location.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Float latitude;
    private Float longitude;
}
