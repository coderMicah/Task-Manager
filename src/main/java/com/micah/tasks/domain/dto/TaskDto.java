package com.micah.tasks.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.micah.tasks.domain.entities.TaskPriority;
import com.micah.tasks.domain.entities.TaskStatus;

public record TaskDto(
    UUID id,
    String title,
    String description,
    LocalDateTime dueDate,
    TaskStatus status,
    TaskPriority taskPriority
    //  UUID taskListId

) {
    
}
