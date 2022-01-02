package com.example.waiterapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.example.waiterapp.activity.submittedorderdetail.SubmittedOrderDetailActivity;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.SubmitOrderDao;
import com.example.waiterapp.helper.App;
import com.example.waiterapp.model.Order;

import java.util.List;

public class ListSubmittedAdapter extends  RecyclerView.Adapter<ListSubmittedAdapter.ViewHolder>{

    private Context context;
    private List<Order> orderList;
    DatabaseHelper databaseHelper = App.getDatabase();

    public ListSubmittedAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public ListSubmittedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.submitted_order_info_list , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListSubmittedAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.name_orderer.setText(order.name_orderer);
        holder.status_orered.setText(String.valueOf(order.status));
        holder.total_ordered.setText(order.total);
        holder.explain_ordered.setText(order.description);
        holder.date_of_ordered.setText(order.date);
        holder.time_ordered.setText(order.time);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView
                name_orderer ,
                date_of_ordered ,
                status_orered ,
                total_ordered ,
                time_ordered ,
                explain_ordered;
        ImageView
                delete_submitted_order ,
                edit_submitted_order;

        public ViewHolder(View itemView) {
            super(itemView);
            name_orderer = itemView.findViewById(R.id.name_orderer);
            date_of_ordered = itemView.findViewById(R.id.date_of_ordered);
            time_ordered = itemView.findViewById(R.id.time_ordered);
            status_orered = itemView.findViewById(R.id.status_orered);
            total_ordered = itemView.findViewById(R.id.total_ordered);
            explain_ordered = itemView.findViewById(R.id.explain_ordered);

            delete_submitted_order = itemView.findViewById(R.id.delete_submitted_order);
            delete_submitted_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SubmitOrderDao submitOrderDao = databaseHelper.submitOrderDao();
                    submitOrderDao.deleteOrder(orderList.get(getAdapterPosition()));
                    orderList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition() , orderList.size());
                    notifyDataSetChanged();
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(context , SubmittedOrderDetailActivity.class);
            intent.putExtra("code" , orderList.get(getAdapterPosition()).unit_code);
            intent.putExtra("orderer_id" , orderList.get(getAdapterPosition()).orderer_id);
            intent.putExtra("orderer_name" , orderList.get(getAdapterPosition()).name_orderer);
            intent.putExtra("total" , orderList.get(getAdapterPosition()).total);
            context.startActivity(intent);

        }
    }
}
