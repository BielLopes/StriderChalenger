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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private ListView itemsList;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.42.33:8080/api/tasks/pendents";

        itemsList = findViewById(R.id.itens_list);

        items = FileHelper.readData(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1 ,items);



        itemsList.setAdapter(adapter);

        Request request = new Request.Builder()
                .url(url)
                .build();

        final MainActivity activity = this;

        if(items.size() <= 0){

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    adapter.add("TNC!");
                    FileHelper.writeData( items, activity);
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

                                    FileHelper.writeData( items, activity);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    adapter.add("Algo Errado!");
                                    FileHelper.writeData( items, activity);
                                }
                            }
                        });
                    }
                }
            });
        }

        itemsList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        String id = items.get(i).split("-")[0];

        items.remove(i);
        adapter.notifyDataSetChanged();
        FileHelper.writeData( items, this);


        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.42.33:8080/api/tasks/finish/"+id;

        Request request = new Request.Builder()
                .url(url)
                .build();

        final MainActivity activity = this;

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                Toast.makeText(activity, "Erro ao enviar request", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){
                    String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Tarefa finalizada com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

    }
}
