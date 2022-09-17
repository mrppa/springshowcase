package com.mrppa.showcase.task.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrppa.showcase.task.TaskContext;
import com.mrppa.showcase.task.TaskResult;

public abstract class TaskDefinition {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static String OUTPUT_PARAM_RESULT = "result";
	public static String OUTPUT_PARAM_EXCEPTION = "exception";

	public abstract String getTaskDefinitionId();

	public final void execute(TaskContext taskContext) {
		logger.debug("Executing Custom Task with Context {}", taskContext);
		try {
			executeTask(taskContext);
		} catch (Exception e) {
			logger.error("Custom task execution failed", taskContext);
			taskContext.getOutputParam().put(OUTPUT_PARAM_RESULT, TaskResult.FAIL);
			taskContext.getOutputParam().put(OUTPUT_PARAM_EXCEPTION, e.getLocalizedMessage());
		}
		if (!taskContext.getOutputParam().containsKey(OUTPUT_PARAM_RESULT)) {
			taskContext.getOutputParam().put(OUTPUT_PARAM_RESULT, TaskResult.FAIL);
		}

		logger.debug("Executing Custom Task Completed with result {}",
				taskContext.getOutputParam().get(OUTPUT_PARAM_RESULT));
	}

	/**
	 * Define the task workload. Do not run this directly
	 *
	 * @param taskContext
	 */
	public abstract void executeTask(TaskContext taskContext);
}
