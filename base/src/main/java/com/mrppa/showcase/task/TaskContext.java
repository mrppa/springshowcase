package com.mrppa.showcase.task;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class TaskContext {
	private Map<String, Object> inputParam = new HashMap<>();
	private Map<String, Object> outputParam = new HashMap<>();
}
