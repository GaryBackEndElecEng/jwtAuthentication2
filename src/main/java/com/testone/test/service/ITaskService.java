package com.testone.test.service;

import com.testone.test.model.Task;

import java.util.List;

public interface ITaskService {

      public  List<Task> getTasks();
      public  Task getTask(Long id);
      public  Task postTask(Task task);
      public  Task updateTask(Task task);
      public Object deleteTask(Long taskId);

      List<Task> getReminded();
}
