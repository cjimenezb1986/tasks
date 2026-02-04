package com.devtiro.tasks.services.impl;

import com.devtiro.tasks.domain.entities.TaskList;
import com.devtiro.tasks.repositories.TaskListRepositories;
import com.devtiro.tasks.services.TaskListService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepositories taskListRepositories;

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepositories.findAll();
    }

    @Transactional
    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(null != taskList.getId()){
            throw new IllegalArgumentException("Task list already has an ID");
        }
        if(null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task list title must be present!");
        }
        return taskListRepositories.save(TaskList.builder()
                        .title(taskList.getTitle())
                        .description(taskList.getDescription())
                        .created(LocalDateTime.now())
                        .updated(LocalDateTime.now())
                .build());

    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepositories.findById(id);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
       /* if(null == taskList.getId()){
            throw new IllegalArgumentException("Task list already has an ID");
        }

        if(!Objects.equals(taskList.getId(),taskListId)){
            throw new IllegalArgumentException("Attempting to change task list ID, this is not permitted!");
        }*/

        TaskList existingTaskList = taskListRepositories.findById(taskListId)
                .orElseThrow(()-> new IllegalArgumentException("Task list not found!"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());

        return taskListRepositories.save(existingTaskList);
    }

    @Transactional
    @Override
    public void deleteTaskList(UUID taskListId) {
        taskListRepositories.deleteById(taskListId);
    }
}
