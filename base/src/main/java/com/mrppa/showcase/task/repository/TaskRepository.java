package com.mrppa.showcase.task.repository;

import org.springframework.stereotype.Repository;

import com.mrppa.showcase.base.repository.BaseRepository;
import com.mrppa.showcase.task.dao.TaskDao;

@Repository
public interface TaskRepository extends BaseRepository<TaskDao, Long> {

}
