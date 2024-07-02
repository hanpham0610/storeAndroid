package com.example.store.Controller;

import android.os.AsyncTask;
import android.util.Log;


import com.example.store.Adapter.SanPhamAdapter;
import com.example.store.Modal.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SanPhamController {
    private static final String TAG = SanPhamController.class.getSimpleName();
    private ArrayList<SanPham> items;
    private String apiUrl;
    private SanPhamAdapter adapter;

    public SanPhamController(SanPhamAdapter adapter) {
        this.adapter = adapter;
    }

    public void fetchData() {
        apiUrl = "https://sheets.googleapis.com/v4/spreadsheets/1EArWilSVZmk23GG5AlCoVov-e1nbVdxbNdNTENzdFwQ/values/jsonVcard?key=AIzaSyB6KdsUEG02YYtkYDjFlVA-HrSvs_PULh4";
        new DownloadData().execute();
    }

    private class DownloadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String response = stringBuilder.toString();

                // Process JSON response
                JSONObject jsonObject = new JSONObject(response);
                JSONArray values = jsonObject.getJSONArray("values");
                System.out.println("values "+ values);
                items = new ArrayList<>();
                for (int i = 0; i < values.length(); i++) {
                    JSONArray row = values.getJSONArray(i);

                    if (row.length() >= 6) {

                            SanPham item = new SanPham();

                            item.setDD(row.getString(0));
                            item.setTenSp(row.getString(1));

                            items.add(item);

                    }
                }
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error fetching data: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (items != null && !items.isEmpty()) {
                JSONArray jsonArray = new JSONArray();

                try {
                    for (SanPham item : items) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("imageUrl",  item.getDD());
                        jsonObject.put("caption", item.getTenSp());

                        // Thêm các trường dữ liệu khác tương ứng nếu cần

                        jsonArray.put(jsonObject);
                    }

                    String jsonString = jsonArray.toString();
                    Log.d(TAG, "onPostExecute: JSON data: " + jsonString);
                } catch (JSONException e) {
                    Log.e(TAG, "Error converting data to JSON: " + e.getMessage());
                }

                // Cập nhật adapter với dữ liệu mới
//                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
