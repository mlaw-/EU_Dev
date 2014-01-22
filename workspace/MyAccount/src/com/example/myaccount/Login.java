package com.example.myaccount;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.example.myaccount.DataModels.ArrayOfOutage;
import com.example.myaccount.DataModels.Outage;
import com.example.myaccount.DataModels.TokenResponse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {
	Button btnLogin;
	EditText txtUsername; 
	EditText txtPwd; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnLogin = (Button) findViewById(R.id.btnLogin);
        //btnLogin.setOnClickListener(btnLogin_OnClick);
        
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPwd = (EditText) findViewById(R.id.txtPwd);
		txtPwd.setTypeface(Typeface.DEFAULT);
		
        txtUsername.setText("mlaw@eucmail.com");
        txtPwd.setText("Mieke39577974");
        
        TextView tv = (TextView)findViewById(R.id.txtView_LoginSplash);

        Spannable wordtoSpan = new SpannableString("Easton Utilities My Account");        
        wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.eastonYellow)), 0, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 17, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(wordtoSpan);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void btnLogin_OnClick(View view){
    	/*
    	Response resp = ((MyApplication) this.getApplication()).authenticateUser(txtUsername.getText().toString(), 
    			txtPwd.getText().toString());
    	
    	if(!resp.success){
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(resp.message).setTitle(R.string.loginDialog);
    		
    		AlertDialog dialog = builder.create();
    		dialog.show();
    	}
    	else{
    		
        	Intent intent = new Intent(this, MainMenu.class);
        	startActivity(intent);
    	}*/
    	new AuthenticateAsync().execute(new AuthenticateObject(txtUsername.getText().toString(), 
    			txtPwd.getText().toString(), this));
    }
    
    class AuthenticateAsync extends AsyncTask<AuthenticateObject, Void, TokenActivityPair> {

		@Override
		protected TokenActivityPair doInBackground(AuthenticateObject...params ) {
			AuthenticateObject authObj = params[0];
			String username = authObj.username;
			String password = authObj.password;
			Activity activity = authObj.activity;
			
			BufferedReader in = null;
	        String data = null;


	        try
	        {
	            HttpClient client = new DefaultHttpClient();
	            //URI website = new URI("https://server.com:8443/Timesheets/ping");
	            String URL = String.format("%s%s?username=%s&password=%s", 
	            		WebServiceAPI.securityURLPrefix, WebServiceAPI.authenticateURL, username, password);
	            URI website = new URI(URL);
	            HttpGet request = new HttpGet();
	            request.setURI(website);
	            HttpResponse response = client.execute(request);
	            response.getStatusLine().getStatusCode();

	            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	            StringBuffer sb = new StringBuffer("");
	            String l = "";
	            String nl = System.getProperty("line.separator");
	            while ((l = in.readLine()) !=null){
	                sb.append(l + nl);
	            }
	            in.close();
	            data = sb.toString();
	            
	        	Serializer serializer = new Persister();
	        	TokenResponse tokenResponse = serializer.read(TokenResponse.class, new StringReader(data));
	        	
	            
	            return new TokenActivityPair(tokenResponse, activity, username);	
	        } 
	        catch(Exception ex){
	        	System.out.println(ex.getMessage());
	        }
	        
	        return null;
		}
		@Override
	    protected void onPostExecute(TokenActivityPair result) {
			TokenResponse resp = result.tokenResp;
			Activity activity = result.activity;
			
			if(resp.getSuccess()){
				((MyApplication) activity.getApplication()).setGUID(resp.getToken());
				((MyApplication) activity.getApplication()).setUsername(result.username);
				/*
				Intent intent = new Intent(activity, MainMenu.class);
	        	startActivity(intent);*/
				WebServiceAPI.getLocations(activity);
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	    		builder.setMessage(resp.getError()).setTitle(R.string.loginDialog);
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
	    		AlertDialog dialog = builder.create();
				
	    		dialog.show();
			}
	    }
		
		
    }
    private class TokenActivityPair{
		public TokenResponse tokenResp;
		public Activity activity;
		public String username;
		
		public TokenActivityPair(TokenResponse resp, Activity activity, String username){
			this.tokenResp = resp;
			this.activity = activity;
			this.username = username;
		}
	}
    private class AuthenticateObject{
    	public String username;
    	public String password;
    	public Activity activity;
    	
    	public AuthenticateObject(String username, String password, Activity activity){
    		this.username = username;
    		this.password = password;
    		this.activity = activity;
    	}
    }
}
