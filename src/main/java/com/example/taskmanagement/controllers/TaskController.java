package com.example.taskmanagement.controllers;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        return taskService.updateTask(taskId, updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    // Other endpoints as needed...

    @PostMapping("/{taskId}/assign/{userId}")
    public Task assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.assignTaskToUser(taskId, userId);
    }

    @DeleteMapping("/{taskId}/unassign")
    public Task unassignTask(@PathVariable Long taskId) {
        return taskService.unassignTask(taskId);
    }

    @PutMapping("/{taskId}/priority/{newPriority}")
    public Task updateTaskPriority(@PathVariable Long taskId, @PathVariable int newPriority) {
        return taskService.updateTaskPriority(taskId, newPriority);
    }

    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable int priority) {
        return taskService.getTasksByPriority(priority);
    }

    @GetMapping("/assigned")
    public List<Task> getTasksWithAssignedUsers() {
        return taskService.getTasksWithAssignedUsers();
    }

}
