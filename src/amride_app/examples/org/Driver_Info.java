package amride_app.examples.org;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class Driver_Info extends Activity implements OnClickListener {

	
	private View call_driver_button_view;
	private Button call_driver_button;
	
	private View cancel_button_view;
	private Button cancel_button;
	
	
	   /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_info);
    	
        
			TextView cost_field =(TextView)findViewById(R.id.cost_field);
			cost_field.setText(getIntent().getStringExtra("cost"));
			
			TextView name_field =(TextView)findViewById(R.id.driver_field);
			name_field.setText(getIntent().getStringExtra("first_name")+" "+getIntent().getStringExtra("last_name"));
			
			TextView app_time_field =(TextView)findViewById(R.id.app_time_field);
			app_time_field.setText(getIntent().getStringExtra("app_time"));
			
			TextView rating_field =(TextView)findViewById(R.id.rating_field);
			rating_field.setText(getIntent().getStringExtra("rating"));
			
			call_driver_button_view = findViewById(R.id.call_driver_button);
			call_driver_button = (Button)call_driver_button_view;
			call_driver_button.setOnClickListener(this);
			
			cancel_button_view = findViewById(R.id.cancel_button);
			cancel_button = (Button)cancel_button_view;
			cancel_button.setOnClickListener(this);
	}
	
	public void onClick(View v) 
	{
		if(v.getId()==R.id.call_driver_button)
		{
			Intent i = new Intent(this,Driver_Approaching.class);
			startActivity(i);
		}
		else if(v.getId() == R.id.cancel_button)
		{
			
		}
	}

}
