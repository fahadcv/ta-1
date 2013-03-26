package com.andriod.tailorassist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewCustomerHome extends Activity {
	
	Context Ctxt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_home);    
        getActionBar().setHomeButtonEnabled(true);
        Ctxt= this;
        Button btnSAddMeasurement = (Button) findViewById(R.id.button_addMeasurement);
        
        btnSAddMeasurement.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			String custName = new String();
			String custMobile = new String();
			String custAddress = new String();
			
			EditText Name = (EditText) findViewById(R.id.editText_name);
			EditText Mobile = (EditText) findViewById(R.id.editText_mobileNumber);
			EditText Address = (EditText) findViewById(R.id.editText_address);
			custName = Name.getText().toString();			
			custMobile = Mobile.getText().toString();			
			custAddress = Address.getText().toString();
			
			if((custName.length()== 0) || (custMobile.length()== 0) ){
				
				Toast.makeText(Ctxt, R.string.alertTxt_NewCustomer_Name_mobile_empty, Toast.LENGTH_LONG).show();
			}
			else if(!Util.isValidMobileNumber(custMobile)){
				
				Toast.makeText(Ctxt, R.string.alertTxt_NewCustomer_invalid_mobile, Toast.LENGTH_LONG).show();
			}else{
			
//				Intent intent = new Intent(Ctxt,AddMeasurement.class );
				Intent intent = new Intent(Ctxt,MeasurementsEntryActivity.class );
				/* sending the customer details to next activity 			 */
				Bundle bundle = new Bundle();
				
				bundle.putString("custName", custName);
				bundle.putString("custMobile", custMobile);
				bundle.putString("custAddress", custAddress);
				
				intent.putExtras(bundle);			
				
				//start the activity			
				Ctxt.startActivity(intent);
			}			
			
		}
	});
        com.andriod.tailorassist.screen.Styles.setActionBarStyle(getActionBar(), getResources());
        //TODO render with current values
        /*
         * use below keys and render values accordingly and enable save buttun if custId>0
         bundle.putLong("custId", custId);
			bundle.putString("custName", custName);
			bundle.putString("custMobile", custMobile);
			bundle.putString("custAddress", custAddress);
			bundle.putString("mode", "edit");
         * */
   }
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	
    	 case android.R.id.home:
             // app icon in action bar clicked; go home
             Intent intent = new Intent(this,Matrix.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(intent);
             return true;
         default:
             return super.onOptionsItemSelected(item);
    	}
    	
 }       
}
