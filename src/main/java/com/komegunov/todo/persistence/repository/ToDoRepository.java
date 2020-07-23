package com.komegunov.todo.persistence.repository;

import com.komegunov.todo.persistence.entity.ToDo;
import com.komegunov.todo.repr.TodoRepr;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

    @Query("select new com.komegunov.todo.repr.TodoRepr(t.id, t.description, t.user.username, t.targetDate)" +
            "from ToDo t where t.user.id = :userId")
    List<TodoRepr> findToDoByUserId(@Param("userId") Long userId);
}
