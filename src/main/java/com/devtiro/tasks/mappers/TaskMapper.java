package com.devtiro.tasks.mappers;

import com.devtiro.tasks.domain.entities.Task;
import com.devtiro.tasks.dto.TaskDto;

import java.util.Optional;
import java.util.UUID;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);

}
