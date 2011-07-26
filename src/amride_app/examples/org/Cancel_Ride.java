package amride_app.examples.org;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Cancel_Ride extends Activity implements OnClickListener {

	View sure_cancel_button_view;
	Button sure_cancel_button;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_ride);
        
        sure_cancel_button_view = findViewById(R.id.sure_cancel_button);
        sure_cancel_button = (Button)sure_cancel_button_view;
		sure_cancel_button.setOnClickListener(this);
    }
	public void onClick(View v) 
	{
		if(v.getId()==R.id.sure_cancel_button)
		{
			/*Intent i = new Intent(this,Cancel_Ride.class);
			startActivity(i);*/
			TextView cost_on_cancel_field = (TextView)findViewById(R.id.cost_on_cancel_field);
			cost_on_cancel_field.setText("Ride Cancelled");
			sure_cancel_button.setEnabled(false);
		}
	}
}
