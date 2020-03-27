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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private ListViewMuvAdapter listViewMuvAdapter;
    private ArrayList<NewsAndAnnouncement> newsAndAnnouncements;
    private DbMUVFirebase dbMUVFirebase;

    private View view;
    private Context myContext;
    private int flags;

    private ListView lvNews;

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
        dbMUVFirebase = new DbMUVFirebase();
        if (flags == 1){
            view = inflater.inflate(R.layout.fragment_news_announcement,container,false);
            loadNewsAndAnnouncement();
        }
        else if (flags == 2){
            view = inflater.inflate(R.layout.fragment_routes,container,false);
            loadRoutes();
        }
        else if (flags == 3){
            view = inflater.inflate(R.layout.fragment_tickets,container,false);
            loadTickets();
        }
        return view;
    }

    private void loadRoutes(){
        ListView lvRoutes = view.findViewById(R.id.lvRoutes);

        Routes routes = new Routes();
        routes.setRouteName("SSS Village to Cubao");
        routes.setNumberQueue("52");
        routes.setCurrentQueue("29");

        ArrayList<Routes> routesArrayList = new ArrayList<>();
        routesArrayList.add(routes);
        listViewMuvAdapter = new ListViewMuvAdapter(myContext);
        listViewMuvAdapter.setRoutesArrayList(routesArrayList);

        lvRoutes.setAdapter(listViewMuvAdapter.getRoutesAdapter());

    }

    private void loadNewsAndAnnouncement(){
        lvNews = view.findViewById(R.id.lvNews);
        listViewMuvAdapter = new ListViewMuvAdapter(myContext);
       /* final NewsAndAnnouncement[] p = new NewsAndAnnouncement[1];
        dbMUVFirebase.loadNewsAndAnnouncement(new DbMUVFirebase.LoadNewsAndAnnouncement() {
            @Override
            public void loadNews(ArrayList<NewsAndAnnouncement> newsAndAnnouncements) {
                 p[0] = newsAndAnnouncements.get(0);                *//*listViewMuvAdapter.setNewsAndAnnouncementList(newsAndAnnouncements);
                lvNews.setAdapter(listViewMuvAdapter.getNewsAndAnnouncementAdapter());*//*
            }
        });*/
//        Toast.makeText(myContext, p[0].getNewsContent(), Toast.LENGTH_SHORT).show();

        NewsAndAnnouncement news1 = new NewsAndAnnouncement();
        news1.setNewsTitle("STI College Marikina: TNT Cluster");
        news1.setNewsContent("STI College Marikina Won 4 Cluster Championship in TNT Competition");

        NewsAndAnnouncement news2 = new NewsAndAnnouncement();
        news2.setNewsTitle("Luzon is Under Enhance Quarantine and State of Calamity");
        news2.setNewsContent("President Duterte announce that Luzon is under Enhance Quarantine due to COVID-19 and the country is under state of calamity");

        NewsAndAnnouncement news3 = new NewsAndAnnouncement();
        news3.setNewsTitle("Testing Testing Testing Testing");
        news3.setNewsContent("Testing Testing Testing Testing Testing Testing Testing Testing Testing Testing Testing Testing " +
                "Testing Testing Testing Testing Testing Testing Testing Testing Testing Testing Testing Testing Testing Testing Testing Testing " +
                "Testing Testing Testing Testing Testing Testing Testing TestingTesting Testing Testing Testing Testing Testing Testing Testing");

        newsAndAnnouncements = new ArrayList<>();
        newsAndAnnouncements.add(news1);
        newsAndAnnouncements.add(news2);
        newsAndAnnouncements.add(news3);
        listViewMuvAdapter.setNewsAndAnnouncementList(newsAndAnnouncements);
        lvNews.setAdapter(listViewMuvAdapter.getNewsAndAnnouncementAdapter());


    }
    private void loadTickets(){
        ListView lvTickets = view.findViewById(R.id.lvTickets);
        Tickets tickets = new Tickets();
        tickets.setTicketNo("27");
        tickets.setRouteName("SSS Village - Cubao");
        tickets.setEta("1 Hour");

        Tickets tickets1 = new Tickets();
        tickets1.setTicketNo("29");
        tickets1.setRouteName("Parang - Cubao");
        tickets1.setEta("30 Minutes");

        ArrayList<Tickets> ticketsList = new ArrayList<>();
        ticketsList.add(tickets);
        ticketsList.add(tickets1);

        listViewMuvAdapter = new ListViewMuvAdapter(myContext);
        listViewMuvAdapter.setTicketsArrayList(ticketsList);
        lvTickets.setAdapter(listViewMuvAdapter.getTicketAdapter());
       /* lvTickets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
    }


    //----------------------------------------Dialogs------------------------------------------


}
