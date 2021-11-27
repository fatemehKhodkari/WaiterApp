package com.example.waiterapp.adapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.example.waiterapp.activity.customer.AddEditCostomerActivity;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.CustomerDao;
import com.example.waiterapp.model.Customer;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder>{

    List<Customer> list_customer;
    DatabaseHelper databaseHelper;
    CustomerDao customerDao;
    Customer customer;
    Context context;

    public CustomerAdapter(List<Customer> list_customer,Context context){
        this.list_customer = list_customer;
        this.context = context;
    }

    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_info,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.ViewHolder holder, int position) {
        customer = list_customer.get(position);
        holder.customer_name_tv.setText(customer.name);
        holder.customer_phone_tv.setText(customer.phone);
        holder.customer_address_tv.setText(customer.address);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list_customer.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customer_name_tv, customer_phone_tv , customer_address_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customer_name_tv = itemView.findViewById(R.id.customer_name);
            customer_phone_tv = itemView.findViewById(R.id.customer_phone);
            customer_address_tv =itemView.findViewById(R.id.customer_address);
        }
    }

    private void showDialogBSheet(int pos){

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_edit_customer);

        LinearLayout edit_customer_btsh , delete_customer_btsh ;

        edit_customer_btsh = bottomSheetDialog.findViewById(R.id.edit_customer_btsh);
        delete_customer_btsh = bottomSheetDialog.findViewById(R.id.delete_customer_btsh);

        delete_customer_btsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("حذف")
                        .setMessage("آیا از حذف این مشتری اطمینان دارید؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                databaseHelper = DatabaseHelper.getInstance(context.getApplicationContext());
                                customerDao = databaseHelper.customerDao();
                                customerDao.deleteCustomer(customer);
                                list_customer.remove(pos);
                                notifyItemRemoved(pos);
                                notifyItemRangeChanged(pos,list_customer.size());
                                bottomSheetDialog.dismiss();
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

    public void addList(List<Customer> arrCustomerList){
        list_customer.clear();
        list_customer.addAll(arrCustomerList);
        notifyDataSetChanged();
    }
}
