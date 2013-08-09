package com.tisa7.daysmatter;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.StackView;

public class MainActivity extends FragmentActivity {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	
	private TaskListFragment mTaskListFragment;
	private MenuItem mMenuCreate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_TITLE);
		mTaskListFragment = new TaskListFragment();
		
		getSupportFragmentManager().beginTransaction().add(R.id.container, mTaskListFragment)
				.commit();
		
		getSupportFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
			@Override
			public void onBackStackChanged() {
				if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
					mMenuCreate.setVisible(true);
					mTaskListFragment.refreshList();
				} else {
					mMenuCreate.setVisible(false);
				}
			}
		});
		
		StackView sv;
		Looper l;
		NumberPicker np;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		mMenuCreate = menu.getItem(0);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		TaskCreateFragment tcf = new TaskCreateFragment();
		tcf.show(getSupportFragmentManager(), "");
		
		return super.onOptionsItemSelected(item);
	}

	public void showTimePickerDialog(View v) {
	}

}
