package com.example.expenses;

/**
 * Created by shara on 11/21/2016.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by androidwarriors on 2/18/2016.
 */
class ListAdapt extends RecyclerView.Adapter<ListAdapt.ListViewHolder> {

    Context context;
    List<UserData> dataList = new ArrayList<>();
    LayoutInflater inflater;
    Listener listener;

    public ListAdapt(Context context, List<UserData> dataList1) {

        this.context = context;
        this.dataList = dataList1;
        this.listener= (Listener) context;
        inflater = LayoutInflater.from(context);


    }
    public void insert(int position, UserData data) {
        dataList.add(position, data);
        notifyItemInserted(position);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = inflater.inflate(R.layout.row, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(convertView);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
      /*  holder.iv_delete.setTag(position);
        holder.tv_name.setText(dataList.get(position).ClientName);
        holder.tv_invoice.setText(dataList.get(position).InvoiceId);
        holder.tv_date.setText(dataList.get(position).Date);
        holder.tv_amount.setText(dataList.get(position).Amount);
        holder.tv_desc.setText(dataList.get(position).Description);
*/
        holder.iv_delete.setTag(position);
        UserData ci = dataList.get(position);
        holder.tv_name.setText("Client Name : "+ci.ClientName);
        holder.tv_invoice.setText("Invoice Number : "+ci.InvoiceId);
        holder.tv_date.setText("Date : "+ci.Date);
        holder.tv_amount.setText("Amount : $"+ci.Amount);
        holder.tv_desc.setText("Description of invoice : "+ci.Description);
        //contactViewHolder.vSurname.setText(ci.surname);
        //contactViewHolder.vEmail.setText(ci.email);
       // contactViewHolder.vTitle.setText(ci.name + " " + ci.surname);

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    // Setting Dialog Title
                    alertDialog.setTitle("Delete");

                    // Setting Dialog Message
                    alertDialog.setMessage("Do you want to delete this expense?");

                    // On pressing the Yes button.
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            // ToDo here
                            listener.nameToChnge(dataList.get((Integer) v.getTag()).InvoiceId);
                        }
                    });

                    // On pressing the No button
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }




            }
        );
    }





    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_invoice,tv_date,tv_amount,tv_desc;
        ImageView iv_delete;

        public ListViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_invoice=(TextView)itemView.findViewById(R.id.tv_invoice);
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);
            tv_amount=(TextView)itemView.findViewById(R.id.tv_amount);
            tv_desc=(TextView)itemView.findViewById(R.id.tv_description);
            iv_delete= (ImageView) itemView.findViewById(R.id.iv_delete);

        }
    }


}

