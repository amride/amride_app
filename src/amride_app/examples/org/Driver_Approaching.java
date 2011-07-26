package amride_app.examples.org;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Driver_Approaching extends Activity implements OnClickListener {

	View cancel_button_view;
	Button cancel_button;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_approaching);
        
        cancel_button_view = findViewById(R.id.cancel_ride_button);
        cancel_button = (Button)cancel_button_view;
		cancel_button.setOnClickListener(this);
    }
	public void onClick(View v) 
	{
		if(v.getId()==R.id.cancel_ride_button)
		{
			Intent i = new Intent(this,Cancel_Ride.class);
			startActivity(i);
		}
	}

}
