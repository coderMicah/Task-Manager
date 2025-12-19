package com.micah.tasks.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micah.tasks.domain.entities.Task;
import com.micah.tasks.domain.entities.TaskList;
import com.micah.tasks.domain.entities.TaskPriority;
import com.micah.tasks.domain.entities.TaskStatus;
import com.micah.tasks.repositories.TaskListRepository;
import com.micah.tasks.repositories.TaskRepository;
import com.micah.tasks.services.TaskService;




@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository,TaskListRepository taskListRepository){
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {

        if(task.getId() != null){ throw new IllegalArgumentException("Task already has an id");}
        if(task.getTitle() == null || task.getTitle().isBlank()){ throw new IllegalArgumentException("Task must have a title");}
       
       TaskPriority taskPriority =  Optional.ofNullable(task.getTaskPriority()).orElse(TaskPriority.LOW);
       TaskStatus taskStatus = TaskStatus.OPEN;

       TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(() -> new IllegalArgumentException("Invalid Task List ID provided!"));
       Task taskToSave  = new Task(
          null,
          task.getTitle(),
          task.getDescription(),
          taskStatus,taskPriority,
          task.getDueDate(),
          taskList
       );

     return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTaskById(UUID taskListId, UUID taskId) {
       return taskRepository.findByTaskListIdAndId(taskListId,taskId);
    }


    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        //   if(task.getId() == null){ throw new IllegalArgumentException("Task must have an id");}
        //   if(Objects.equals(taskId, task.getId())){throw new IllegalArgumentException("Task IDs do not match");}
          if(null == task.getTaskPriority()){throw new IllegalArgumentException("Task must have valid priority");}
          if(null == task.getStatus()){throw new IllegalArgumentException("Task must have valid priority");}

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId,taskId).orElseThrow(() -> new IllegalArgumentException("Task not Found"));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setTaskPriority(task.getTaskPriority());

       return taskRepository.save(existingTask);

    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
    
}