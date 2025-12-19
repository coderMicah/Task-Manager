package com.micah.tasks.mappers.impl;


import org.springframework.stereotype.Component;

import com.micah.tasks.domain.dto.TaskDto;
import com.micah.tasks.domain.entities.Task;
import com.micah.tasks.mappers.TaskMapper;


@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task fromDto(TaskDto taskDto) {
      

    return new Task(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.status(),
                taskDto.taskPriority(),
                taskDto.dueDate(), null
                // taskDto.taskListId() 
        );
    }

    @Override
    public TaskDto toDto(Task task) {
      return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getTaskPriority(),
                task.getTaskList() != null ? task.getTaskList().getId() : null
        );
    }
    
}
