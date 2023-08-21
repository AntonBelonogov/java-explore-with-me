package ru.practicum.request.dto;

import lombok.*;
import ru.practicum.enums.RequestStatus;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {
    @NotEmpty
    private List<Long> requestIds;
    @NotEmpty
    private RequestStatus status;
}
