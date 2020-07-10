package todo.rest.representations;

import java.util.ArrayList;

public class Task {

	private ToDoItem task;
	private ArrayList<ToDoItem> subTasks;

	public Task() {

	}

	public Task(ToDoItem task, ArrayList<ToDoItem> subTasks) {
		this.task = task;
		this.subTasks = subTasks;
	}

	public ToDoItem getMainTask() {
		return this.task;
	}

	public ArrayList<ToDoItem> getSubTasks() {
		if (this.subTasks == null)
			return new ArrayList<ToDoItem>();

		return this.subTasks;
	}

	public void setMainTask(ToDoItem main) {
		task = main;
	}

	public void setSubTasks(ArrayList<ToDoItem> subTasks) {
		if (subTasks == null)
			this.subTasks = new ArrayList<ToDoItem>();
		else
			this.subTasks = subTasks;
	}

	public void AddSubTask(ToDoItem subtask) {
		subTasks.add(subtask);
	}
}
