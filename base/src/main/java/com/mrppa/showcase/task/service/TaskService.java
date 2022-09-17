package com.mrppa.showcase.task.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mrppa.showcase.CustomServiceException;
import com.mrppa.showcase.ErrorCodes;
import com.mrppa.showcase.base.services.BaseCrudService;
import com.mrppa.showcase.task.TaskContext;
import com.mrppa.showcase.task.TaskResult;
import com.mrppa.showcase.task.dao.TaskDao;
import com.mrppa.showcase.task.model.Task;
import com.mrppa.showcase.task.repository.TaskRepository;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
public class TaskService extends BaseCrudService<TaskDao, Task, Long> {

	@Autowired
	private ModelMapper modelMapper;

	private TaskRepository repository;

	@Autowired
	public TaskService(TaskRepository repository) {
		super(repository);
		this.repository = repository;
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	List<TaskDefinition> taskDefinitionList = new ArrayList<>();

	/**
	 * Execute task in the system
	 *
	 * @param taskId           - Id of the task. If null this will create a new task
	 * @param taskDefinitionId - definition of the task. If not found throw a
	 *                         CustomServiceException
	 * @param inputParam       - input parameter map
	 * @param outputParam      -empty map for output parameters
	 * @return Task object
	 * @throws CustomServiceException
	 */
	public Task runTask(Long taskId, String taskDefinitionId, Map<String, Object> inputParam,
			Map<String, Object> outputParam) throws CustomServiceException {
		logger.debug("About to run the task {}", taskDefinitionId);

		// Finding task if exits. Otherwise create a new
		TaskDao taskDao;
		if (taskId == null) {
			taskDao = new TaskDao();
			taskDao.setQueuedDate(LocalDateTime.now());
		} else {
			Optional<TaskDao> optTaskDao = repository.findById(taskId);
			if (optTaskDao.isEmpty()) {
				logger.error("Task with id {} not found");
				throw new CustomServiceException(ErrorCodes.ITEM_NOT_FOUND);
			}
			taskDao = optTaskDao.get();
		}

		taskDao.setExecutionStartDate(LocalDateTime.now());
		taskDao.setInputParam(inputParam);
		taskDao.setTaskResult(TaskResult.STARTED);
		taskDao.setTaskDefinitionId(taskDefinitionId);

		// finding task definition
		Optional<TaskDefinition> optTaskDef = taskDefinitionList.stream()
				.filter(task -> task.getTaskDefinitionId().equals(taskDefinitionId)).findAny();
		if (optTaskDef.isEmpty()) {
			logger.error("Task Definition {} . not found", taskDefinitionId);
			throw new CustomServiceException(ErrorCodes.ITEM_NOT_FOUND);
		}

		TaskContext taskContext = new TaskContext();
		taskContext.setInputParam(inputParam);
		taskContext.setOutputParam(outputParam);

		// execute task
		optTaskDef.get().execute(taskContext);

		// save task info
		taskDao.setExecutionEndDate(LocalDateTime.now());
		taskDao.setTaskResult((TaskResult) taskContext.getOutputParam().get(TaskDefinition.OUTPUT_PARAM_RESULT));
		taskDao.setOutputParam(taskContext.getOutputParam());
		repository.save(taskDao);
		logger.debug("Task definition {} executed with task id {}", taskDefinitionId, taskDao.getId());

		return toUi(taskDao);
	}

	@Override
	public Task toUi(TaskDao dao) {
		Task task = modelMapper.map(dao, Task.class);
		return task;
	}

	@Override
	public TaskDao setToDao(TaskDao dao, Task model) {
		if (dao == null) {
			dao = new TaskDao();
		}
		TaskDao taskDao = modelMapper.map(dao, TaskDao.class);
		return taskDao;
	}
}
