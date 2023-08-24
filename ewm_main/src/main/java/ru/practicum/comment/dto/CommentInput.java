package ru.practicum.comment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentInput {
    @NotBlank
    @Size(min = 32, max = 1024)
    private String text;
}
