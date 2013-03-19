package com.bpingar.arias.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.bpingar.arias.R;

public class ItemDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			final Bundle arguments = new Bundle();
			arguments.putLong(ItemDetailFragment.ARG_ITEM_ID, getIntent()
					.getLongExtra(ItemDetailFragment.ARG_ITEM_ID, -1));
			final ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.item_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this,
					new Intent(this, ItemListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
