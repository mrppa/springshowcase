package com.mrppa.showcase.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mrppa.showcase.task.TaskContext;
import com.mrppa.showcase.task.TaskResult;

class TaskDefinitionTest {

	@Test
	void whenNoResult_TaskShouldFail() {
		TaskDefinition taskDefinition = new TaskDefinition() {

			@Override
			public void executeTask(TaskContext taskContext) {

			}

			@Override
			public String getTaskDefinitionId() {
				return "Test";
			}
		};

		TaskContext taskContext = new TaskContext();
		taskDefinition.execute(taskContext);
		assertEquals(TaskResult.FAIL, taskContext.getOutputParam().get(TaskDefinition.OUTPUT_PARAM_RESULT));
	}

	@Test
	void whenException_TaskShouldFail() {
		TaskDefinition taskDefinition = new TaskDefinition() {

			@Override
			public void executeTask(TaskContext taskContext) {
				taskContext.getOutputParam().put(OUTPUT_PARAM_RESULT, TaskResult.SUCCESS);
				throw new RuntimeException("Unidentified exception");
			}

			@Override
			public String getTaskDefinitionId() {
				return "Test";
			}
		};

		TaskContext taskContext = new TaskContext();
		taskDefinition.execute(taskContext);
		assertEquals(TaskResult.FAIL, taskContext.getOutputParam().get(TaskDefinition.OUTPUT_PARAM_RESULT));
		assertEquals("Unidentified exception", taskContext.getOutputParam().get(TaskDefinition.OUTPUT_PARAM_EXCEPTION));
	}

	@Test
	void whenSuccess_PassSuccess() {
		TaskDefinition taskDefinition = new TaskDefinition() {

			@Override
			public void executeTask(TaskContext taskContext) {
				taskContext.getOutputParam().put(OUTPUT_PARAM_RESULT, TaskResult.SUCCESS);
			}

			@Override
			public String getTaskDefinitionId() {
				return "Test";
			}
		};

		TaskContext taskContext = new TaskContext();
		taskDefinition.execute(taskContext);
		assertEquals(TaskResult.SUCCESS, taskContext.getOutputParam().get(TaskDefinition.OUTPUT_PARAM_RESULT));
	}

}
