package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    String LOG_CAT = TaskService.class.getSimpleName();
    private final TaskRepository taskRepository;
    private final UserRepository userRepository; // Autowire UserRepository

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public Task createTask(Task task) {
        // Additional logic, if needed, before saving to the repository
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        // Additional logic, if needed, before updating in the repository
        Task existingTask = taskRepository.findById(taskId).orElse(null);
        if (existingTask != null) {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setPriority(updatedTask.getPriority());
            // Update other fields as needed
            return taskRepository.save(existingTask);
        }
        return null;
    }

    public void deleteTask(Long taskId) {
        // Additional logic, if needed, before deleting from the repository
        taskRepository.deleteById(taskId);
    }

    // Other service methods as needed

    public Task updateTaskPriority(Long taskId, int newPriority) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task != null) {
            task.setPriority(newPriority);
            return taskRepository.save(task);
        }

        return null;
    }

    public List<Task> getTasksByPriority(int priority) {
        return taskRepository.findByPriority(priority);
    }

    public Task assignTaskToUser(Long taskId, Long userId) {
        try {
            Task task = taskRepository.findById(taskId).orElse(null);
            User user = userRepository.findById(userId).orElse(null);

            if (task != null && user != null) {
                task.setAssignedUser(user);
                return taskRepository.save(task);
            }
            else {
                // Log information about the IDs or entities not found
                System.out.println("Task or user not found with IDs: " + taskId + ", " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
        }

        return null;
    }

    public Task unassignTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task != null) {
            task.setAssignedUser(null);
            return taskRepository.save(task);
        }

        return null;
    }

    public List<Task> getTasksWithAssignedUsers() {
        // Fetch all tasks from the repository
        List<Task> allTasks = taskRepository.findAll();

        // Filter tasks to include only those with assigned users
        return allTasks.stream()
                .filter(task -> task.getAssignedUser() != null)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void clearTaskCache() {
        // This method will clear the "tasks" cache.
        // Use it when tasks are added, updated, or deleted.
    }
}
