package com.andriod.tailorassist;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SearchView;



public class Matrix extends Activity {
	
	Context Ctxt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Ctxt = this;
        
        //when user clicks on the view All customer button, show all the customers
        ImageButton ImgBtnViewAll = (ImageButton) findViewById(R.id.viewAll_cust);        
        ImgBtnViewAll.setOnClickListener(new OnClickListener() {
        	
			public void onClick(View v)  {
			
						Bundle bundle = new Bundle();			
						bundle.putString("custName","");	
//						
//						Intent intent = new Intent(Ctxt,SearchCustomerByName.class);				
//						/* sending the customer details to next activity 			 */						
//						intent.putExtras(bundle);			
//						
//						//start the activity						
//						Ctxt.startActivity(intent);		
						Intent intent = new Intent(Ctxt,SearchableActivity.class );
						bundle.putBoolean("listAll",true);	
						intent.putExtras(bundle);
						Ctxt.startActivity(intent);
			}	
		});
//        final ActionBar actionBar = getActionBar();
//        BitmapDrawable background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.bg_topbar));
//        actionBar.setBackgroundDrawable(background);
        com.andriod.tailorassist.screen.Styles.setActionBarStyle(getActionBar(), getResources());
    }

    
    /** Called when the user clicks the new Customer button */
    public void newCustHome(View view) {
    	
    	
    	Intent intent = new Intent(this,NewCustomerHome.class );
    	startActivity(intent);
    	
    }
    
	 public void findCustomer(View view) {
	        
//	    	Intent intent = new Intent(this,FindCustomer.class );
//	    	startActivity(intent);	    	
		 
			Intent intent = new Intent(this,SearchableActivity.class );
	    	startActivity(intent);	
	  }
	 public boolean onCreateOptionsMenu(Menu menu) {
		    // Inflate the options menu from XML
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.options_menu, menu);

		    
		    // Get the SearchView and set the searchable configuration
		    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		    SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//		    Log.d("TEST", "ComponentName : "+new ComponentName(getApplicationContext(), "com.andriod.tailorassist.SearchableActivity"));
		    searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), "com.andriod.tailorassist.SearchableActivity")));
		    searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
		    
		    return true;
		}
}
