package com.example.digifestmethod.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digifestmethod.Api;
import com.example.digifestmethod.R;
import com.example.digifestmethod.models.Query;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedActivity extends AppCompatActivity {

    public static final String TAG = "FeedAct";

    private RecyclerView rvFeed;
    private ArrayList<Query> queries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        getQueries();
        Log.d(TAG, "onCreate: " + queries);

    }

    private void getQueries() {
        String urlToPost = getString(R.string.url);
        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(urlToPost)
                .build();

        Api api = retrofit.create(Api.class);
        Log.d(TAG, "getQueries: getting queries");
        api.listQueries().enqueue(new Callback<ArrayList<Query>>() {
            @Override
            public void onResponse(Call<ArrayList<Query>> call, Response<ArrayList<Query>> response) {
                if (response.body() != null) {

                    Log.d(TAG, "onResponse: " + response.body());
                    queries = response.body();
                    findViews();
                } else {
                    Log.d(TAG, "onResponse: failed");
                    Toast.makeText(FeedActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                    queries = new ArrayList<Query>();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Query>> call, Throwable t) {

            }
        });

    }

    private void findViews() {
        this.rvFeed = (RecyclerView) findViewById(R.id.rv_feed);
        this.rvFeed.setAdapter(new FeedAdapter());
        this.rvFeed.setLayoutManager(new LinearLayoutManager(this));
    }


    private class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

        @Override
        public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_feed, parent, false);
            return new FeedViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FeedViewHolder holder, int position) {
            final Query query = queries.get(position);
            holder.tvQuery.setText(query.getText());
            holder.btnTakeup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO : Take up Shit  send query Id also.
                    Intent i = new Intent(FeedActivity.this,QueryTaken.class);
                    i.putExtra("id", query.getId());
                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return queries.size();
        }
    }

    private class FeedViewHolder extends RecyclerView.ViewHolder {

        private TextView tvQuery;
        private Button btnTakeup;

        public FeedViewHolder(View itemView) {
            super(itemView);

            this.tvQuery = (TextView) itemView.findViewById(R.id.item_feed_query);
            this.btnTakeup = (Button) itemView.findViewById(R.id.item_feed_takeup);
        }
    }

}
