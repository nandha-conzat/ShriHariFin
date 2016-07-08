package com.rd.kv.shriharifin.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nandha on 10-04-2016.
 */
public class DataBaseInsert extends AppCompatActivity {

    private String InvestersURL = ShriHariConstants.GET_INVESTERS_URL;
    private String SpentURL = ShriHariConstants.GET_SPENT_URL;
    private String jsonResult, jsonSpend;
    InvestorsDBHelper investorsDBHelper;
    SpendDBHelper spendDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        investorsDBHelper = new InvestorsDBHelper(getApplicationContext());
        spendDBHelper = new SpendDBHelper(getApplicationContext());
    }

    public void accessWebService() {
//        JsonReadTask task = new JsonReadTask();
//        // passes values for the urls string array
//        task.execute(new String[]{InvestersURL});
//
//        JsonSpendTask spendTask = new JsonSpendTask();
//        spendTask.execute(new String[]{SpentURL});
    }

    // Async Task to access the web
    private class JsonReadTask extends AsyncTask<String, Void, String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getApplicationContext(), "Loading ", "Please wait...", true, true);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
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
                // e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            loading.dismiss();
            ListDrwaer();
        }
    }// end async task

    // build hash set for list view
    public void ListDrwaer() {
        List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String id = jsonChildNode.optString("id");
                String name = jsonChildNode.optString("name");
                String number = jsonChildNode.optString("amount");
                String date = jsonChildNode.optString("date");
                String outPut = name + "-" + number + "-" + date;
                employeeList.add(createEmployee("employees", outPut));
                investorsDBHelper.insertInvestors(id, name, number, date);
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, employeeList,
//                android.R.layout.simple_list_item_1,
//                new String[]{"employees"}, new int[]{android.R.id.text1});
//        listView.setAdapter(simpleAdapter);
    }

    // Async Task to access the web
    private class JsonSpendTask extends AsyncTask<String, Void, String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getApplicationContext(), "Loading ", "Please wait...", true, true);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonSpend = inputStreamToString(
                        response.getEntity().getContent()).toString();
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
                // e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            loading.dismiss();
            ListSpendDrwaer();
        }
    }// end async task

    // build hash set for list view
    public void ListSpendDrwaer() {
        List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonSpend);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String id = jsonChildNode.optString("id");
                System.out.println("id :" + id);
                String amount = jsonChildNode.optString("amount");
                System.out.println("amount :" + amount);
                String date = jsonChildNode.optString("date");
                System.out.println("date :" + date);
                String nname = jsonChildNode.optString("nname");
                System.out.println("nname :" + nname);
                String address = jsonChildNode.optString("address");
                System.out.println("address :" + address);
                String phone = jsonChildNode.optString("phone");
                System.out.println("phone :" + phone);
                String email = jsonChildNode.optString("email");
                System.out.println("email :" + email);
                String status = jsonChildNode.optString("status");
                System.out.println("status :" + status);
                String name2 = jsonChildNode.optString("name2");
                System.out.println("name2 :" + name2);
                String interest = jsonChildNode.optString("interest");
                System.out.println("interest :" + interest);
                String days = jsonChildNode.optString("days");
                System.out.println("days :" + days);
                String date9 = jsonChildNode.optString("date9");
                System.out.println("date9 :" + date9);
                String createddate = jsonChildNode.optString("createddate");
                System.out.println("createddate :" + createddate);
                //String outPut = name + "-" + number + "-" + date;
                //employeeList.add(createEmployee("employees", outPut));
                System.out.print("");
                spendDBHelper.insertSpend(id, amount, date, nname, address, phone, email, status, name2, interest, days, createddate);
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, employeeList,
//                android.R.layout.simple_list_item_1,
//                new String[]{"employees"}, new int[]{android.R.id.text1});
//        listView.setAdapter(simpleAdapter);
    }


    private HashMap<String, String> createEmployee(String name, String number) {
        HashMap<String, String> employeeNameNo = new HashMap<String, String>();
        employeeNameNo.put(name, number);
        return employeeNameNo;
    }
}
