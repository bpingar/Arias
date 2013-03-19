package com.bpingar.arias.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.bpingar.arias.R;

public class ItemListActivity extends FragmentActivity implements
		ItemListFragment.Callbacks {

	private boolean mTwoPane;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ItemListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);
		}
	}

	@Override
	public void onItemSelected(final Long id) {
		if (mTwoPane) {
			final Bundle arguments = new Bundle();
			arguments.putLong(ItemDetailFragment.ARG_ITEM_ID, id);
			final ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();
		} else {
			final Intent detailIntent = new Intent(this,
					ItemDetailActivity.class);
			detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
