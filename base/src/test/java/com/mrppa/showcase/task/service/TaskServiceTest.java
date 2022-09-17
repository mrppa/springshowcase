package com.mrppa.showcase.task.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.mrppa.showcase.CustomServiceException;
import com.mrppa.showcase.ErrorCodes;
import com.mrppa.showcase.task.TaskContext;
import com.mrppa.showcase.task.TaskResult;
import com.mrppa.showcase.task.model.Task;

@SpringBootTest
class TaskServiceTest {
	@TestConfiguration
	static class TaskServiceTestConfig {
		@Bean
		TaskDefinition taskDef1() {
			return new TaskDefinition() {
				@Override
				public String getTaskDefinitionId() {
					return "DEF1";
				}

				@Override
				public void executeTask(TaskContext taskContext) {
					System.out.println("TASK DEF1 RUNNING");
					String param1 = (String) taskContext.getInputParam().getOrDefault("param1", "");
					taskContext.getOutputParam().put("res1", param1 + "_processed");
					taskContext.getOutputParam().put(OUTPUT_PARAM_RESULT, TaskResult.SUCCESS);
				}
			};
		}
	}

	@Autowired
	TaskService taskService;

	@Test
	void whenInvalidDefinition_ThrowErrror() {
		CustomServiceException exception = assertThrows(CustomServiceException.class, () -> {
			taskService.runTask(null, "INVALID_DEF", new HashMap<>(), new HashMap<>());
		});
		assertEquals(ErrorCodes.ITEM_NOT_FOUND, exception.getErrorCode());
	}

	@Test
	void whenInvalidTaskId_ThrowErrror() {
		CustomServiceException exception = assertThrows(CustomServiceException.class, () -> {
			taskService.runTask(25l, "DEF1", new HashMap<>(), new HashMap<>());
		});
		assertEquals(ErrorCodes.ITEM_NOT_FOUND, exception.getErrorCode());
	}

//	@Test
	void whenNoTask_createNewTask() throws CustomServiceException {
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("param1", "hello");
		Map<String, Object> outputMap = new HashMap<>();

		Task task = taskService.runTask(null, "DEF1", inputMap, outputMap);

		assertEquals(TaskResult.SUCCESS, outputMap.get(TaskDefinition.OUTPUT_PARAM_RESULT));
		assertEquals("hello_processed", outputMap.get("res1"));
		assertNotNull(task);
		assertNotNull(task.getId());
	}

	@Test
	void whenTaskPassed_updateTheTask() throws CustomServiceException {
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("param1", "hello");
		Map<String, Object> outputMap = new HashMap<>();
		Task task = new Task();
		task.setTaskDefinitionId("X");
		task.setTaskResult(TaskResult.FAIL);
		task.setQueuedDate(null);
		task.setExecutionEndDate(null);
		task.setExecutionStartDate(null);
		task.setInputParam(null);
		task.setOutputParam(null);
		task = taskService.create(task);

		Task taskUpdated = taskService.runTask(task.getId(), "DEF1", inputMap, outputMap);

		assertEquals(TaskResult.SUCCESS, outputMap.get(TaskDefinition.OUTPUT_PARAM_RESULT));
		assertEquals("hello_processed", outputMap.get("res1"));
		assertNotNull(taskUpdated);
		assertNotNull(taskUpdated.getId());
		System.out.println(taskUpdated);
		
		assertEquals(task.getId(), taskUpdated.getId());
		assertEquals(task.getQueuedDate(), taskUpdated.getQueuedDate());
		
		assertNotEquals(task.getTaskDefinitionId(), taskUpdated.getTaskDefinitionId());
		assertNotEquals(task.getTaskResult(), taskUpdated.getTaskResult());
		assertNotEquals(task.getExecutionEndDate(), taskUpdated.getExecutionEndDate());
		assertNotEquals(task.getExecutionStartDate(), taskUpdated.getExecutionStartDate());
		assertNotEquals(task.getInputParam(), taskUpdated.getInputParam());
		assertNotEquals(task.getOutputParam(), taskUpdated.getOutputParam());

	}

}
