package course.examples.Services.KeyClient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import course.examples.Services.KeyCommon.KeyGenerator;

public class FedCash extends Activity {

    //Strings to pass as extras in intent to DisplayData.java
    public static final String INTENT_STRING="INTENT_STRING";
    public static final String INTENT_DATA="INTENT_DATA";
    public static final String MONTHLY_CASH="MONTHLY_CASH";
    public static final String DAILY_CASH="DAILY_CASH";
    public static final String YEARLY_AVERAGE="YEARLY_AVERAGE";

    protected static final String TAG = "ServiceUser";

    Button monthly_cash,daily_cash,yearly_avg, unbind;
    EditText year,date,days;

    //Android interface definition language Service
    private KeyGenerator mKeyGeneratorService;
    private boolean mIsBound = false;
    String status;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthly_cash=(Button)findViewById(R.id.button);
        daily_cash=(Button)findViewById(R.id.button2);
        yearly_avg=(Button)findViewById(R.id.button3);
        unbind=(Button)findViewById(R.id.button4);

        year=(EditText)findViewById(R.id.editText);
        date=(EditText)findViewById(R.id.editText2);
        days=(EditText)findViewById(R.id.editText3);

        // Get the monthly cash for the selected year and send it as a string to display to DisplayData.java
       monthly_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String y=year.getText().toString();
                if(validate(y,"",1)) {

                    if (mIsBound)
                        try {
                            //Get the monthly cash as a string from the service
                            final String z = mKeyGeneratorService.getMonthlyCash(Integer.parseInt(y));

                            //Make JSON array and get the required columns from the string
                            String result="";
                            JSONArray j=new JSONArray(z);
                            for (int i = 0; i < j.length(); i++) {
                                JSONObject jsonobject = j.getJSONObject(i);
                                String name = jsonobject.getString("open_mo");
                                result+=name+" ";
                            }

                            //Toast.makeText(FedCash.this,result,Toast.LENGTH_LONG).show();

                            //Send the intent with the parsed data to display in DisplayData.java
                            Intent i = new Intent(FedCash.this, DisplayData.class);
                            i.putExtra(INTENT_STRING,MONTHLY_CASH);
                            i.putExtra(INTENT_DATA,result);
                            startActivity(i);


                        } catch (Exception e) {

                        }
                }
            }
        });

        // Get the daily cash for the input date and days and send it as a string to display to DisplayData.java
        daily_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d=date.getText().toString();
                String nd=days.getText().toString();

                if(validate(d,nd,2)) {

                    String [] dateComponents = d.split("/");
                    String month = dateComponents[0];
                    String day = dateComponents[1];
                    String year = dateComponents[2];

                    final int da=Integer.parseInt(day);
                    final int mo=Integer.parseInt(month);
                    final int ye=Integer.parseInt(year);
                    final int no_of_days=Integer.parseInt(nd);
                    if (mIsBound)
                        try {

                            //Get the daily cash as a string from the service
                            final String z = mKeyGeneratorService.getDailyCash(da,mo,ye,no_of_days);

                            //Make JSON array and get the required columns from the string
                            JSONArray j=new JSONArray(z);
                            String result="";
                            for (int i = 0; i < j.length(); i++) {
                                JSONObject jsonobject = j.getJSONObject(i);
                                String name = jsonobject.getString("open_today");
                                result+=name+" ";
                            }
                            //Toast.makeText(FedCash.this,result,Toast.LENGTH_LONG).show();

                            //Send the intent with the parsed data to display in DisplayData.java
                            Intent i = new Intent(FedCash.this, DisplayData.class);
                            i.putExtra(INTENT_STRING,DAILY_CASH);
                            i.putExtra(INTENT_DATA,result);
                            startActivity(i);

                        } catch (Exception e) {

                        }
                }
            }
        });

        // Get the year average for the selected year and send it as a string to display to DisplayData.java
        yearly_avg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String y=year.getText().toString();
                if(validate(y,"",1)) {
                    if (mIsBound)
                        try {
                            //Get the yearly average as a string
                            final String z = mKeyGeneratorService.getYearlyAverage(Integer.parseInt(y));

                            //Make JSON array and get the required columns from the string
                            JSONArray j= new JSONArray(z);
                            JSONObject jsonobject = j.getJSONObject(0);
                            String name = jsonobject.getString("avg(open_today)");
                            //Toast.makeText(FedCash.this,name,Toast.LENGTH_LONG).show();

                            //Send the intent with the parsed data to display in DisplayData.java
                            Intent i = new Intent(FedCash.this, DisplayData.class);
                            i.putExtra(INTENT_STRING,YEARLY_AVERAGE);
                            i.putExtra(INTENT_DATA,name);
                            startActivity(i);

                        } catch (Exception e) {

                        }
                }
            }
        });

        // Unbind the service on pressing this button and send status to TreasuryServ to update the Status
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mIsBound) {
                    status = "Service is unbound!";

                    try {
                        String j = mKeyGeneratorService.status(status);
                    } catch (Exception e) {

                    }
                    try {
                        unbindService(mConnection);
                    }
                    catch(IllegalArgumentException e)
                    {
                        Toast.makeText(FedCash.this,"IllegalArgumentException caught!",Toast.LENGTH_SHORT);
                    }

                    Toast.makeText(getApplicationContext(), "service is unbound", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    //Validation of the input
    private boolean validate(String str1, String str2, int cases)
    {
        switch(cases)
        {
            case 1: if(str1=="")
                        Toast.makeText(FedCash.this,"Please enter a year between 2005 and 2017", Toast.LENGTH_LONG).show();
                    else
                    {
                        try
                        {
                            Integer.parseInt(str1.trim());
                        }
                        catch(NumberFormatException ex)
                        {
                            Toast.makeText(FedCash.this,"Please enter a year number between 2015 and 2017", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        if(Integer.parseInt(str1)<2006 || Integer.parseInt(str1)>2016)
                        {
                            Toast.makeText(FedCash.this,"Please enter a year between 2005 and 2017", Toast.LENGTH_SHORT).show();
                        }
                        else
                            return true;
                    }
            case 2: if(str1=="" || str2=="")
                        Toast.makeText(FedCash.this,"Please enter both the date and the working days", Toast.LENGTH_SHORT).show();
                    else
                    {
                        try
                        {
                            int a= Integer.parseInt(str2.trim());
                            if(a<=5 || a>=25)
                            {
                                Toast.makeText(FedCash.this,"Please enter working days between 5 and 25", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(NumberFormatException ex)
                        {
                            Toast.makeText(FedCash.this,"Please enter a valid number of working days", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
                        sdfrmt.setLenient(false);

                        try
                        {
                            Date check=sdfrmt.parse(str1);
                            System.out.println(str1+" is valid date format");

                            Date date1 = sdfrmt.parse("01/01/2006");
                            Date date2 = sdfrmt.parse("12/31/2016");
                            if(date1.compareTo(check)>0 || date2.compareTo(check)<0)
                            {
                                Toast.makeText(FedCash.this,"Enter a date between years 2005 and 2017", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }
	                    /* Date format is invalid */
                        catch (ParseException e)
                        {
                            Toast.makeText(FedCash.this,"Please enter a valid date", Toast.LENGTH_LONG).show();
                            System.out.println(str1+" is Invalid Date format");
                            return false;
                        }
                        return true;
                    }
            default: return false;
        }
    }

    // Bind to KeyGenerator Service
    @Override
    protected void onResume() {
        super.onResume();

        if (!mIsBound) {
            boolean b = false;
            Intent i = new Intent(KeyGenerator.class.getName());
            // Must make intent explicit or lower target API level to 19.
            ResolveInfo info = getPackageManager().resolveService(i, PackageManager.GET_META_DATA);
            //ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

            b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);
            status="The service is bound!";
            Toast.makeText(FedCash.this,status,Toast.LENGTH_SHORT).show();
            try {
                String j = mKeyGeneratorService.status(status);
            }
            catch (Exception e)
            {

            }
            if (b) {
                Log.i(TAG, " bindService() succeeded!");
            } else {
                Log.i(TAG, " bindService() failed!");
            }

        }
    }


    //Create the connection to the service and update the status in the service
  private final ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder iservice) {

            mKeyGeneratorService = KeyGenerator.Stub.asInterface(iservice);

            mIsBound = true;
            status = "Service bound but idle!";
            Toast.makeText(FedCash.this,status,Toast.LENGTH_SHORT).show();
            try
            {
                String j = mKeyGeneratorService.status(status);
            }
            catch(Exception e)
            {

            }
        }

        // Service disconnected
        public void onServiceDisconnected(ComponentName className) {

            mKeyGeneratorService = null;

            mIsBound = false;

        }
    };
}
