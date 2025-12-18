package com.micah.tasks.domain.dto;

 
import java.util.List;
import java.util.UUID;


public record TaskListDto(
      UUID id,
    String title,
    String description,
    Double progress,
    Integer count,
   List<TaskDto> tasks
) {
    
}
