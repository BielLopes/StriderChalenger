package com.example.striderandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private ListView itemsList;

    private Timer mTimer = new Timer();

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    private final String url = "http://192.168.42.33:8080/api/tasks";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private TimerTask mTask = new TimerTask() {
        @Override
        public void run() {


            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url + "/pendents")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.add("TNC!");
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if(response.isSuccessful()){
                        final String myResponse = response.body().string();

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                items.clear();
                                adapter.notifyDataSetChanged();

                                String description = null;
                                JSONArray pendents = null;

                                try {

                                    pendents = new JSONArray(myResponse);

                                    for (int i = 0; i < pendents.length(); i++){
                                        description = pendents.getJSONObject(i).getInt("id") + "- " +  pendents.getJSONObject(i).getString("description");
                                        adapter.add(description);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    adapter.add("Algo Errado!");
                                }
                            }
                        });
                    }
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient();

        itemsList = findViewById(R.id.itens_list);

        items = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1 ,items);
        itemsList.setAdapter(adapter);

        Request request = new Request.Builder()
                .url(url + "/pendents")
                .build();

        mTimer.scheduleAtFixedRate(mTask, 1000, 1000);



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.add("TNC!");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){
                    final String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String description = null;
                            JSONArray pendents = null;

                            try {

                                pendents = new JSONArray(myResponse);

                                for (int i = 0; i < pendents.length(); i++){
                                  description = pendents.getJSONObject(i).getInt("id") + "- " +  pendents.getJSONObject(i).getString("description");
                                  adapter.add(description);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                adapter.add("Algo Errado!");
                            }
                        }
                    });
                }
            }
        });


        itemsList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        String id = items.get(i).split("-")[0];

        items.remove(i);
        adapter.notifyDataSetChanged();


        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("id", id)
                .build();

        Request request = new Request.Builder()
                .url(url + "/finish")
                .post(formBody)
                .build();

        /*
        try {
            client.newCall(request).execute();
            Toast.makeText(MainActivity.this, "Tarefa finalizada com sucesso", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Erro ao enviar request", Toast.LENGTH_SHORT).show();
        }*/

        //Toast.makeText(MainActivity.this, "Tentando 123...", Toast.LENGTH_SHORT).show();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Erro no request!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                if(response.isSuccessful()){

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(MainActivity.this, "Tarefa finalizada!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


    }
}
