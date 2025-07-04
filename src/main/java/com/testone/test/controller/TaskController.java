package com.testone.test.controller;

import com.testone.test.dto.TaskDto;
import com.testone.test.exception.ResourceNotFound;
import com.testone.test.model.Task;
import com.testone.test.response.ApiResponse;
import com.testone.test.service.ITaskService;
import com.testone.test.service.TaskToDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private final ITaskService taskService;
    private final TaskToDto dtoService;

    @GetMapping("all")
    public ResponseEntity<ApiResponse> getTask(){
        try {
            List<Task> tasks=taskService.getTasks();
            List<TaskDto> tasksToDtos=dtoService.convertToListDto(tasks);
            return ResponseEntity.ok().body(new ApiResponse(tasksToDtos,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null,e.getMessage()));
        }
    };

    @PostMapping("/post")
    public ResponseEntity<ApiResponse> postTask(@RequestBody Task task){
        try {
            Task _task=taskService.postTask(task);
            TaskDto _taskDto=dtoService.convertDto(_task);
            System.out.println("INCOMMING:" + _task + " OUTGOING: "+ _taskDto);
            return ResponseEntity.ok().body(new ApiResponse(_taskDto,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null,e.getMessage()));
        }
    };

    @GetMapping("/post/{taskId}")
    public ResponseEntity<ApiResponse> getTask(@PathVariable Long taskId){
        try {
            Task task=taskService.getTask(taskId);
            TaskDto _taskDto=dtoService.convertDto(task);
            return ResponseEntity.ok().body(new ApiResponse(_taskDto,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null,e.getMessage()));
        }
    };

    @PutMapping("/post")
    public ResponseEntity<ApiResponse> updateTask(@RequestBody Task task){
        try {
            Task _task=taskService.updateTask(task);
            TaskDto _taskDto=dtoService.convertDto(_task);
            return ResponseEntity.ok().body(new ApiResponse(_taskDto,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null,e.getMessage()));
        }
    };
    @DeleteMapping("post/delete/{taskId}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long taskId){
        try {
            Object obj=taskService.deleteTask(taskId);
            return ResponseEntity.ok().body(new ApiResponse(null,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(null,e.getMessage()));
        }
    };

    @GetMapping("/reminded")
    public ResponseEntity<ApiResponse> getReminded(){
        try {
            List<Task> tasks=taskService.getReminded();
            List<TaskDto> taskDtos=dtoService.convertToListDto(tasks);
            return ResponseEntity.ok().body(new ApiResponse(taskDtos,"success"));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(null,e.getMessage()));
        }
    }

    //end
};
