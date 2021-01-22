package com.avanes.adressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListContact extends RecyclerView.Adapter<AdapterListContact.ListHolder> {

    List<ClListContact> list;
    Context context;

    public AdapterListContact(List<ClListContact> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView rv_tv_number_contact, rv_tv_name_contact;
        ImageView rv_img_list_contact;


        public ListHolder(@NonNull View itemView) {
            super(itemView);
            rv_img_list_contact = itemView.findViewById(R.id.rv_img_list_contact);
            rv_tv_name_contact = itemView.findViewById(R.id.rv_tv_name_contact);
            rv_tv_number_contact = itemView.findViewById(R.id.rv_tv_number_contact);
        }
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list_contact, parent, false);
        ListHolder lh = new ListHolder(v);
        return lh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        holder.rv_tv_name_contact.setText(list.get(position).name);
        holder.rv_tv_number_contact.setText(list.get(position).number);
        if (list.get(position).img == null) {
            Picasso.get().load(R.drawable.not_user).into(holder.rv_img_list_contact);
        } else {
            Picasso.get().load(list.get(position).img).into(holder.rv_img_list_contact);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
