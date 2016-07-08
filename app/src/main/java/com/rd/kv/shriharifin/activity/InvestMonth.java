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
public class InvestMonth extends AppCompatActivity {

    private ListView listView;
    private String MonthInvestURL = ShriHariConstants.GET_MONTH_INVESTMENT;
    private String jsonResult;
    Context context = InvestMonth.this;
    ArrayList<SegmentList> myList = new ArrayList<SegmentList>();
    ProgressDialog loading;
    CustomBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invest_month);
        listView = (ListView) findViewById(R.id.listView1);

        accessWebService();
        adapter = new CustomBaseAdapter(context, myList);

        listView.setAdapter(adapter);
    }

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        loading = ProgressDialog.show(InvestMonth.this, "Loading ", "Please wait...", true, true);
        task.execute(new String[]{MonthInvestURL});
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
                String name = jsonChildNode.optString("name");
                String amount = jsonChildNode.optString("amount");
                String date = jsonChildNode.optString("date");

                SegmentList lde = new SegmentList();
                lde.setId(Integer.parseInt(id));
                lde.setName(name);
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
