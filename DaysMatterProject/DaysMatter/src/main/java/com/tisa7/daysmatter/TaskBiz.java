package com.tisa7.daysmatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.content.Context;

public class TaskBiz {

	private static final String TASK_LIST_BIN = "task_list.bin";

	public static void saveTask(Context ctx, Task t) {
		ArrayList<Task> tasks = getTaskList(ctx);
		if (tasks == null) {
			tasks = new ArrayList<Task>();
		}
		tasks.add(t);
		SerializeUtil.saveIntoFile(ctx, TASK_LIST_BIN, tasks);
	}

	public static ArrayList<Task> getTaskList(Context ctx) {
		ArrayList<Task> ret = (ArrayList<Task>) SerializeUtil.readFile(ctx, TASK_LIST_BIN);
		if (ret != null) {
			Collections.sort(ret, new Comparator<Task>() {
				@Override
				public int compare(Task lhs, Task rhs) {
					return lhs.getSort() - rhs.getSort();
				}
			});
		}
		return ret;
	}
	
	public static void deleteTask(Context ctx, String id) {
		ArrayList<Task> tasks = getTaskList(ctx);
		if (tasks != null && id != null) {
			for (Task t : tasks) {
				if (id.equals(t.getId())) {
					tasks.remove(t);
					break;
				}
			}
		}
		SerializeUtil.saveIntoFile(ctx, TASK_LIST_BIN, tasks);
	}

	public static Task getMostRecent(ArrayList<Task> tasks) {
		if (tasks == null) {
			return null;
		}
		Task ret = null;
		Date now = new Date();
		for (Task task : tasks) {
			if (task.getDate().before(now)) {
				if (ret == null) {
					ret = task;
				} else if (task.getDate().after(ret.getDate())) {
					ret = task;
				}
			}
		}
		if (ret == null && tasks.size() > 0) {
			ret = tasks.get(0);
		}
		return ret;
	}

}
