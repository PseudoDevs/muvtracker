package com.muv.tracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListViewMuvAdapter {
    private Context myContext;
   // private int myResource;
    private BaseAdapter newsAndAnnouncementAdapter,ticketAdapter, routesAdapter;
    private ArrayList<NewsAndAnnouncement> newsAndAnnouncementArrayList;
    private ArrayList<Tickets> ticketsArrayList;
    private ArrayList<Routes> routesArrayList;
    private android.support.v7.app.AlertDialog adViewNews;

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
                final NewsAndAnnouncement newsAndAnnouncement = newsAndAnnouncementArrayList.get(position);
                String newsTitle = newsAndAnnouncementArrayList.get(position).getNewsTitle();
                String newsDescription = newsAndAnnouncementArrayList.get(position).getNewsDescription();
                LayoutInflater inflater = LayoutInflater.from(myContext);

                if ((newsDescription.length()/2) > 100) {
                    newsDescription = newsDescription.substring(0, newsDescription.length() / 2) + "...";
                }

                convertView = inflater.inflate(R.layout.listview_adapter_news_announcement,parent,false);
                TextView tvNewsTitle = convertView.findViewById(R.id.tvNewsTitle);
                TextView tvNewsText = convertView.findViewById(R.id.tvNewsText);
                TextView tvReadMore = convertView.findViewById(R.id.tvReadMore);
                tvReadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogViewNews(newsAndAnnouncement);
                    }
                });
                tvNewsTitle.setText(newsTitle);
                tvNewsText.setText(newsDescription);
                return convertView;
            }
        };
        return newsAndAnnouncementAdapter;
    }
    private void dialogViewNews(NewsAndAnnouncement newsAndAnnouncement){
        adViewNews = new android.support.v7.app.AlertDialog.Builder(myContext).setTitle(newsAndAnnouncement.getNewsTitle()).setPositiveButton("Close",null).create();
        View view1 = LayoutInflater.from(myContext).inflate(R.layout.dialog_view_news_announcement,null);
        TextView tvNewsDescription = view1.findViewById(R.id.tvNewsText);
        tvNewsDescription.setText(newsAndAnnouncement.getNewsDescription());
        adViewNews.setView(view1);
        adViewNews.cancel();
        adViewNews.show();
    }

    public BaseAdapter getTicketAdapter() {
        this.ticketAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return ticketsArrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return ticketsArrayList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(myContext);
                convertView = inflater.inflate(R.layout.listview_adapter_tickets,parent,false);

                String ticketNo = ticketsArrayList.get(position).getTicketNo();
                String routeName = ticketsArrayList.get(position).getRouteName();
                String eta = ticketsArrayList.get(position).getEta();

                TextView tvTicketNo = convertView.findViewById(R.id.tvTicketNo);
                TextView tvRouteName = convertView.findViewById(R.id.tvRouteName);
                TextView tvEta = convertView.findViewById(R.id.tvETA);

                tvTicketNo.setText("#" + ticketNo);
                tvRouteName.setText(routeName);
                tvEta.setText(eta);
                return convertView;
            }
        };
        return ticketAdapter;
    }

    public BaseAdapter getRoutesAdapter() {
        this.routesAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return routesArrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return routesArrayList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final Routes routes = routesArrayList.get(position);
                LayoutInflater inflater = LayoutInflater.from(myContext);
                convertView = inflater.inflate(R.layout.listview_adapter_routes,parent,false);

                TextView tvRoute = convertView.findViewById(R.id.tvRoute);
                TextView tvNumberOfQueue = convertView.findViewById(R.id.tvNumberQueue);
                TextView tvCurrentQueue = convertView.findViewById(R.id.tvCurrentQueue);

                tvRoute.setText("Route: " + routes.getRouteName());
                tvNumberOfQueue.setText(routes.getNumberQueue());
                tvCurrentQueue.setText(routes.getCurrentQueue());

                Button btnQueue = convertView.findViewById(R.id.btnQueue);
                Button btnViewQueue = convertView.findViewById(R.id.btnViewQueue);

                final RoutesDialogHolder routesDialogHolder = new RoutesDialogHolder(myContext);

                btnQueue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        routesDialogHolder.dialogQueue();
                    }
                });

                btnViewQueue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        routesDialogHolder.dialogViewQueue();
                    }
                });

                return convertView;
            }
        };
        return routesAdapter;
    }

    private class RoutesDialogHolder{

        private Context context;
        private AlertDialog adQueue;
        private AlertDialog adViewQueue;
        private BaseAdapter adapViewQueue;
        private ArrayList<ViewQueue> viewQueueArrayList;
        private int noOfSeats = 0;

        public RoutesDialogHolder(Context context) {
            this.context = context;
        }

        private void dialogQueue(){
            adQueue  = new AlertDialog.Builder(context)
                    .setTitle("Queue")
                    .setPositiveButton("Close",null)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "You pressed Ok", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create();
            View view =  LayoutInflater.from(context).inflate(R.layout.dialog_queue,null);


            final EditText etNoOfSeats = view.findViewById(R.id.etNumberOfSeats);
            Button btnDecrease = view.findViewById(R.id.btnDecrease);
            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noOfSeats--;
                    if (noOfSeats < 0) {
                        noOfSeats = 0;
                    }
                    etNoOfSeats.setText(Integer.toString(noOfSeats));
                }
            });
            Button btnIncrease = view.findViewById(R.id.btnIncrease);
            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   noOfSeats++;
                   etNoOfSeats.setText(Integer.toString(noOfSeats));
                }
            });



            adQueue.setView(view);
            adQueue.show();
        }

        private void dialogViewQueue(){
            adViewQueue = new AlertDialog.Builder(context)
                    .setTitle("View Queue")
                    .setPositiveButton("Close",null)
                    .create();
            View view =  LayoutInflater.from(context).inflate(R.layout.dialog_view_queue,null);
            ListView lvViewQueue = view.findViewById(R.id.lvViewQueue);

            ViewQueue viewQueue = new ViewQueue();
            viewQueue.setVehicleNo("1");
            viewQueue.setETA("1h 3mins");

            ViewQueue viewQueue1 = new ViewQueue();
            viewQueue1.setVehicleNo("2");
            viewQueue1.setETA("2h 35mins");

            viewQueueArrayList = new ArrayList<>();
            viewQueueArrayList.add(viewQueue);
            viewQueueArrayList.add(viewQueue1);

            lvViewQueue.setAdapter(getAdapViewQueue());
            adViewQueue.setView(view);
            adViewQueue.show();

        }

        public BaseAdapter getAdapViewQueue() {
            this.adapViewQueue = new BaseAdapter() {
                @Override
                public int getCount() {
                    return viewQueueArrayList.size();
                }

                @Override
                public Object getItem(int position) {
                    return viewQueueArrayList.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    ViewQueue viewQueue = viewQueueArrayList.get(position);
                    convertView = LayoutInflater.from(context).inflate(R.layout.listview_adapter_dialog_viewqueue,parent,false);
                    TextView tvVehicleNo = convertView.findViewById(R.id.tvVehicleNo);
                    TextView tvEta = convertView.findViewById(R.id.tvEta);
                    tvVehicleNo.append(viewQueue.getVehicleNo());
                    tvEta.append(viewQueue.getETA());
                    return convertView;
                }
            };
            return adapViewQueue;
        }
    }

    public void setRoutesArrayList(ArrayList<Routes> routesArrayList) {
        this.routesArrayList = routesArrayList;
    }

    public void setNewsAndAnnouncementList(ArrayList<NewsAndAnnouncement> newsAndAnnouncements) {
        this.newsAndAnnouncementArrayList = newsAndAnnouncements;
    }

    public void setTicketsArrayList(ArrayList<Tickets> ticketsArrayList) {
        this.ticketsArrayList = ticketsArrayList;
    }
}
