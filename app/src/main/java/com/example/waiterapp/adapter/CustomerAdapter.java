package com.example.waiterapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.example.waiterapp.activity.customer.AddEditCostomerActivity;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.CustomerDao;
import com.example.waiterapp.database.dao.DetailOrderDao;
import com.example.waiterapp.database.dao.SubmitOrderDao;
import com.example.waiterapp.helper.App;
import com.example.waiterapp.helper.Permition;
import com.example.waiterapp.model.Customer;
import com.example.waiterapp.model.Order;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> implements Filterable{

    private List<Customer> list_customer , list_search;
    private DatabaseHelper databaseHelper;
    private CustomerDao customerDao;
    private SubmitOrderDao submitOrderDao;
    private DetailOrderDao detailOrderDao;
    private Context context;
    private Listener listener;
    private String text;
    private Activity activity;


    public CustomerAdapter(List<Customer> list_customer, Context context, Listener listener , Activity activity){
        this.list_search = list_customer;
        this.list_customer = new ArrayList<>(list_search);
        this.context = context;
        this.listener = listener;
        this.activity = activity;
    }

    public interface Listener{
        void onClickListener(Customer customer , int pos ,String name);
    }

    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_info,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Customer customer = list_customer.get(position);
        holder.customer_name_tv.setText(customer.name);
        holder.customer_phone_tv.setText(customer.phone);
        holder.customer_address_tv.setText(customer.address);

        holder.call.setOnClickListener(v -> {

            Permition permition;
            permition = new Permition(200,context , activity) {

                @Override
                public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            };

            if (permition.checkPermission()){
                String number_txt = list_search.get(position).phone;
                Intent intent =  new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:" + number_txt));
                context.startActivity(intent);
            }

        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                showDialogBSheet(position);
                listener.onClickListener(customer , position , list_customer.get(position).name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_customer.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customer_name_tv, customer_phone_tv , customer_address_tv;
        LinearLayout call;

        public ViewHolder(View itemView) {
            super(itemView);
            customer_name_tv = itemView.findViewById(R.id.customer_name);
            customer_phone_tv = itemView.findViewById(R.id.customer_phone);
            customer_address_tv =itemView.findViewById(R.id.customer_address);
            call = itemView.findViewById(R.id.call);
        }
    }

     public void showDialogBSheet(int pos , String name , int id){

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_edit_customer);

        LinearLayout edit_customer_btsh , delete_customer_btsh ;

        edit_customer_btsh = bottomSheetDialog.findViewById(R.id.edit_customer_btsh);
        delete_customer_btsh = bottomSheetDialog.findViewById(R.id.delete_customer_btsh);

         TextView title = bottomSheetDialog.findViewById(R.id.name_sheet_c);
         title.setText(name);


         delete_customer_btsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initDataBase();
                setTextDialog(id);
                setAlertDialog(id , bottomSheetDialog , pos ,  name);

            }
        });

        edit_customer_btsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AddEditCostomerActivity.class);
                intent.putExtra("Customer" , new Gson().toJson(list_customer.get(pos)));
                context.startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private void initDataBase(){
        databaseHelper = App.getDatabase();
        customerDao = databaseHelper.customerDao();
        submitOrderDao = databaseHelper.submitOrderDao();
        detailOrderDao = databaseHelper.detailOrderDao();
        List<Order> listOrder = new ArrayList<>();
        listOrder.addAll(submitOrderDao.getOrderList()) ;
    }

    private void setTextDialog(int id){
        if(submitOrderDao.getid(id) != null){
            text = " این کاربر دارای سفارش هست ، ایا مایلید انرا حذف کنید؟ ";
        }else {
            text = " ایا مایلید این مورد را حذف کنید؟";
        }
    }

    private void setAlertDialog(int id , BottomSheetDialog bottomSheetDialog , int pos , String name){

        new AlertDialog.Builder(context)
                .setTitle("حذف")
                .setMessage(text)
                .setPositiveButton("بله", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(submitOrderDao.getid(id) != null){
                            Log.e("unit" , "unit : " + submitOrderDao.getid(id).unit_code);
                            detailOrderDao.deleteOneOrderDetail(submitOrderDao.getid(id).unit_code);
                            submitOrderDao.deteteID(id);
                            deleteOneItem(bottomSheetDialog, pos, name);
                        }else {
                            deleteOneItem(bottomSheetDialog, pos, name);
                        }
                    }
                })
                .setNegativeButton("خیر",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        bottomSheetDialog.dismiss();
                    }
                })
                .create()
                .show();
        bottomSheetDialog.dismiss();

    }

    public void deleteOneItem(BottomSheetDialog bottomSheetDialog , int pos, String name){
        customerDao.deleteCustomer(list_customer.get(pos));
        list_customer.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, list_customer.size());
        notifyDataSetChanged();
        bottomSheetDialog.dismiss();
        Toast.makeText(context, name+" با موفقیت حذف شد ", Toast.LENGTH_LONG).show();
    }

    public void addList(List<Customer> arrCustomerList){
        list_search.clear();
        list_search.addAll(arrCustomerList);
        list_customer = new ArrayList<>(list_search);
        notifyDataSetChanged();
    }


    //  For search
    @Override
    public Filter getFilter() {
        return newsFilter;
    }
    private final Filter newsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Customer> filterdNewList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterdNewList.addAll(list_search);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Customer customer : list_search){

                    if(customer.name.toLowerCase().contains(filterPattern))
                        filterdNewList.add(customer);

                    if(customer.phone.toLowerCase().contains(filterPattern))
                        filterdNewList.add(customer);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterdNewList;
            results.count = filterdNewList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list_customer.clear();
            list_customer.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

}
