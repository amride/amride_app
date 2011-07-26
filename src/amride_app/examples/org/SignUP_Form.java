package amride_app.examples.org;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.os.Bundle;

public class SignUP_Form extends Activity implements OnTouchListener {

	  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form);
     
		View signup_enterData_button = findViewById(R.id.signup_enterData_button);
		signup_enterData_button.setOnTouchListener(this);
    }
	
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.signup_enterData_button:
		Intent i = new Intent(this, Login_SignUp_Screen.class);
		startActivity(i);
		break;
		}
		return false;
	}
}
