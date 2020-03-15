package com.muv.tracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private ListView lvNews;
    private ListViewMuvAdapter newsAdapter;
    private ArrayList<NewsAndAnnouncement> newsAndAnnouncements;
    private AlertDialog adViewNews;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(DashboardActivity.this, R.string.title_home, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_dashboard:
                    Toast.makeText(DashboardActivity.this, R.string.title_dashboard, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_notifications:
                    Toast.makeText(DashboardActivity.this, R.string.title_notifications, Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadNewsAndAnnouncement();
    }

    private void loadNewsAndAnnouncement(){
        lvNews = findViewById(R.id.lvNews);

        NewsAndAnnouncement news1 = new NewsAndAnnouncement();
        news1.setNewsTitle("STI College Marikina Testing Testing");
        news1.setNewsDescription("STI College Marikina Won 4 Cluster Championship in TNT Competition");

        newsAndAnnouncements = new ArrayList<>();
        newsAndAnnouncements.add(news1);
        newsAndAnnouncements.add(news1);
        newsAndAnnouncements.add(news1);

        newsAdapter = new ListViewMuvAdapter(this);
        newsAdapter.setNewsAndAnnouncementList(newsAndAnnouncements);
        lvNews.setAdapter(newsAdapter.getNewsAndAnnouncementAdapter());
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogView(newsAndAnnouncements.get(position));
            }
        });
    }
    private void dialogView(NewsAndAnnouncement newsAndAnnouncement){
        adViewNews = new AlertDialog.Builder(DashboardActivity.this).setTitle(newsAndAnnouncement.getNewsTitle()).setPositiveButton("Close",null).create();
        View view1 = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.dialog_view_news_announcement,null);
        TextView tvNewsDescription = view1.findViewById(R.id.tvNewsText);
        tvNewsDescription.setText(newsAndAnnouncement.getNewsDescription());
        adViewNews.setView(view1);
        adViewNews.cancel();
        adViewNews.show();
    }
}
