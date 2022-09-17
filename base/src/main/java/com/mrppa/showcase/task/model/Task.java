package com.mrppa.showcase.task.model;

import java.time.LocalDateTime;
import java.util.Map;

import com.mrppa.showcase.base.model.BaseModel;
import com.mrppa.showcase.task.TaskResult;

import lombok.Data;

@Data
public class Task implements BaseModel<Long> {
	private Long id;
	private String taskDefinitionId;
	private LocalDateTime queuedDate;
	private LocalDateTime executionStartDate;
	private LocalDateTime executionEndDate;
	private TaskResult taskResult;
	private Map<String, Object> inputParam;
	private Map<String, Object> outputParam;
}
