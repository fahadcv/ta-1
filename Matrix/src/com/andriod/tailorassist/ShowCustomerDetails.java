package com.andriod.tailorassist;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andriod.tailorassist.adapter.BindDataAdapter;
import com.andriod.tailorassist.screen.Styles;



public class ShowCustomerDetails extends Activity {
	Context Ctxt;
	String searchedCustName ;
	long custId;
	int[] img = { R.drawable.ic_shirt, R.drawable.ic_trouser, R.drawable.ic_trouser  };
	String titles[] = new String[]{"Shirt", "Trouser", "Others"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ctxt = this;
//        setContentView(R.layout.activity_show_customer_details); 
        setContentView(R.layout.activity_cust_details_view); 
//        Log.d("ShowCustomerDetails.onCreate", "bundle: "+savedInstanceState);
        getActionBar().setHomeButtonEnabled(true);
        Bundle extras = this.getIntent().getExtras();
        
        custId = Long.parseLong(extras.getString("newCustmerNumber"));
    	
    	CustomerTable custTable = new CustomerTable(this);
    	custTable.open();
    	Cursor newCustDetails = custTable.fetchCustomerById(custId);
    	custTable.close();
    	 searchedCustName = extras.getString("searchedOn");
    	
    	if (newCustDetails.getCount()!= 0){    		
    		
    		String custName = newCustDetails.getString(newCustDetails.getColumnIndex(custTable.KEY_NAME));
        	String custMob = newCustDetails.getString(newCustDetails.getColumnIndex(custTable.KEY_MOBILE));    		
        	String custAdrs = newCustDetails.getString(newCustDetails.getColumnIndex(custTable.KEY_ADDRESS));
        	String custShirtDetails = newCustDetails.getString(newCustDetails.getColumnIndex(custTable.KEY_SHIRTDETAILS));
        	String custPantDetails = newCustDetails.getString(newCustDetails.getColumnIndex(custTable.KEY_PANTDETAILS));
        	String custOtherDetails = newCustDetails.getString(newCustDetails.getColumnIndex(custTable.KEY_OTHERDETAILS));
//        	// getting the string for the details
//        	newCustDetails.close();
//        	StringBuilder detailText = new StringBuilder();
//        	detailText.append("\tCustomer Number:\t");
//        	detailText.append(custId);
//        	detailText.append("\n\tName:\t");
//        	detailText.append(custName);
//        	detailText.append("\n\tMobile:\t");
//        	detailText.append(custMob);
//        	detailText.append("\n\tAddress:\t");
//        	detailText.append(custAdrs);
//        	if(custShirtDetails != null && custShirtDetails.length()>0){
//	        	detailText.append("\n\n\tShirt:\t\n\t\t");
//	        	detailText.append(custShirtDetails.replaceAll("\n", "\n\t\t"));
//        	}
//        	if(custPantDetails != null && custPantDetails.length()>0){
//        		detailText.append("\n\n\tTrouser:\t\n\t\t");
//            	detailText.append(custPantDetails.replaceAll("\n", "\n\t\t"));	
//        	}
//        	if(custOtherDetails != null && custOtherDetails.length()>0){
//            	detailText.append("\n\n\tOther:\t\n\t\t");
//            	detailText.append(custOtherDetails.replaceAll("\n", "\n\t\t"));	
//        	}
//        	detailText.append("\n\n");
//        	
////        	String details = "\tCustomer Number:\t"+ custId+ 
////        			"\n\tName:\t"+ custName+"\n\tMobile:\t"+custMob+"\n\tAddress:\t"+custAdrs+
////        			"\n\n\t\tShirt measurement: \n\t" +custShirtDetails+
////        			"\n\n\t\tTrouser Measurement: \n\t" + custPantDetails +   
////        			"\n\n\t\tOther Measurement: \n\t" + custOtherDetails + "\n\n";  
//        	
//        	TextView custDetailsView = (TextView) findViewById(R.id.textView_ShowCustDetails);
//        	custDetailsView.setText(detailText.toString());   
    		
    		TextView name = (TextView)findViewById(R.id.cust_view_name);
    		name.setText(custName);
    		TextView mobile = (TextView)findViewById(R.id.cust_view_mobile);
    		mobile.setText(custMob);
    		TextView adr = (TextView)findViewById(R.id.cust_view_address);
    		adr.setText(custAdrs);
    		
    		ListView mesurmentList = (ListView)findViewById(R.id.detail_view_measurments_list);
//    		mesurmentList.setDividerHeight(2);
    		mesurmentList.setAdapter(new BindDataAdapter(this, img,titles, new String[]{custShirtDetails, custPantDetails, custOtherDetails} ));
    		View canvas = (View)findViewById(R.id.custdetail_main);
    		canvas.setRotationX(-5);
    		canvas.setRotation(canvas.getHeight()/2);
    	}
    	
        Styles.setActionBarStyle(getActionBar(), getResources());
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
    
//	public void goHome(View v){
//	    	
//	    	/*Intent intent = new Intent(this,Matrix.class);
//	    	startActivity(intent);*/
//	    	
//	        Intent parentActivityIntent = new Intent(this, Matrix.class);
//	        parentActivityIntent.addFlags(
//	                Intent.FLAG_ACTIVITY_CLEAR_TOP |
//	                Intent.FLAG_ACTIVITY_NEW_TASK);
//	        startActivity(parentActivityIntent);
//	        finish();
//	    	
//	}
	   public void goHome(View v){
	    	
	    	/*Intent intent = new Intent(this,Matrix.class);
	    	startActivity(intent);*/
	    	
	        Intent parentActivityIntent = new Intent(this, Matrix.class);
	        parentActivityIntent.addFlags(
	                Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(parentActivityIntent);
	        finish();
	    	
	    }
	    
	    public void goBack(){ 
	    	
	    	if(searchedCustName != null){
	    		
	    		
				
				
//	    		Intent parentActivityIntent = new Intent(this, SearchCustomerByName.class);
	    		Intent parentActivityIntent = new Intent(this, SearchableActivity.class);
	            /*parentActivityIntent.addFlags(
	                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                    Intent.FLAG_ACTIVITY_NEW_TASK);*/
	    		Bundle bundle = new Bundle();			
				
				bundle.putString("searchedOn", searchedCustName);
	    		parentActivityIntent.putExtras(bundle);
	            startActivity(parentActivityIntent);
	            finish();
	    	}
	    	else{
	    		goHome(null);
	    	}
	    }
	    
//	    public void cancelEit(View v){
//	    	
//	    	EditText shirtDetailsField = (EditText)findViewById(R.id.editText_serchResult_ShirtDetails);		
//			shirtDetailsField.setEnabled(false);				
//			shirtDetailsField.setBackgroundResource(R.color.Color_steelBlue);
//			shirtDetailsField.setClickable(false);
//					
//			EditText pantDetailsField = (EditText)findViewById(R.id.editText_serchResult_PantDetails);		
//			pantDetailsField.setEnabled(false);
//			pantDetailsField.setBackgroundResource(R.color.Color_steelBlue);
//			pantDetailsField.setClickable(false);
//			
//	    	v.setVisibility(4);
//			
//			Button saveBtn = (Button)findViewById(R.id.btn_SearchResult_Save);
//			saveBtn.setVisibility(4);
//			
//			Button editBtn = (Button)findViewById(R.id.btn_SearchResult_Edit);
//			editBtn.setVisibility(0);
//			
//			Button deleteBtn = (Button)findViewById(R.id.btn_SearchResult_Delete);
//			deleteBtn.setVisibility(0);
//			
//			Button homeBtn = (Button)findViewById(R.id.btn_SearchResult_Home);
//			homeBtn.setVisibility(0);	
//	    }
//	    
//	    private boolean updateMeasurements(long custNum, String custName, String custMob, String custAdrs,String newShirtMesurement,String newPantMesurement,String newOtherMesurement) {
//			int cNum = (int) custNum;
//			boolean updated = false;
//	    	CustomerTable custTable = new CustomerTable(Ctxt);
//	    	custTable.open();
//	    	updated = custTable.updateCustomer(cNum, custName, custMob, custAdrs, newShirtMesurement, newPantMesurement, newOtherMesurement);
//			custTable.close();	
//			
//			return updated;
//		}
	    
	    
	    public void editMe(View v){
//	    	Toast.makeText(Ctxt,"Edit NOT yet implemented!",Toast.LENGTH_LONG).show();
	    	Intent intent = new Intent(Ctxt,MeasurementsEntryActivity.class);				
			/* sending the customer details to next activity 			 */
			Bundle bundle = new Bundle();			
			bundle.putString("mode", "edit");		
			Log.d("editMe","custId : "+custId);
			bundle.putLong("custId", custId);
			intent.putExtras(bundle);				
			//start the activity				
			Ctxt.startActivity(intent);		
	    }
	    public void deleteMe(View v){
	    	deleteConfirm(custId).show();
	    }
	    public Dialog deleteConfirm(final long custNum){
	    	
	    	AlertDialog.Builder builder = new AlertDialog.Builder(Ctxt);
			builder.setMessage(R.string.alertDialogTxt_SearchResult_deleteMessage);
			builder.setInverseBackgroundForced(true);
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					boolean isDeleted = deleteCustomer(custNum);
					if(isDeleted){
						
						Toast.makeText(Ctxt,R.string.alertTxt_SearchResult_customerDelete_success,Toast.LENGTH_LONG).show();					
						/* Going back home after deleting the record*/					
						goBack();
					}
					else{
						
						Toast.makeText(Ctxt,R.string.alertTxt_SearchResult_customerDelete_fail,Toast.LENGTH_LONG).show();
					}
									
				}		
			});
					
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					
					dialog.dismiss();
				}
			});
			return builder.create();
	    }
	    public Dialog addAnotherConfirm(){
	    	
	    	AlertDialog.Builder builder = new AlertDialog.Builder(Ctxt);
			builder.setMessage(R.string.alertDialogTxt_SearchResult_deleteMessage);
			builder.setInverseBackgroundForced(true);
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					//TODO go to newcustomer
									
				}		
			});
					
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					
					dialog.dismiss();
				}
			});
			return builder.create();
	    }
	    private boolean deleteCustomer(long custNum) {
			
	    	boolean deleted = false;
	    	CustomerTable custTable = new CustomerTable(Ctxt);
	    	custTable.open();
	    	deleted = custTable.deleteCustomer(custNum);
			custTable.close();			
			return deleted;
			
			
		}
}
