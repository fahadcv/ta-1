package com.andriod.tailorassist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.System;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andriod.tailorassist.conf.AppConfig;

public class MeasurementsEntryActivity extends FragmentActivity implements
		ActionBar.TabListener {
	SimpleDateFormat DBdateFormat = new SimpleDateFormat("yyyy-MM-dd");

	final public static int PROFILE = 1;
	final public static int HOME = 2;
	private static final int VIRTUAL_KEY = 0;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	Context Ctxt;
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	long custId = -1;
	String custName;
	String custMobile;
	String custAddress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			Ctxt = this;
			super.onCreate(savedInstanceState);

			setContentView(R.layout.activity_measurements_entry);
			// Create the adapter that will return a fragment for each of the
			// three primary sections
			// of the app.
			mSectionsPagerAdapter = new SectionsPagerAdapter(
					getSupportFragmentManager());

			// Set up the action bar.
			final ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setHomeButtonEnabled(true);

			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);

			// When swiping between different sections, select the corresponding
			// tab.
			// We can also use ActionBar.Tab#select() to do this if we have a
			// reference to the
			// Tab.
			mViewPager
					.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
						@Override
						public void onPageSelected(int position) {
							actionBar.setSelectedNavigationItem(position);
						}
					});

			// For each of the sections in the app, add a tab to the action bar.
			for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
				// Create a tab with text corresponding to the page title
				// defined by the adapter.
				// Also specify this Activity object, which implements the
				// TabListener interface, as the
				// listener for when this tab is selected.
				Tab tab = actionBar.newTab();
				tab.setText(mSectionsPagerAdapter.getPageTitle(i));
				// tab.setIcon(icon)
				tab.setTabListener(this);
				actionBar.addTab(tab);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Bundle extras = this.getIntent().getExtras();
		custName = extras.getString("custName");
		custMobile = extras.getString("custMobile");
		custAddress = extras.getString("custAddress");
		if (extras.getString("mode") != null
				&& "edit".equals(extras.getString("mode"))) {
			custId = extras.getLong("custId");

			initEdit("preserve".equals(extras.getString("changes")));
		} else {
			custId = -1;

		}
		com.andriod.tailorassist.screen.Styles.setActionBarStyle(
				getActionBar(), getResources());
	}

	private void initEdit(boolean preserveProfileChanges) {
		CustomerTable custTable = new CustomerTable(this);
		custTable.open();
		Cursor newCustDetails = custTable.fetchCustomerById(custId);
		custTable.close();
		if (!preserveProfileChanges) {
			custName = newCustDetails.getString(newCustDetails
					.getColumnIndex(custTable.KEY_NAME));
			custMobile = newCustDetails.getString(newCustDetails
					.getColumnIndex(custTable.KEY_MOBILE));
			custAddress = newCustDetails.getString(newCustDetails
					.getColumnIndex(custTable.KEY_ADDRESS));
		}
		String custShirtDetails = newCustDetails.getString(newCustDetails
				.getColumnIndex(custTable.KEY_SHIRTDETAILS));
		String custPantDetails = newCustDetails.getString(newCustDetails
				.getColumnIndex(custTable.KEY_PANTDETAILS));
		String custOtherDetails = newCustDetails.getString(newCustDetails
				.getColumnIndex(custTable.KEY_OTHERDETAILS));
		// getting the string for the details
		newCustDetails.close();
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

			// String tabTitle =
			// mSectionsPagerAdapter.getPageTitle(i).toString();
			// System.out.println(i+":"+tabTitle);
			// int tabCount = mSectionsPagerAdapter.getCount();
			// MeasurmentFragment fragment = new MeasurmentFragment(tabTitle);

			MeasurmentFragment fragment = (MeasurmentFragment) mSectionsPagerAdapter
					.getItem(i);
			// String measurement = fragment.getMeasurement();
			// if(measurement.isEmpty()) measurement = "";
			switch (i) {
			case AppConfig.Types.SHIRT:
				fragment.setMeasurement(custShirtDetails);
				break;
			case AppConfig.Types.TROUSER:
				fragment.setMeasurement(custPantDetails);
				break;
			default:
				fragment.setMeasurement(custOtherDetails);
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (custId > 0)
			getMenuInflater().inflate(R.menu.action_menu, menu);
		else
			getMenuInflater().inflate(
					R.menu.activity_measurment_new_action_menu, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save_btn:
			if (!isChangesSaved()) {
				return save();
			} else {
				if (isEmpty()) {

					Toast.makeText(
							Ctxt,
							R.string.alertTxt_NewCustomer_Measuremetns_emptyMsg,
							Toast.LENGTH_LONG).show();
				}
				return false;
			}
		case android.R.id.home:
			if (isChangesSaved()) {
				return goHome();
			} else {
				unSavedAlert(HOME).show();

			}
			return false;

		case R.id.editCustProfile_btn:
			if (isChangesSaved()) {
				return editProfile();
			} else {
				unSavedAlert(PROFILE).show();

			}
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean save() {
		Bundle details = this.getIntent().getExtras();
		details.putLong("custId", custId);
		putMeasurmentDetails(details);
		// for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
		//
		// // String tabTitle =
		// mSectionsPagerAdapter.getPageTitle(i).toString();
		// // System.out.println(i+":"+tabTitle);
		// // int tabCount = mSectionsPagerAdapter.getCount();
		// // MeasurmentFragment fragment = new MeasurmentFragment(tabTitle);
		//
		// MeasurmentFragment fragment =
		// (MeasurmentFragment)mSectionsPagerAdapter.getItem(i);
		// String measurement = fragment.getMeasurement();
		// if(measurement.isEmpty()) measurement = "";
		// switch (i) {
		// case SHIRT:
		// details.putString("shirtDetails", measurement);
		// break;
		// case TROUSER:
		// details.putString("trouserDetails", measurement);
		// break;
		// default:
		// details.putString("otherDetails", measurement);
		// break;
		// }
		// }
		long newCustNumber = addCustomerDetails(details);

		if (newCustNumber != 0) {
			String newCustNum = String.valueOf(newCustNumber);
			// Show the next layout view
			Intent intent = new Intent(Ctxt, ShowCustomerDetails.class);
			/* sending the customer details to next activity */
			Bundle bundle = new Bundle();
			bundle.putString("newCustmerNumber", newCustNum);
			intent.putExtras(bundle);
			// start the activity
			Ctxt.startActivity(intent);
			return true;
		}
		return false;
	}

	public boolean goHome() {
		Intent intent = new Intent(this, Matrix.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		return true;
	}

	public boolean editProfile() {
		Intent intent1 = new Intent(this, NewCustomerHome.class);
		Bundle bundle = new Bundle();

		bundle.putLong("custId", custId);
		bundle.putString("custName", custName);
		bundle.putString("custMobile", custMobile);
		bundle.putString("custAddress", custAddress);
		bundle.putString("mode", "edit");
		putMeasurmentDetails(bundle);
		intent1.putExtras(bundle);
		startActivity(intent1);
		return true;
	}

	public void putMeasurmentDetails(Bundle details) {
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

			// String tabTitle =
			// mSectionsPagerAdapter.getPageTitle(i).toString();
			// System.out.println(i+":"+tabTitle);
			// int tabCount = mSectionsPagerAdapter.getCount();
			// MeasurmentFragment fragment = new MeasurmentFragment(tabTitle);

			MeasurmentFragment fragment = (MeasurmentFragment) mSectionsPagerAdapter
					.getItem(i);
			String measurement = fragment.getMeasurement();
			if (measurement.isEmpty())
				measurement = "";
			switch (i) {
			case AppConfig.Types.SHIRT:
				details.putString("shirtDetails", measurement);
				break;
			case AppConfig.Types.TROUSER:
				details.putString("trouserDetails", measurement);
				break;
			default:
				details.putString("otherDetails", measurement);
				break;
			}
		}
	}

	public boolean isEmpty() {
		boolean empty = true;
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			MeasurmentFragment fragment = (MeasurmentFragment) mSectionsPagerAdapter
					.getItem(i);
			String measurement = fragment.getMeasurement();
			if (!measurement.isEmpty()) {
				empty = false;
			}

		}
		return empty;
	}

	// public void onTabUnselected(ActionBar.Tab tab,
	// FragmentTransaction fragmentTransaction) {
	//
	// }
	//
	// public void onTabSelected(ActionBar.Tab tab,
	// FragmentTransaction fragmentTransaction) {
	// // When the given tab is selected, switch to the corresponding page in
	// // the ViewPager.
	// mViewPager.setCurrentItem(tab.getPosition());
	//
	// }

	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// tab.getCustomView().setBackgroundResource(R.drawable.measurement_nav);
		Log.d("tabCustomTesting", "at onTabUnselected");
		tab.setCustomView(R.layout.tab_default);
	}

	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
		tab.setCustomView(R.layout.tab_selected);
		ActionBar actionBar = getActionBar();
		for (int i = 0; i < actionBar.getTabCount(); i++) {
			ActionBar.Tab tab1 = actionBar.getTabAt(i);
			if (tab1 != tab) {
				Log.d("tabCustomTesting", "at not selected tab ");
				tab1.setCustomView(R.layout.tab_default);
			} else {
				Log.d("tabCustomTesting", "at selected tab ");
			}
		}
		// tab.getCustomView().setBackgroundResource(R.drawable.measurement_nav_selected);
	}

	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		List<Fragment> tabs;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			tabs = new ArrayList<Fragment>(3);
			tabs.add(new MeasurmentFragment(
					getString(R.string.measurment_tab1_text),
					AppConfig.Types.SHIRT));
			tabs.add(new MeasurmentFragment(
					getString(R.string.measurement_tab2_text),
					AppConfig.Types.TROUSER));
			tabs.add(new MeasurmentFragment(
					getString(R.string.measuremnet_tab3_text)));
		}

		@Override
		public Fragment getItem(int i) {

			MeasurmentFragment fragment = (MeasurmentFragment) tabs.get(i);
			// EditText measurementField;
			//
			// measurementField =
			// (EditText)findViewById(R.id.editText_shirtMeasurement);
			// measurementField.setHint(fragment.getCaption());
			return fragment;
		}

		@Override
		public int getCount() {
			return tabs.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {

			switch (position) {
			case 0:
				return getString(R.string.measurment_tab1_text);
			case 1:
				return getString(R.string.measurement_tab2_text);
			case 2:
				return getString(R.string.measuremnet_tab3_text);
			}
			return null;
		}
	}

	public long addCustomerDetails(Bundle extras) {

		long newCustNo = 0;
		String custShirt = extras.getString("shirtDetails");// ((EditText)findViewById(R.id.editText_measurement)).getText().toString();
		String custPant = extras.getString("trouserDetails");// ((EditText)findViewById(R.id.editText_measurement)).getText().toString();;
		String custOther = extras.getString("otherDetails");
		String entryDate = DBdateFormat.format(new Date());

		if ((custShirt.length() == 0) && (custPant.length() == 0)
				&& (custOther.length() == 0)) {

			Toast.makeText(Ctxt,
					R.string.alertTxt_NewCustomer_Measuremetns_emptyMsg,
					Toast.LENGTH_LONG).show();
		} else {
			Log.d("addCustomerDetails", "shirtDetails : " + custShirt
					+ " trouserDetails : " + custPant + " otherDetails :"
					+ custOther);
			// long custId = extras.getLong("custId");
			// String custName = extras.getString("custName");
			// String custMobile = extras.getString("custMobile");
			// String custAddress = extras.getString("custAddress");

			CustomerTable custTable = new CustomerTable(this);
			custTable.open();
			if (custId <= 0) {
				newCustNo = custTable.addCustomer(custName, custMobile,
						custAddress, custShirt, custPant, custOther, entryDate);
			} else {
				if (custTable.updateCustomer(custId, custName, custMobile,
						custAddress, custShirt, custPant, custOther, entryDate)) {
					newCustNo = custId;
				}
			}
			custTable.close();
			if (newCustNo > 0) {
				Toast.makeText(
						Ctxt,
						"Customer details stored successfully as customer number : "
								+ newCustNo, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(Ctxt, "Failed to save customer details",
						Toast.LENGTH_SHORT).show();
			}
		}
		return newCustNo;
	}

	public void extraBtnclick(View v) {

		Button clickedBtn = (Button) findViewById(v.getId());
		String btnTxt = clickedBtn.getText().toString();

		// EditText measurementField;
		// measurementField = (EditText)findViewById(R.id.editText_measurement);
		// measurementField.append(btnTxt + "\t");
		/*
		 * String currentMesurementText = measurementField.getText().toString();
		 * currentMesurementText = currentMesurementText + btnTxt+"\t";
		 */
		MeasurmentFragment msrmntFrg = (MeasurmentFragment) mSectionsPagerAdapter
				.getItem(mViewPager.getCurrentItem());
		msrmntFrg.appendMeasurement(btnTxt);
		// getApplicationContext().getSystemService(VIBRATOR_SERVICE)
		// System mySystemSettings = new Settings.System();
		ContentResolver mContentResolver = this.getContentResolver();
		int val = System.getInt(mContentResolver,
				Settings.System.HAPTIC_FEEDBACK_ENABLED, 0);
		boolean mHapticEnabled = val != 0;
		if (mHapticEnabled) {
			v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
		}

		// EditText measurementField;
		// measurementField =
		// (EditText)msrmntFrg.getText(R.id.editText_measurement);

	}

	public void clearMeasure(View v) {
		MeasurmentFragment msrmntFrg = (MeasurmentFragment) mSectionsPagerAdapter
				.getItem(mViewPager.getCurrentItem());
		msrmntFrg.setMeasurement("");

	}

	public boolean isChangesSaved() {
		boolean changesSaved = true;
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			MeasurmentFragment fragment = (MeasurmentFragment) mSectionsPagerAdapter
					.getItem(i);
			if (!fragment.isChangesSaved()) {
				changesSaved = false;
				break;
			}
		}
		return changesSaved;
	}

	public void showOtherMeasures(View v) {
		MeasurmentFragment msrFragment = (MeasurmentFragment) mSectionsPagerAdapter
				.getItem(mViewPager.getCurrentItem());
		msrFragment.doOtherBtns(v);
	}

	public Dialog unSavedAlert(final int moveTo) {

		AlertDialog.Builder builder = new AlertDialog.Builder(Ctxt);
		builder.setMessage("Measurements have been modified. Save changes?");
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("Save",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						save();
						goHome();
					}
				});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				if (moveTo == HOME)
					goHome();
				else if (moveTo == PROFILE)
					editProfile();
			}
		});
		builder.setNeutralButton("Cancel",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}
				});
		return builder.create();
	}
}
