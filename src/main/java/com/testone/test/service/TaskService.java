package com.testone.test.service;

import com.testone.test.dto.TaskDto;
import com.testone.test.exception.ResourceNotFound;
import com.testone.test.model.Task;
import com.testone.test.repository.TaskRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {


    @Autowired
    private final TaskRepo taskRepo;
    @Override
    public List<Task> getTasks() {
        return taskRepo.findAll();
    }

    @Override
    public Task getTask(Long id) {
        return taskRepo.findById(id)
                .orElseThrow(()->new ResourceNotFound("NOT FOUND"));
    }

    @Override
    public Task postTask(Task task) {
        return taskRepo.save(task);
    }



    @Override
    public Task updateTask(Task task) {
        Task uptask=taskRepo.findById(task.getTaskId())
                .orElseThrow(()->new ResourceNotFound(("NOT FOUND")));
        if(uptask!=null){
            return taskRepo.save(task);
        }
        return null;
    }

    @Override
    public Object deleteTask(Long taskId) {
        Task isTask=taskRepo.findById(taskId)
                .orElse(null);
        if(isTask !=null){
            taskRepo.delete(isTask);
            return "success";
        }
        return  new ResourceNotFound(" NOT FOUND");
    }

    @Override
    public List<Task> getReminded() {
        boolean isTrue=true;
        return taskRepo.findAll().stream()
                .filter(task->(task.getReminder().equals(true))).toList();
    }


}
