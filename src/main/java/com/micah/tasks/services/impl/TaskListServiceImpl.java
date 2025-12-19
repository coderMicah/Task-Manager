package com.micah.tasks.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micah.tasks.domain.entities.TaskList;
import com.micah.tasks.repositories.TaskListRepository;
import com.micah.tasks.services.TaskListService;



@Service
class TaskListServiceImpl implements TaskListService{

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository){
        this.taskListRepository = taskListRepository;
    }


    @Override
    public List<TaskList> listTaskLists() {
       return taskListRepository.findAll();
    }



    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(taskList.getId() != null){
            throw new IllegalArgumentException("Tasklist already has an id");
        }if(taskList.getTitle() == null || taskList.getTitle().isBlank()){
           throw new IllegalArgumentException("Tasklist title must be present");
        }

        return taskListRepository.save(
            new TaskList(null, taskList.getTitle(), taskList.getDescription(), null)
        );
       
    }


    @Override
    public Optional<TaskList> getTaskList(UUID id) {
      return taskListRepository.findById(id);
    }


    @Transactional
    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {

        //  if(taskList.getId() == null){
        //     throw new IllegalArgumentException("Tasklist must have id");
        // }if(!Objects.equals(taskList.getId(), id)){
        //    throw new IllegalArgumentException("Not permitted to change this tasklist");
        // }

        TaskList existingTaskList = taskListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task List Not Found"));
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setTitle(taskList.getTitle());

       
        return taskListRepository.save(existingTaskList);
        
        
    }


    @Override
    public void deleteTaskList(UUID id) {
      taskListRepository.deleteById(id);
    }
    
 }