package com.example.hp.franchisehelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {



    Context context;

    ArrayList<Profile> profiles;



    public MyAdapter(Context c , ArrayList<Profile> p)

    {

        context = c;

        profiles = p;

    }



    @NonNull

    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));

    }



    @Override

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Tname.setText("  "+profiles.get(position).getName());
        holder.recieved.setText(" "+profiles.get(position).getReceived());
        holder.sold.setText("  "+profiles.get(position).getSold());
        holder.leftover.setText("  "+profiles.get(position).getLeftover());
        holder.Main_price.setText("  Rs."+profiles.get(position).getPrice()+"/-");



    }



    @Override

    public int getItemCount() {

        return profiles.size();

    }



    class MyViewHolder extends RecyclerView.ViewHolder

    {

        TextView Tname,leftover,recieved,sold,Main_price;

        public MyViewHolder(View itemView) {

            super(itemView);

            Tname = (TextView) itemView.findViewById(R.id.names);
            leftover=itemView.findViewById(R.id.remainings);
            sold=itemView.findViewById(R.id.solds);
            recieved=itemView.findViewById(R.id.alelas);
            Main_price=itemView.findViewById(R.id.priceItem);

        }



    }

}