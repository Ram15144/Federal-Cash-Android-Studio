package course.examples.Services.KeyService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import course.examples.Services.KeyCommon.KeyGenerator;

public class KeyGeneratorImpl extends Service {

	// Set of already assigned IDs
	// Note: These keys are not guaranteed to be unique if the Service is killed
	// and restarted.

	static String monthly_cash, daily_cash, yearly_avg;
	int year, year1, month, day, no_of_days;

	public static String updateStatus="Service not yet bound!";

	// Implement the Stub for this Object
	private final KeyGenerator.Stub mBinder = new KeyGenerator.Stub() {
		@Override
		public String getMonthlyCash(int a) throws RemoteException {

			year=a;
			Runnable api1_Runnable = new Runnable() {
				@Override
				public void run() {

					try {
						//Creating the URL with the year sent by the client to get the monthly_cash
						URL url = new URL("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT%20%22table%22,%20%22open_mo%22%20FROM%20t1%20WHERE%20(%22year%22%20==%20%27"+year+"%27%20)%20LIMIT%2010");
						HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
						InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
						StringBuilder builder = new StringBuilder();
						String inputString;
						while ((inputString = bufferedReader.readLine()) != null) {
							builder.append(inputString);
						}
						//Get the data from the website in data as a string
						String data = builder.toString();
						monthly_cash= data;
						//Disconnect from the URL
						urlConnection.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			};
			Thread MCThread= new Thread((api1_Runnable));
			MCThread.start();
			return monthly_cash;
		}

		@Override
		public String getDailyCash(int x, int y, int z, int w) throws RemoteException {
			day=x;
			month=y;
			year1=z;
			no_of_days=w;

			Runnable api2_Runnable = new Runnable() {
				@Override
				public void run() {

					try {
						//Creating the URL with the date and number of working days sent by the client to get the daily_cash
						URL url = new URL("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=select%20open_today%20from" +
								"%20t1%20where%20account=%22Total%20Operating%20Balance%22%20and%20date%20%3E%20%27" + year1 + "-" + month + "-" + day + "%27%20limit%20" + no_of_days + ";");
						HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
						InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
						StringBuilder builder = new StringBuilder();
						String inputString;
						while ((inputString = bufferedReader.readLine()) != null) {
							builder.append(inputString);
						}
						String data = builder.toString();
						daily_cash= data;
						urlConnection.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			};
			Thread DCThread= new Thread((api2_Runnable));
			DCThread.start();

			return daily_cash;

		}

		@Override
		public String getYearlyAverage(int i) throws RemoteException{




			Runnable api3_Runnable = new Runnable() {
				@Override
				public void run() {

					try {
						//Creating the URL with the year sent by the client to get the yearly_avg_cash
						URL url = new URL("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=select%20avg(open_today)%20from%20t1%20where%20date%20%3E%20%27" + year + "-01-01%27AND%20date%20%3C%20%27" + year + "-12-31%27;");
						HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
						InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
						StringBuilder builder = new StringBuilder();
						String inputString;
						while ((inputString = bufferedReader.readLine()) != null) {
							builder.append(inputString);

						}
						String data = builder.toString();
						yearly_avg= data;
						urlConnection.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			};
			Thread YAThread= new Thread((api3_Runnable));
			YAThread.start();

			return yearly_avg;

		}

		@Override
		public String status(String x) throws RemoteException
		{
			updateStatus=x;
			return updateStatus;

		}
	};

	// Return the Stub defined above
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
}
