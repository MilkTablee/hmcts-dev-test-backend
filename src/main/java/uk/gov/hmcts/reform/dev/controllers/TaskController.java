package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskCounterRepository;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/tasks") // Base path for all task routes
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskCounterRepository taskCounterRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, TaskCounterRepository taskCounterRepository) {
        this.taskRepository = taskRepository;
        this.taskCounterRepository = taskCounterRepository;
    }

    // GET all tasks (for listing tasks)
    @GetMapping(value = {"", "/"}, produces = "application/json")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ok(taskRepository.findAll());
    }

    // GET a single task by ID (for viewing task detail)
    @GetMapping(value = "/{id}/view")
    public String viewTask(@PathVariable int id, Model model) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task currentTask = task.get();

            // Format the LocalDateTime fields
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDueDate = currentTask.getDueDate() != null ? currentTask.getDueDate().format(formatter) : "Not provided";
            String formattedCreatedDate = currentTask.getCreatedDate() != null ? currentTask.getCreatedDate().format(formatter) : "Not provided";

            // Set formatted fields into the model
            model.addAttribute("task", currentTask);
            model.addAttribute("formattedDueDate", formattedDueDate);
            model.addAttribute("formattedCreatedDate", formattedCreatedDate);

            return "view-task"; // Renders the view-task.njk template
        }
        return "redirect:/tasks"; // Redirect if task not found
    }

    /*CREATE TASK*/

    // POST (create a new task)
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setCreatedDate(LocalDateTime.now());

        // Step 1: Increment the counter
        taskCounterRepository.incrementCaseNumber();

        // Step 2: Fetch the updated counter value
        int currentValue = taskCounterRepository.getNextCaseNumber();

        // Step 3: Generate case number (e.g., CASE-1, CASE-2, etc.)
        String caseNumber = "CASE-" + currentValue;
        task.setCaseNumber(caseNumber);

        // Step 4: Save the task
        Task savedTask = taskRepository.save(task);

        return ok(savedTask);
    }

    @GetMapping(value = "/create")
    public String showCreateTaskForm() {
        return "create-task"; // Template on frontend
    }


    /*UPDATE/EDIT TASK*/

    // PUT (update an existing task)
    @PutMapping(value = "/{id}/edit", produces = "application/json")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task updatedTask = existingTask.get();
            updatedTask.setTitle(task.getTitle());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setStatus(task.getStatus());
            updatedTask.setDueDate(task.getDueDate());
            taskRepository.save(updatedTask);
            return ok(updatedTask);
        }
        return notFound().build();
    }

    // DELETE a task
    @GetMapping(value = "/{id}/delete")
    public String deleteTask(@PathVariable int id, Model model) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "delete-task"; // Renders the delete-task.njk template
        }
        return "redirect:/tasks"; // Redirect if task not found
    }

    @PostMapping(value = "/{id}/delete")
    public String confirmDeleteTask(@PathVariable int id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks"; // Redirect back to task list
    }
}
