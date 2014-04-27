package com.andriod.tailorassist;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils.TruncateAt;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.andriod.tailorassist.conf.AppConfig;

public class MeasurmentFragment extends Fragment {
	String caption;
	EditText measurementField;
	String measurmentText;
	String[] extraButtons;
	int type;
	TableLayout OtherMeasures;
	int ems = 10;
	int top = 1, left = 1, bottom = 1, right = 1;
	int rowTop = 5, rowLeft = 1, rowBottom = 5, rowRight = 1;
	int fontSize = 18;
	// boolean changesSaved;
	public MeasurmentFragment(String caption) {
		this.caption = "Enter " + caption + " Details";
		// EditText measurementField;
		//
		// measurementField =
		// (EditText)getView().findViewById(R.id.editText_shirtMeasurement);
		// measurementField.setHint(caption);
		// changesSaved = true;
	}

	public MeasurmentFragment(String caption, int type) {
		this(caption);
		this.extraButtons = AppConfig.extraButtons(type);
		this.type = type;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	//	  setTheme(SampleList.THEME); //Used for theme switching in samples
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_measurments, container,
				false);
		measurementField = (EditText) view
				.findViewById(R.id.editText_measurement);
		/*
		 * EditText measurementField =
		 * (EditText)view.findViewById(R.id.editText_shirtMeasurement);
		 */
		measurementField.setHint(caption);
		if (measurmentText != null) {
			measurementField.setText(measurmentText);
		}

		// OtherMeasures.addV
		if (extraButtons != null) {
			OtherMeasures = (TableLayout) view
					.findViewById(R.id.OtherMeasuremntTable);
			// Button othrBtn = (Button)
			// getView().findViewById(R.id.others_btn);
			addExtraButtons(extraButtons, OtherMeasures, view.getContext());

		}

		return view;
	}

	// public void clearMeasure(View v){
	// setMeasurement("");
	// }
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getMeasurement() {
		// View v = this.getView();
		// String measurement = null;
		// final FragmentManager fm = getFragmentManager();
		// EditText measurementField =
		// (EditText)v.findViewById(R.id.editText_measurement);
		// measurement = measurementField.getText().toString();
		// return measurement;
		// getActivity().getFragmentManager().get
		if (measurementField != null && measurementField.getText() != null)
			return measurementField.getText().toString().trim();

		else
			return measurmentText == null ? "" : measurmentText;
		// return getText(R.id.editText_measurement).toString();
	}

	public void appendMeasurement(String appendText) {
		// changesSaved = false;
		int start = measurementField.getSelectionStart();

		if (start < measurementField.getText().length()) {
			int end = measurementField.getSelectionEnd();
			measurementField.getText().replace(Math.min(start, end),
					Math.max(start, end), appendText, 0, appendText.length());
		} else
			measurementField.append(appendText + "  ");
	}

	public void setMeasurement(String newText) {
		if (newText == null)
			newText = "";
		measurmentText = newText;
		if (measurementField != null)
			measurementField.setText(newText);
		// else
		// {
		// measurmentText = newText;
		// //Log.e("setMeasurment","measurementField NOT yet init it is null");
		// }

	}

	public boolean isChangesSaved() {
		return (Util.isEmpty(measurmentText)
				&& (measurementField == null || Util.isEmpty(measurementField
						.getText().toString())) || (measurementField != null
				&& measurementField.getText() != null && measurementField
				.getText().toString().trim().equals(measurmentText)));
	}

	private void addExtraButtons(String extraButtons[],
			TableLayout targetTable, Context context) {
		if (extraButtons != null) {
			int colCount = 3;
			int curCol = 0;
			TableRow curRow = null;

			for (String extraBtn : extraButtons) {
				// btn.setText(extraBtn);TODO
				if (curCol == 0) {
					curRow = new TableRow(context);
					curRow.setPadding(rowLeft, rowTop, rowRight, rowBottom);
					targetTable.addView(curRow);
					
					
				}
				TextView item = new TextView(context);
				item.setText(extraBtn);
				item.setPadding(left, right, top, bottom);
				item.setEms(ems);
				item.setHint(R.string.hnt_txt_special_instruction_click);
				item.setEllipsize(TruncateAt.MARQUEE);
				item.setTypeface(Typeface.DEFAULT_BOLD);
				item.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
//				item.setBackground(new BitmapDrawable(
//						BitmapFactory.decodeResource(getResources(),
//								R.drawable.bg_edittext_small)));
				
//				item.
//				item.setTextColor()
//				ColorStateList colrLiust = new ColorStateList(states, colors)
				curRow.addView(item);
				item.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						TextView itemText = (TextView) v;
						appendMeasurement(itemText.getText().toString());
					}
				});
				
				curCol = (curCol+1) % colCount;
			}
		}
	}

	public void doOtherBtns(View v) {
		if (OtherMeasures != null) {
			Button btn = (Button) v;
			if (btn.getText().equals(
					getResources().getString(R.string.btnText_Measures_other))) {
				OtherMeasures.setVisibility(View.VISIBLE);
				btn.setText(getResources().getString(
						R.string.btnText_Measures_close));
			} else {
				OtherMeasures.setVisibility(View.INVISIBLE);
				btn.setText(getResources().getString(
						R.string.btnText_Measures_other));
			}
		}
	}

}
