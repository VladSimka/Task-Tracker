package com.vladsimonenko.tasktracker.repository;

import com.vladsimonenko.tasktracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
                SELECT * FROM tasks t
                JOIN users_tasks ut ON t.id = ut.task_id
                WHERE ut.user_id = :userId
            """, nativeQuery = true
    )
    List<Task> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = """
                INSERT INTO users_tasks (user_id, task_id)
                VALUES (:userId, :taskId)
            """, nativeQuery = true
    )
    void connectTaskToUser(@Param("taskId") Long taskId, @Param("userId") Long userId);
}
