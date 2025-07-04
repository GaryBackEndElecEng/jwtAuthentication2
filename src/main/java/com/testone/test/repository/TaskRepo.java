package com.testone.test.repository;

import com.testone.test.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long> {
 public Task findByText(String str);
}
