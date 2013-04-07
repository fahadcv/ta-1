package com.andriod.tailorassist;

import com.andriod.tailorassist.conf.AppConfig;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.EditText;

public class MeasurmentFragment extends Fragment {
	String caption;
	EditText measurementField ;
	String measurmentText;
	String[] extraButtons;
	int type;
//	boolean changesSaved;
	public MeasurmentFragment(String caption){
		this.caption = "Enter "+caption+" Details";
//EditText measurementField;
//    	
//    	measurementField = (EditText)getView().findViewById(R.id.editText_shirtMeasurement);
//    	measurementField.setHint(caption);
//		changesSaved = true;
	}
	public MeasurmentFragment(String caption, int type){
		this(caption);
		this.extraButtons = AppConfig.extraButtons(type);
		this.type = type;
	}
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
		 View view = inflater.inflate(R.layout.fragment_measurments, container, false);
		 measurementField = (EditText)view.findViewById(R.id.editText_measurement);
		 /*EditText measurementField = (EditText)view.findViewById(R.id.editText_shirtMeasurement);*/
		 measurementField.setHint(caption)    ;
		 if(measurmentText != null){
			 measurementField.setText(measurmentText);
		 }
		 addExtraButtons(extraButtons);
	        return view;
	    }
//	public void clearMeasure(View v){
//		setMeasurement("");
//	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getMeasurement(){
//		View v = this.getView();
//		String measurement = null;		
//		final FragmentManager fm = getFragmentManager();
//		EditText measurementField = (EditText)v.findViewById(R.id.editText_measurement);
//		measurement = measurementField.getText().toString();
//		return measurement;
		//getActivity().getFragmentManager().get
		if(measurementField != null && measurementField.getText()!=null)
			return measurementField.getText().toString().trim();
		
		else
			return measurmentText == null ? "" : measurmentText;
//		return getText(R.id.editText_measurement).toString();
	} 
	public void appendMeasurement(String appendText){
//		changesSaved = false;
		int start = measurementField.getSelectionStart();
		
		if(start < measurementField.getText().length()){
			int end = measurementField.getSelectionEnd();
			measurementField.getText().replace(Math.min(start, end), Math.max(start, end),
				appendText, 0, appendText.length());
		}else
			measurementField.append(appendText + "  "); 
	}
	public void setMeasurement(String newText){
		if(newText == null)
			newText = "";
		measurmentText = newText;
		if(measurementField != null)
			measurementField.setText(newText);
//		else
//		{
//			measurmentText = newText;
//			//Log.e("setMeasurment","measurementField NOT yet init it is null");
//		}
	}
	public boolean isChangesSaved()
	{
		return (Util.isEmpty(measurmentText) && (measurementField == null || Util.isEmpty(measurementField.getText().toString()))
				|| (measurementField != null && measurementField.getText()!=null && measurementField.getText().toString().trim().equals(measurmentText))) ;
	}
	private void addExtraButtons(String extraButtons[]){
		if(extraButtons != null){
			for(String extraBtn: extraButtons){
//				btn.setText(extraBtn);TODO
			}
		}
	}
}
