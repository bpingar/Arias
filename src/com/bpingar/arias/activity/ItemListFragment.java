package com.bpingar.arias.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bpingar.arias.model.Marca;

public class ItemListFragment extends ListFragment {

	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	private Callbacks mCallbacks;

	private int mActivatedPosition = ListView.INVALID_POSITION;

	public interface Callbacks {
		public void onItemSelected(Long id);
	}

	public ItemListFragment() {
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: replace with a real list adapter.
		setListAdapter(new ArrayAdapter<Marca>(getActivity(),
				android.R.layout.simple_list_item_activated_1, Marca.ITEMS));
	}

	@Override
	public void onViewCreated(final View view, final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onListItemClick(final ListView listView, final View view,
			final int position, final long id) {
		super.onListItemClick(listView, view, position, id);

		mCallbacks.onItemSelected(Marca.ITEMS.get(position).getId());
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);

		if (mActivatedPosition != ListView.INVALID_POSITION) {
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	public void setActivateOnItemClick(final boolean activateOnItemClick) {
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(final int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}
		mActivatedPosition = position;
	}
}
