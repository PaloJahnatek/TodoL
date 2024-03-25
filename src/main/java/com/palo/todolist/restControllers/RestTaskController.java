package com.palo.todolist.restControllers;

import com.palo.todolist.domain.Task;
import com.palo.todolist.services.api.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/task")
public class RestTaskController {


    private final TaskService taskService;

    @Autowired
    public RestTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // do PostMappingu bude vstupovať celý objekt RequestBody Task
    @PostMapping
    public ResponseEntity addTask(@RequestBody Task task) {
        Integer id = taskService.add(task); // keď si vytvoríme ten task tak sa nám vytvorí id a to si uchováme v (task)

        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED); // id bolo vytovorené
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // keď id nebolo vytvorené
        }
    }

    @GetMapping   // znamená že to bude metóda get
    public ResponseEntity getAll() {
        List<Task> tasks = taskService.getTasks();

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/usertask/{userId}")
    public ResponseEntity getAllTasksWithUserId(@PathVariable("userId") int userId) { // userId z url sa prenesie do int userId
        List<Task> tasks = taskService.getTaskByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getTask(@PathVariable("id") int id) { // PathVariable sa tam píše preto že tam bde vstupovať to id
        Task task = taskService.get(id);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteTask(@PathVariable("id") int id) {
        if (taskService.get(id) != null) {
            taskService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Task with id : " + id + "doesn't exist");
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity updateTask(@PathVariable("id") int id, @RequestBody Task task) { // cez RequestBody tam posielame ten upravený task
        if (taskService.get(id) != null) { //  skontorlujem si či dané id nie je null
            task.setId(id);  // ak existuje tak sa to id nasetuje do toho task.setId(id) aby sa mi automaticky nevytvoril nejaký nový task s idéčkom
            taskService.update(id, task); // týmto sa updatne ten task, je to ako keby som vytvoril nový task s tým istým idečkom
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Task with id : " + id + "doesn't exist");
        }
    }
}
