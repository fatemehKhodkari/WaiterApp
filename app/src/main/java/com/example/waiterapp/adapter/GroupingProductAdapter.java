package com.example.waiterapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.example.waiterapp.model.Grouping;

import java.util.List;

public class GroupingProductAdapter extends RecyclerView.Adapter<GroupingProductAdapter.ViewHolder>{

    private Context context;
    private List<Grouping> groupingList;
    private ProductAdapter productAdapter;
    private Listener listener;
    private String categoryIsChosen = "" ;
    private int row_index ;

    public GroupingProductAdapter(Context context, List<Grouping> groupingList,Listener listener , String category ) {
        this.context = context;
        this.groupingList = groupingList;
        this.listener = listener;
        this.categoryIsChosen = category;
    }

    public interface Listener{
        void onClick(int pos, Grouping category);
    }


    @Override
    public GroupingProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grouping_product_info_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroupingProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Grouping grouping = groupingList.get(position);
        holder.grouping_product_name_tv.setText(grouping.name);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(position,grouping);
//            }
//        });
        if(row_index == position || grouping.name.equals(categoryIsChosen)){
            holder.linearLayout.setCardBackgroundColor(Color.parseColor("#795548"));
            holder.grouping_product_name_tv.setTextColor(Color.parseColor("#f8f3f7"));
        } else {
            holder.linearLayout.setCardBackgroundColor(Color.parseColor("#9F8075"));
            holder.grouping_product_name_tv.setTextColor(Color.parseColor("#737373"));
        }
    }

    @Override
    public int getItemCount() {
        return groupingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView grouping_product_name_tv;
        CardView linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            grouping_product_name_tv = itemView.findViewById(R.id.grouping_product_name_tv);
            linearLayout = itemView.findViewById(R.id.group_produc_back);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Grouping grouping = groupingList.get(getAdapterPosition());
            Log.e("ppp", "ViewHolder: send position " + getAdapterPosition());
            listener.onClick(getAdapterPosition(), grouping);
            row_index = getAdapterPosition() ;
            categoryIsChosen = "";
            notifyDataSetChanged();
            Log.e("poo", "ViewHolder: send position " + getAdapterPosition());
        }
    }
}
