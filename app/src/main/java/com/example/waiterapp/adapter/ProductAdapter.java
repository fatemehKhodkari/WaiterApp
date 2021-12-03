package com.example.waiterapp.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.ProductDao;
import com.example.waiterapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewholderProduct> implements Filterable {

    List<Product> productList,search_list_product;
    Context  context;
    Listener listener;
    DatabaseHelper databaseHelper;
    ProductDao productDao;

    public ProductAdapter(List<Product> productList, Context context, Listener listener) {
        this.productList = productList;
        this.context = context;
        this.listener = listener;
    }

    public interface Listener{
        void onClick(Product product , int pos);
    }

    @Override
    public ViewholderProduct onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent. getContext());
        View view = layoutInflater.inflate(R.layout.product_info_list,parent,false);
        ViewholderProduct viewholderProduct =new ViewholderProduct(view);
        return viewholderProduct;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ProductAdapter.ViewholderProduct holder, @SuppressLint("RecyclerView") int position) {

        Product product = productList.get(position);
        holder.product_name_tv.setText(product.name_product);
        holder.product_grouping_name_tv.setText(product.category);
        holder.product_price_tv.setText(product.price);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                showBottomSheetDialogclick(position);
                listener.onClick(product , position);

            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return newsFilter;
    }

    private final Filter newsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Product> filterNewList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterNewList.addAll(search_list_product);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Product product : search_list_product){

                    if(product.name_product.toLowerCase().contains(filterPattern))
                        filterNewList.add(product);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterNewList;
            results.count = filterNewList.size();
            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewholderProduct extends RecyclerView.ViewHolder {
        TextView product_name_tv , product_grouping_name_tv , product_price_tv;
        ImageView product_imv;
        public ViewholderProduct( View itemView) {
            super(itemView);
            product_name_tv = itemView.findViewById(R.id.product_name_tv);
            product_grouping_name_tv = itemView.findViewById(R.id.product_grouping_name_tv);
            product_imv = itemView.findViewById(R.id.product_imv);
            product_price_tv = itemView.findViewById(R.id.product_price_tv);

        }
    }

    public  void addList(List<Product> arryList){
        search_list_product.clear();
        search_list_product.addAll(arryList);
        productList = new ArrayList<>(search_list_product);
        notifyDataSetChanged();
    }

    public int count(){
        return productList.size();
    }
}
