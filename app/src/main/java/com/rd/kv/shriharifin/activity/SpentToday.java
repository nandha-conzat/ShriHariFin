package com.rd.kv.shriharifin.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.rd.kv.shriharifin.R;
import com.rd.kv.shriharifin.adapter.CustomBaseAdapter;
import com.rd.kv.shriharifin.bean.SegmentList;
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
public class SpentToday extends AppCompatActivity {

    private ListView listView;
    private String TodaySpentURL = ShriHariConstants.GET_TODAY_SPENT;
    private String jsonResult;
    Context context = SpentToday.this;
    ArrayList<SegmentList> myList = new ArrayList<SegmentList>();
    ProgressDialog loading;
    CustomBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spent_today);
        listView = (ListView) findViewById(R.id.listView1);

        accessWebService();
        adapter = new CustomBaseAdapter(context, myList);

        listView.setAdapter(adapter);
    }

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        loading = ProgressDialog.show(SpentToday.this, "Loading ", "Please wait...", true, true);
        task.execute(new String[]{TodaySpentURL});

    }

    // Async Task to access the web
    private class JsonReadTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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

            ListDrwaer();

            loading.dismiss();
        }
    }// end async task

    // build hash set for list view
    public void ListDrwaer() {
        myList.clear();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
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

                SegmentList lde = new SegmentList();
                lde.setId(Integer.parseInt(id));
                lde.setName(nname);
                lde.setAmount(amount);
                lde.setDate(date);

                // Add this object into the ArrayList myList
                myList.add(lde);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
