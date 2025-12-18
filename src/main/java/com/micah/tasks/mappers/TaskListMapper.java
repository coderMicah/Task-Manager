package com.micah.tasks.mappers;

import com.micah.tasks.domain.dto.TaskListDto;
import com.micah.tasks.domain.entities.TaskList;

public interface TaskListMapper {
    
     TaskList fromDto(TaskListDto taskListDto);
     TaskListDto toDto(TaskList taskList);
}
