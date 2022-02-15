package com.example.req;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);
        Button button = findViewById(R.id.btn_parse_json);

        requestQueue = VolleySingleton.getInstance(this).getmRequestQueue();
//                Volley.newRequestQueue(this);

        button.setOnClickListener(view -> jsonParse());

    }

    private void jsonParse() {
        String url = "https://anshumemorial.in/experiment/data.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i=0; i < 5; i++){
                            JSONObject data = jsonArray.getJSONObject(i);

                            int id = data.getInt("id");
                            String title = data.getString("title");
                            String body = data.getString("body");

                            textView.append(id +", "+ title +", "+ body +"\n\n");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, Throwable::printStackTrace);

        requestQueue.add(jsonObjectRequest);
    }
}