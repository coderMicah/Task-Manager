package com.micah.tasks.mappers;

import com.micah.tasks.domain.dto.TaskDto;
import com.micah.tasks.domain.entities.Task;

public interface TaskMapper {
    
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
