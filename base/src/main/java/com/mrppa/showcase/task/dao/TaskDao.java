package com.mrppa.showcase.task.dao;

import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mrppa.showcase.HashMapConverter;
import com.mrppa.showcase.base.dao.BaseDao;
import com.mrppa.showcase.task.TaskResult;

import lombok.Data;

@Entity
@Data
public class TaskDao implements BaseDao<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String taskDefinitionId;
	private LocalDateTime queuedDate;
	private LocalDateTime executionStartDate;
	private LocalDateTime executionEndDate;
	@Enumerated
	private TaskResult taskResult;
	@Convert(converter = HashMapConverter.class)
	private Map<String, Object> inputParam;
	@Convert(converter = HashMapConverter.class)
	private Map<String, Object> outputParam;

}
