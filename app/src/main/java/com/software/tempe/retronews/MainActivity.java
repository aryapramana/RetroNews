package com.software.tempe.retronews;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.software.tempe.retronews.adapter.NewsAdapter;
import com.software.tempe.retronews.model.Articles;
import com.software.tempe.retronews.model.News;
import com.software.tempe.retronews.service.ClientApi;
import com.software.tempe.retronews.service.ClientInterface;
import com.software.tempe.retronews.utility.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "b4fc974ae34c43a5aca21930da1638bc";

    private RecyclerView newsRecyclerView;
    private NewsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView titleHeadTxtView;

    private CollapsingToolbarLayout app_collapsing_bar;

    private List<Articles> articlesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecyclerView = findViewById(R.id.newsRecyclerView);

        app_collapsing_bar = findViewById(R.id.app_collapsing_bar);

        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // newsRecyclerView.setNestedScrollingEnabled(true);

        loadData("");

    }

    private void loadData(String keyword) {
        ClientInterface clientInterface = ClientApi.getClientApi().create(ClientInterface.class);
        // String country = Utils.getCountry();
        // String language = Utils.getLanguage();

        Call<News> call;

        if (keyword.length() > 0)   {
            call = clientInterface.getKeyword(keyword, 10, 1, "en", "publishedAt", API_KEY);
        } else {
            call = clientInterface.getNews("us", 10, 1,API_KEY);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (!response.isSuccessful())   {
                    Toast.makeText(MainActivity.this, "message: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                if (response.body() != null) {
                    articlesList = response.body().getArticles();
                    adapter = new NewsAdapter(articlesList, MainActivity.this);
                    newsRecyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                    }

                }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(MainActivity.this, "message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem menuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search the latest news...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() > 2) {
                    loadData(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                loadData(s);
                return false;
            }
        });

        menuItem.getIcon().setVisible(false, false);

        return true;

    }
}
