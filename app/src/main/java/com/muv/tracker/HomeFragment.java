package com.muv.tracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private ListViewMuvAdapter newsAdapter;
    private ArrayList<NewsAndAnnouncement> newsAndAnnouncements;
    private AlertDialog adViewNews;
    private View view;
    private Context myContext;
    private int flags;

    public HomeFragment(Context context) {
        this.myContext = context;
    }

    public HomeFragment(Context context,int flags) {
        this.myContext = context;
        this.flags = flags;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (flags == 1){
            view = inflater.inflate(R.layout.fragment_news_announcement,container,false);
            loadNewsAndAnnouncement();
        }
        return view;
    }

    private void loadNewsAndAnnouncement(){
        ListView lvNews;lvNews = view.findViewById(R.id.lvNews);
        NewsAndAnnouncement news1 = new NewsAndAnnouncement();
        news1.setNewsTitle("STI College Marikina Testing Testing");
        news1.setNewsDescription("STI College Marikina Won 4 Cluster Championship in TNT Competition");

        newsAndAnnouncements = new ArrayList<>();
        newsAndAnnouncements.add(news1);
        newsAndAnnouncements.add(news1);
        newsAndAnnouncements.add(news1);

        newsAdapter = new ListViewMuvAdapter(myContext);
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
        adViewNews = new AlertDialog.Builder(myContext).setTitle(newsAndAnnouncement.getNewsTitle()).setPositiveButton("Close",null).create();
        View view1 = LayoutInflater.from(myContext).inflate(R.layout.dialog_view_news_announcement,null);
        TextView tvNewsDescription = view1.findViewById(R.id.tvNewsText);
        tvNewsDescription.setText(newsAndAnnouncement.getNewsDescription());
        adViewNews.setView(view1);
        adViewNews.cancel();
        adViewNews.show();
    }

}
