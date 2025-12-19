package com.micah.tasks.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micah.tasks.domain.dto.TaskDto;
import com.micah.tasks.domain.entities.Task;
import com.micah.tasks.mappers.TaskMapper;
import com.micah.tasks.services.TaskService;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TaskController {
    
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService,TaskMapper taskMapper){
        this.taskMapper = taskMapper;
        this.taskService = taskService;
    }


    @GetMapping
    public List<TaskDto> getTasks(@PathVariable("task_list_id") UUID taskListId){
        return taskService.listTasks(taskListId)
                            .stream()
                            .map(taskMapper::toDto)
                            .toList();
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId,@RequestBody TaskDto taskDto){
       Task createdTask = taskService.createTask(taskListId, taskMapper.fromDto(taskDto));
       return taskMapper.toDto(createdTask);
    }

     @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTaskById(@PathVariable("task_list_id") UUID taskListId,@PathVariable("task_id") UUID taskId){
        return taskService.getTaskById(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping(path = "/{task_id}")
      public TaskDto updateTask(@PathVariable("task_list_id") UUID taskListId,@PathVariable("task_id") UUID taskId,@RequestBody TaskDto taskdto){
        Task updatedTask = taskService.updateTask(taskListId, taskId,taskMapper.fromDto(taskdto));
        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "/{task_id}")
    public void deleteTaskById(@PathVariable("task_list_id") UUID taskListId,@PathVariable("task_id") UUID taskId){
          taskService.deleteTask(taskListId, taskId);
    }
}
