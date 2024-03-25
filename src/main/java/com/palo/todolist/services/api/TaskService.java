package com.palo.todolist.services.api;

import com.palo.todolist.domain.Task;

import java.util.List;

public interface TaskService {
    List<Task> getTasks();
    Task get(Integer id);
    List<Task> getTaskByUserId(Integer userId);
    Integer add(Task task);
    void delete(Integer id);
    void update(Integer id, Task task);
}
