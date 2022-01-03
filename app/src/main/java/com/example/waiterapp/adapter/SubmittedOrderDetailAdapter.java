package com.example.waiterapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.example.waiterapp.model.DetailOrder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubmittedOrderDetailAdapter extends RecyclerView.Adapter<SubmittedOrderDetailAdapter.ViewHolder> {

    Context context;
    List<DetailOrder> detailOrderList;

    public SubmittedOrderDetailAdapter(Context context, List<DetailOrder> detailOrderList) {
        this.context = context;
        this.detailOrderList = detailOrderList;
    }

    @Override
    public SubmittedOrderDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.submitted_order_detail_info , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubmittedOrderDetailAdapter.ViewHolder holder, int position) {
        DetailOrder detailOrder = detailOrderList.get(position);
        holder.submitted_ordering_name_tv.setText(detailOrder.name);
        holder.submitted_ordering_category_tv.setText(detailOrder.category);
        holder.submitted_ordering_price_tv.setText(detailOrder.price);
        holder.amount_detail.setText(String.valueOf(detailOrder.amant));
        holder.submitted_order_imv.setImageURI(Uri.parse(detailOrder.ordered_pic));

    }

    @Override
    public int getItemCount() {
        return detailOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView
                submitted_ordering_price_tv,
                submitted_ordering_name_tv,
                submitted_ordering_category_tv,
                amount_detail;
        CircleImageView submitted_order_imv;

        public ViewHolder(View itemView) {
            super(itemView);
            submitted_ordering_price_tv = itemView.findViewById(R.id.submitted_ordering_price_tv);
            submitted_ordering_name_tv = itemView.findViewById(R.id.submitted_ordering_name_tv);
            submitted_ordering_category_tv = itemView.findViewById(R.id.submitted_ordering_category_tv);
            amount_detail = itemView.findViewById(R.id.amount_detail);
            submitted_order_imv = itemView.findViewById(R.id.submitted_order_imv);
        }
    }
}
