package com.tisa7.daysmatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TaskListFragment extends Fragment implements OnItemClickListener,
		OnItemLongClickListener {

	private TextView mTvTopName;
	private TextView mTvTopDate;
	private TextView mTvTopRemainDays;
	private ListView mLvTaskList;
	private TaskAdapter mTaskAdapter;

	public TaskListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.task_list_fragment, null);
		mTvTopName = (TextView) v.findViewById(R.id.tv_top_name);
		mTvTopRemainDays = (TextView) v.findViewById(R.id.tv_top_remaind_days);
		mTvTopDate = (TextView) v.findViewById(R.id.tv_top_date);
		mLvTaskList = (ListView) v.findViewById(R.id.lv_task_list);
		mTaskAdapter = new TaskAdapter();
		mLvTaskList.setAdapter(mTaskAdapter);
		mLvTaskList.setOnItemClickListener(this);
		mLvTaskList.setOnItemLongClickListener(this);

		refreshList();

		return v;
	}

	private final class TaskLoader extends AsyncTask<Void, Void, ArrayList<Task>> {
		@Override
		protected ArrayList<Task> doInBackground(Void... params) {
			return TaskBiz.getTaskList(getActivity());
		}

		@Override
		protected void onPostExecute(ArrayList<Task> result) {
			super.onPostExecute(result);
			if (result != null) {
				mTaskAdapter.setTasks(result);
				if (result.size() > 0) {
					Task t = TaskBiz.getMostRecent(result);
					mTvTopName.setText(t.getName());
					mTvTopDate.setText(new SimpleDateFormat("yyyy年MM月dd日 结束").format(t.getDate()));
					mTvTopRemainDays.setText("" + Math.abs(DateUtil.getRemainDays(t.getDate())));
				}
			}
		}
	}

	private final class TaskAdapter extends BaseAdapter {

		private final ArrayList<Task> tasks = new ArrayList<Task>();

		public void setTasks(ArrayList<Task> newTasks) {
			tasks.clear();
			tasks.addAll(newTasks);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return tasks.size();
		}

		@Override
		public Object getItem(int position) {
			return tasks.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if (view == null) {
				view = getLayoutInflater(null).inflate(R.layout.task_list_item, null);
			}
			Task t = (Task) getItem(position);
			int remainDays = DateUtil.getRemainDays(t.getDate());

			TextView tvName = (TextView) view.findViewById(R.id.tv_name);
			if (remainDays >= 0) {
				tvName.setText("距离" + t.getName() + "还有");
			} else {
				tvName.setText(t.getName() + "已经");
			}
			TextView tvRemainTime = (TextView) view.findViewById(R.id.tv_remain_time);
			tvRemainTime.setText("" + Math.abs(remainDays));

			return view;
		}

	}

	public void refreshList() {
		new TaskLoader().execute();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		final Task t = (Task) mTaskAdapter.getItem(position);
		new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_launcher)
				.setTitle("要删掉" + t.getName() + "?")
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						TaskBiz.deleteTask(getActivity(), t.getId());
						refreshList();
					}
				}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).create().show();
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}