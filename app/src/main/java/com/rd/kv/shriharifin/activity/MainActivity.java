package com.rd.kv.shriharifin.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rd.kv.shriharifin.R;
import com.rd.kv.shriharifin.dbhelper.InvestorsDBHelper;
import com.rd.kv.shriharifin.dbhelper.SpendDBHelper;
import com.rd.kv.shriharifin.utils.ShriHariConstants;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static int NETWORK_STATE = 0;
    public static final int CONNECTED = 1;
    public static final int NOT_CONNECTED = 2;
    private String jsonYearTotal;
    private String YearTotalInvest, MonthTotalInvest, DayTotalInvest, YearTotalSpent, MonthTotalSpent, DayTotalSpent;
    private String TopInsName, TopInsAmount, TopSptName, TopSptAmount, FullInsList, FullSptList;
    private String DashBoardURL = ShriHariConstants.GET_DASHBOARD;
    private TextView txtTotalYearAmountInvest, txtTotalMonthAmountInvest, txtTotalDayAmountInvest, txtTop10Invest, txtFullInvestList;
    private TextView txtTotalYearAmountSpent, txtTotalMonthAmountSpent, txtTotalDayAmountSpent, txtTop10Spent, txtFullSpentList;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FindViewById();
        this.registerReceiver(this.mConnReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
        haveNetworkConnection();

        accessWebService();

        txtTotalDayAmountInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InvestToday.class);
                startActivity(i);
            }
        });

        txtTotalMonthAmountInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InvestMonth.class);
                startActivity(i);
            }
        });

        txtTotalYearAmountInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InvestYear.class);
                startActivity(i);
            }
        });

        txtTop10Invest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InvestTopTen.class);
                startActivity(i);
            }
        });

        txtFullInvestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InvestFullList.class);
                startActivity(i);
            }
        });

        txtTotalDayAmountSpent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SpentToday.class);
                startActivity(i);
            }
        });

        txtTotalMonthAmountSpent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SpentMonth.class);
                startActivity(i);
            }
        });

        txtTotalYearAmountSpent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SpentYear.class);
                startActivity(i);
            }
        });

        txtTop10Spent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SpentTopTen.class);
                startActivity(i);
            }
        });

        txtFullSpentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SpentFullList.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_page: {
                accessWebService();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // Async Task to access the web
    private class JsonYearOfTotalTask extends AsyncTask<String, Void, String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(MainActivity.this, "Loading ", "Please wait...", true, true);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonYearTotal = inputStreamToString(
                        response.getEntity().getContent()).toString();
                try {
                    JSONObject jsonResponse = new JSONObject(jsonYearTotal);
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("data");

                    for (int i = 0; i < jsonMainNode.length(); i++) {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        YearTotalInvest = jsonChildNode.optString("yeartotalinvest");
                        System.out.println("id :" + YearTotalInvest);
                        MonthTotalInvest = jsonChildNode.optString("monthtotalinvest");
                        System.out.println("id :" + MonthTotalInvest);
                        DayTotalInvest = jsonChildNode.optString("daytotalinvest");
                        System.out.println("id :" + DayTotalInvest);
                        YearTotalSpent = jsonChildNode.optString("yeartotalspent");
                        System.out.println("id :" + YearTotalSpent);
                        MonthTotalSpent = jsonChildNode.optString("monthtotalspent");
                        System.out.println("id :" + MonthTotalSpent);
                        DayTotalSpent = jsonChildNode.optString("daytotalspent");
                        System.out.println("id :" + DayTotalSpent);
                        TopInsName = jsonChildNode.optString("topinsname");
                        System.out.println("id :" + TopInsName);
                        TopInsAmount = jsonChildNode.optString("topinsamount");
                        System.out.println("id :" + TopInsAmount);
                        TopSptName = jsonChildNode.optString("topsptname");
                        System.out.println("id :" + TopSptName);
                        TopSptAmount = jsonChildNode.optString("topsptamount");
                        System.out.println("id :" + TopSptAmount);
                        FullInsList = jsonChildNode.optString("totalins");
                        System.out.println("id :" + FullInsList);
                        FullSptList = jsonChildNode.optString("TotalSpt");
                        System.out.println("id :" + FullSptList);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            loading.dismiss();
            txtTotalYearAmountInvest.setTypeface(tf);
            txtTotalYearAmountInvest.setText("`" + YearTotalInvest);
            txtTotalMonthAmountInvest.setTypeface(tf);
            txtTotalMonthAmountInvest.setText("`" + MonthTotalInvest);
            txtTotalDayAmountInvest.setTypeface(tf);
            txtTotalDayAmountInvest.setText("`" + DayTotalInvest);
            txtTotalYearAmountSpent.setTypeface(tf);
            txtTotalYearAmountSpent.setText("`" + YearTotalSpent);
            txtTotalMonthAmountSpent.setTypeface(tf);
            txtTotalMonthAmountSpent.setText("`" + MonthTotalSpent);
            txtTotalDayAmountSpent.setTypeface(tf);
            txtTotalDayAmountSpent.setText("`" + DayTotalSpent);
            txtTop10Invest.setTypeface(tf);
            txtTop10Invest.setText(TopInsName + " : " + "`" + TopInsAmount);
            txtTop10Spent.setTypeface(tf);
            txtTop10Spent.setText(TopSptName + " : " + "`" + TopSptAmount);
            txtFullInvestList.setTypeface(tf);
            txtFullInvestList.setText("No of investment " + FullInsList);
            txtFullSpentList.setTypeface(tf);
            txtFullSpentList.setText("No of spent: " + FullSptList);
        }
    }// end async task

    public void accessWebService() {
        JsonYearOfTotalTask yearOfTotalTask = new JsonYearOfTotalTask();
        yearOfTotalTask.execute(new String[]{DashBoardURL});
    }

    //Check network connection
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            NetworkInfo currentNetworkInfo = (NetworkInfo) intent
                    .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

            if (currentNetworkInfo.isConnected()) {
                NETWORK_STATE = CONNECTED;
            } else {
                NETWORK_STATE = NOT_CONNECTED;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Enable Internet Connection")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }; //end internet connection

    //Variable assign
    private void FindViewById() {
        txtTotalYearAmountInvest = (TextView) findViewById(R.id.yearAmountInvest);
        txtTotalMonthAmountInvest = (TextView) findViewById(R.id.monthAmountInvest);
        txtTotalDayAmountInvest = (TextView) findViewById(R.id.dayAmountInvest);
        txtTotalYearAmountSpent = (TextView) findViewById(R.id.yearAmountSpent);
        txtTotalMonthAmountSpent = (TextView) findViewById(R.id.monthAmountSpent);
        txtTotalDayAmountSpent = (TextView) findViewById(R.id.dayAmountSpent);
        txtTop10Invest = (TextView) findViewById(R.id.top10Invest);
        txtTop10Spent = (TextView) findViewById(R.id.top10Spent);
        txtFullInvestList = (TextView) findViewById(R.id.fullListInvest);
        txtFullSpentList = (TextView) findViewById(R.id.fullListSpent);
        tf = Typeface.createFromAsset(getAssets(), "fonts/Rupee_Foradian.ttf");

    }
}
