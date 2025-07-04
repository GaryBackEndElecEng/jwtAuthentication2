package com.testone.test.service;

import com.testone.test.dto.TaskDto;
import com.testone.test.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Getter
@Setter
public class TaskToDto {


    private final TaskDto taskDto;
    private final String  pattern = "MM/dd/yyyy HH:mm:ss";

    public TaskToDto(){
        this.taskDto=new TaskDto();
    }


    public TaskDto convertDto(Task task){

        taskDto.setTaskId(task.getTaskId());
        System.out.println("CONVERTING DATE:"
                + "ID:" + task.getTaskId()
        + "text:" + task.getText()
        + "date: " + task.getDate());
        taskDto.setDate(this.convertDate(task.getDate()));
        taskDto.setText(task.getText());
        taskDto.setReminder(task.getReminder());
        return taskDto;
    }

    public String convertDate(Date date){
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);

    }

    public List<TaskDto> convertToListDto(List<Task> tasks){
        List<TaskDto> taskDtos=new ArrayList<>();
        tasks.forEach(task->{
        TaskDto _taskDto=new TaskDto();
            _taskDto.setTaskId(task.getTaskId());
            _taskDto.setDate(this.convertDate(task.getDate()));
            _taskDto.setText(task.getText());
            _taskDto.setReminder(task.getReminder());

            taskDtos.add(_taskDto);
        });
        return taskDtos;
    }



}
