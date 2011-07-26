package amride_app.examples.org;


import java.io.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;
import org.apache.http.client.HttpClient;

import org.apache.http.impl.client.DefaultHttpClient;
import java.util.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.w3c.dom.*;
import javax.xml.parsers.*;


import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.os.Bundle;

public class Login_SignUp_Screen extends Activity implements OnTouchListener {

	  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup_screen);
        
    	View login_button = findViewById(R.id.login_button);
		login_button.setOnTouchListener(this);
		
		View signup_button = findViewById(R.id.signup_button);
		signup_button.setOnTouchListener(this);
    }
	
	public boolean onTouch(View v, MotionEvent event) {
	
		switch (v.getId()) 
		{
			case R.id.login_button:
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://202.164.49.146/clients/amride_mob/callWebService.php");
				try 
				{
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					EditText username_login_field = (EditText)findViewById(R.id.username_login_field);
					String username = username_login_field.getText().toString();
					
					EditText password_login_field = (EditText)findViewById(R.id.password_login_field);
					String password = password_login_field.getText().toString();
		        
					nameValuePairs.add(new BasicNameValuePair("method","login"));
					nameValuePairs.add(new BasicNameValuePair("param[username]",username));
					nameValuePairs.add(new BasicNameValuePair("param[password]",password));
		        
		        
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        
					// Execute HTTP Post Request
		       
					HttpResponse response = httpclient.execute(httppost);
		  		//	EditText status_field = (EditText)findViewById(R.id.status_field);        
					
		  			String s = EntityUtils.toString(response.getEntity());
		  			//status_field.setText(s);
		  			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		  			DocumentBuilder builder = factory.newDocumentBuilder();
		  			InputSource is = new InputSource(new StringReader(s));
		  			NodeList l =  builder.parse(is).getElementsByTagName("responseCode");
		  			if(l.item(0).getTextContent().equals("0"))
		  			{
		  				AlertDialog.Builder build=new AlertDialog.Builder(this);
		  				build.setTitle("Alert");
		  				build.setMessage("Wrong Username/Password");
		  				build.setIcon(android.R.drawable.ic_dialog_alert);
		  				build.setPositiveButton("OK",new OnClickListener() 
		  				{
		  					public void onClick(DialogInterface dialog, int which) 
		  					{}
		  				});
		  				build.show(); 
		  			}
		  			else
		  			{
		  				Intent i = new Intent(this,Select_Src_Dest.class);
		  				startActivity(i);
		  			}
		        	  
		      	}
				catch(Exception e)
				{}
		//Intent i = new Intent(this, Login_SignUp_Screen.class);
		//startActivity(i);
		break;
		}
		return false;
	}
}
