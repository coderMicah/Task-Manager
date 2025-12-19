package com.micah.tasks.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.micah.tasks.domain.entities.Task;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId,Task task);
    Optional<Task> getTaskById(UUID taskListId,UUID taskId);
    Task updateTask(UUID taskListId,UUID taskId,Task task);
    void deleteTask(UUID taskListId,UUID taskId);
}
