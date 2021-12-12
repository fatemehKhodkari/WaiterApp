package com.example.waiterapp.adapter;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.example.waiterapp.helper.Tools;
import com.example.waiterapp.model.Product;

import java.util.List;

public class OrderingAdapter extends RecyclerView.Adapter<OrderingAdapter.ViewHolder>{

    Context context;
    Product product;
    List<Product> productList;
    Listener listener;

    public OrderingAdapter(Context context, List<Product> productList, Listener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    public interface Listener{
        void onAdded(int pos);
        void onRemove(int pos);
    }

    @Override
    public OrderingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_submited_info,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(OrderingAdapter.ViewHolder holder, int position) {
        product = productList.get(position);
        holder.product_ordering_name_tv.setText(product.name_product);
        holder.category_ordering_tv.setText(product.category);
        holder.product_ordering_price_tv.setText(Tools.getForamtPrice(Tools.convertToPrice(product.price) * product.amount + ""));
        holder.product_number_ordering.setText(product.amount+"");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView
                product_ordering_name_tv ,
                category_ordering_tv ,
                product_ordering_price_tv ,
                product_number_ordering ;
        ImageView ordering_product_imv;

        public ViewHolder(View itemView) {
            super(itemView);
            product_ordering_name_tv = itemView.findViewById(R.id.product_ordering_name_tv);
            category_ordering_tv = itemView.findViewById(R.id.category_ordering_tv);
            product_ordering_price_tv = itemView.findViewById(R.id.product_ordering_price_tv);
            product_number_ordering = itemView.findViewById(R.id.product_number_ordering);
            ordering_product_imv = itemView.findViewById(R.id.ordering_product_imv);
        }

        public void addList(List<Product> arryList){
            productList.clear();
            productList.addAll(arryList);
            notifyDataSetChanged();
        }
    }
}
