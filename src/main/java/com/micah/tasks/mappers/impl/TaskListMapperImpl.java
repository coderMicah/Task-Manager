package com.micah.tasks.mappers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.micah.tasks.domain.dto.TaskDto;
import com.micah.tasks.domain.dto.TaskListDto;
import com.micah.tasks.domain.entities.Task;
import com.micah.tasks.domain.entities.TaskList;
import com.micah.tasks.domain.entities.TaskStatus;
import com.micah.tasks.mappers.TaskListMapper;
import com.micah.tasks.mappers.TaskMapper;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

            private Double calculateTaskListProgress(List<Task> tasks)
        {
            if(null == tasks){return null;}
           long closedTaskCount = tasks.stream().filter(task -> TaskStatus.CLOSED == task.getStatus()).count();
           return(double) closedTaskCount / tasks.size();

        }

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        List<Task> tasks = Optional.ofNullable(taskListDto.tasks())
                .map(list -> list.stream()
                        .map(taskMapper::fromDto)
                        .toList()) // collect to List
                .orElse(null); // default empty list if null
        return new TaskList(taskListDto.id(), taskListDto.title(), taskListDto.description(), tasks);
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {

         List<TaskDto> taskDtos = Optional.ofNullable(taskList.getTasks())
            .map(tasks -> tasks.stream()
                    .map(taskMapper::toDto)
                    .toList())
            .orElse(new ArrayList<>());

         return new TaskListDto(
            taskList.getId(),
        taskList.getTitle(), 
        taskList.getDescription(), 
        calculateTaskListProgress(taskList.getTasks()),
         Optional.ofNullable(taskList.getTasks()).map(List::size).orElse(0),
        taskDtos
        );
    }

}
