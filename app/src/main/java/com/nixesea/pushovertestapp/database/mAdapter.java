package com.nixesea.pushovertestapp.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nixesea.pushovertestapp.R;

import java.util.List;

class mAdapterHolder extends RecyclerView.ViewHolder{

    public TextView itemUserID;
    public TextView itemMessage;
    public TextView itemDate;

    mAdapterHolder(View itemView) {
        super(itemView);

        itemUserID = itemView.findViewById(R.id.item_card_user);
        itemMessage = itemView.findViewById(R.id.item_card_message);
        itemDate = itemView.findViewById(R.id.item_data);
    }
}

public class mAdapter extends RecyclerView.Adapter<mAdapterHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<HistoryUnit> items;


    public mAdapter(Context context, List<HistoryUnit> items) {
        this.items = items;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public mAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_item, parent, false);
        return new mAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final mAdapterHolder holder, int position) {
        holder.itemUserID.setText(items.get(position).getUserID());
        holder.itemMessage.setText(items.get(position).getMessage());
        holder.itemDate.setText(items.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}