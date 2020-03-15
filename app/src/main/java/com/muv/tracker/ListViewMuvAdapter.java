package com.muv.tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListViewMuvAdapter {
    private Context myContext;
   // private int myResource;
    private BaseAdapter newsAndAnnouncementAdapter;
    private ArrayList<NewsAndAnnouncement> newsAndAnnouncementArrayList;
    public ListViewMuvAdapter(Context myContext) {
        this.myContext = myContext;
    }

    public BaseAdapter getNewsAndAnnouncementAdapter() {
        this.newsAndAnnouncementAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return newsAndAnnouncementArrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return newsAndAnnouncementArrayList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                String newsTitle = newsAndAnnouncementArrayList.get(position).getNewsTitle();
                String newsDescription = newsAndAnnouncementArrayList.get(position).getNewsDescription();
                LayoutInflater inflater = LayoutInflater.from(myContext);

                convertView = inflater.inflate(R.layout.listview_adapter_news_announcement,parent,false);
                TextView tvNewsTitle = convertView.findViewById(R.id.tvNewsTitle);
                TextView tvNewsText = convertView.findViewById(R.id.tvNewsText);
                tvNewsTitle.setText(newsTitle);
                tvNewsText.setText(newsDescription);
                return convertView;
            }
        };
        return newsAndAnnouncementAdapter;
    }

    public void setNewsAndAnnouncementList(ArrayList<NewsAndAnnouncement> newsAndAnnouncements) {
        this.newsAndAnnouncementArrayList = newsAndAnnouncements;
    }
}
