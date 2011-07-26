package amride_app.examples.org;


import com.google.android.maps.MyLocationOverlay;
//import javax.xml.parsers.*;
import android.widget.Button;
import android.widget.EditText;
import java.io.*;

import android.location.Address;

import java.util.ArrayList;
import java.util.Locale;
import android.location.Geocoder;
import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import java.util.List;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;
import org.apache.http.client.HttpClient;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.w3c.dom.*;

import javax.xml.parsers.*;


public class Select_Src_Dest extends MapActivity implements android.view.View.OnClickListener{
	
	public double src_longitude;
	public double src_latitude;
	public double dest_longitude;
	public double dest_latitude;
	public boolean src_selected;
	
	Button submit_source_button;
	View submit_source_button_view;
	
	
	MapView mapView; 
	MapController mc;
	GeoPoint p;
	
	private MyLocationOverlay myLocOverlay;
	
	   /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_src_dest);
        MapView mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.add(mapOverlay);
        
        View submit_source_button_view = findViewById(R.id.Submit_Source_Button);
		submit_source_button = (Button)submit_source_button_view;
		submit_source_button.setOnClickListener(this);
        
        src_selected = false;
        
        myLocOverlay = new MyLocationOverlay(this, mapView);
		if(myLocOverlay.enableMyLocation()==true)
		{
			mapView.getOverlays().add(myLocOverlay);
			p = myLocOverlay.getMyLocation();
			src_latitude = p.getLatitudeE6() / 1E6;
	    	src_longitude = p.getLongitudeE6() / 1E6;
	    	
	    	try 
    		{
	    		Geocoder geoCoder = new Geocoder( getBaseContext(), Locale.ENGLISH);
    			List<Address> addresses = geoCoder.getFromLocation(src_latitude,src_longitude,1);
    			String add = "";
    			if (addresses.size() > 0) 
    			{
    				for(int i=0; i<addresses.get(0).getMaxAddressLineIndex(); i++)
    					add += addresses.get(0).getAddressLine(i) + ",";
    			}
    			EditText status_field = (EditText)findViewById(R.id.Geolocation);
    			status_field.setText(add);
    		}
    		catch (IOException e) 
    		{ 
    			EditText status_field = (EditText)findViewById(R.id.Geolocation);
    			status_field.setText("IOEXCEPTION Src");  
    		}	
		}					
   }
  
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    public void onClick(View v) 
    {	
    	if(v.getId()==R.id.Submit_Source_Button)
    	{
    		submit_source_button_view = findViewById(R.id.Submit_Source_Button);
    		submit_source_button = (Button)submit_source_button_view;
    		if(submit_source_button.getText().equals("Pick Me Here"))
    		{	
    			submit_source_button.setText("Drop Me Here");
    			src_selected = true; 
    		}
    		else if(submit_source_button.getText().equals("Drop Me Here"))
    		{
    			HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://202.164.49.146/clients/amride_mob/callWebService.php");
				try 
				{
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
							        
					nameValuePairs.add(new BasicNameValuePair("method","getRideInfo"));
					nameValuePairs.add(new BasicNameValuePair("param[sourceLat]",src_latitude+""));
					nameValuePairs.add(new BasicNameValuePair("param[sourceLong]",src_longitude+""));
					nameValuePairs.add(new BasicNameValuePair("param[destLat]",dest_latitude+""));
					nameValuePairs.add(new BasicNameValuePair("param[destLong]",dest_longitude+""));
					
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        
					// Execute HTTP Post Request
		       
					HttpResponse response = httpclient.execute(httppost);
					String s = EntityUtils.toString(response.getEntity());
		  
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		  			DocumentBuilder builder = factory.newDocumentBuilder();
		  			InputSource is = new InputSource(new StringReader(s));
		  			Document d =  builder.parse(is);
		  			
		  			NodeList l = d.getElementsByTagName("responseCode");
		  			
		  			if(l.item(0).getTextContent().equals("1"))
		  			{
		   				Intent i = new Intent(this,Driver_Info.class);
		   				
		  				NodeList first_name = d.getElementsByTagName("first_name");
		  				NodeList last_name = d.getElementsByTagName("last_name");
		  				NodeList app_time = d.getElementsByTagName("approx_time");		 
		  				NodeList rating = d.getElementsByTagName("rating");
		  				NodeList cost = d.getElementsByTagName("cost");
		  				
		  				
		  				i.putExtra("first_name",first_name.item(0).getTextContent());
		  				i.putExtra("last_name",last_name.item(0).getTextContent());
		  				i.putExtra("app_time",app_time.item(0).getTextContent());
		  				i.putExtra("rating",rating.item(0).getTextContent());
		  				i.putExtra("cost",cost.item(0).getTextContent());
		  				startActivity(i);
		  			}
		  			else
		  			{
		  				AlertDialog.Builder build=new AlertDialog.Builder(this);
		  				build.setTitle("Alert");
		  				build.setMessage("Driver Not Available For"+"\n"+" This Source-Destination Pair");
		  				build.setIcon(android.R.drawable.ic_dialog_alert);
		  				build.setPositiveButton("OK",new OnClickListener() 
		  				{
		  					public void onClick(DialogInterface dialog, int which) 
		  					{}
		  				});
		  				build.show();
		  			}
				}
				catch(Exception e)
				{}
    		}
    	}  
    } 
    
    class MapOverlay extends com.google.android.maps.Overlay
    {	
        @Override
        public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                    
            return true;
        }
        
        public boolean onTouchEvent(MotionEvent event, MapView mapView) 
        {   
            if (event.getAction() == 1) 
            {
            	if(src_selected == false)
            	{
            		GeoPoint p = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());	
            	
            		Toast.makeText(getBaseContext(), p.getLatitudeE6() / 1E6 + "," + p.getLongitudeE6() /1E6 , Toast.LENGTH_SHORT).show();
            		Geocoder geoCoder = new Geocoder( getBaseContext(), Locale.ENGLISH);
            	
            		src_latitude = p.getLatitudeE6() / 1E6;
            		src_longitude = p.getLongitudeE6() / 1E6;      	
            	
            		try 
            		{          	
            			List<Address> addresses = geoCoder.getFromLocation(src_latitude,src_longitude,1);
            			String add = "";
            			if (addresses.size() > 0) 
            			{
            				
            				for(int i=1; i<addresses.get(0).getMaxAddressLineIndex()-1; i++)
            					add += addresses.get(0).getAddressLine(i) + "\n";
            				
            				add += addresses.get(0).getAddressLine(addresses.get(0).getMaxAddressLineIndex()-1);
            			}
            			
            			EditText status_field = (EditText)findViewById(R.id.Geolocation);
            			status_field.setText(add);
            		}
            		catch (IOException e) 
            		{ 
            			EditText status_field = (EditText)findViewById(R.id.Geolocation);
            			status_field.setText("IOEXCEPTION Src");  
            		}
            	}
            	else if(src_selected == true)
            	{
            		GeoPoint p = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());	
            		//Toast.makeText(getBaseContext(), p.getLatitudeE6() / 1E6 + "," + p.getLongitudeE6() /1E6 , Toast.LENGTH_SHORT).show();
            		Geocoder geoCoder = new Geocoder( getBaseContext(), Locale.ENGLISH);
            	
            		dest_latitude = p.getLatitudeE6() / 1E6;
            		dest_longitude = p.getLongitudeE6() / 1E6;      	
            	
            		try 
            		{          	
            			List<Address> addresses = geoCoder.getFromLocation(dest_latitude,dest_longitude,1);
            			String add = "";
            			if (addresses.size() > 0) 
            			{
            				for(int i=1; i<addresses.get(0).getMaxAddressLineIndex()-1; i++)
            					add += addresses.get(0).getAddressLine(i) + "\n";
            				
            				add += addresses.get(0).getAddressLine(addresses.get(0).getMaxAddressLineIndex()-1);
            			}
              			EditText status_field = (EditText)findViewById(R.id.Geolocation);
            			status_field.setText(add);
            		}
            		catch (IOException e) 
            		{ 
            			EditText status_field = (EditText)findViewById(R.id.Geolocation);
            			status_field.setText("IOEXCEPTION Dest");  
            		}
            	}
            		return true;
            }
            else
            {
            	return false;
            }           	
        }
    }       
}


