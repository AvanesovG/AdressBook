package com.avanes.adressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
        ConstraintLayout cl_list_contact_item;


        public ListHolder(@NonNull View itemView) {
            super(itemView);
            rv_img_list_contact = itemView.findViewById(R.id.rv_img_list_contact);
            rv_tv_name_contact = itemView.findViewById(R.id.rv_tv_name_contact);
            rv_tv_number_contact = itemView.findViewById(R.id.rv_tv_number_contact);
            cl_list_contact_item = itemView.findViewById(R.id.cl_list_contact_item);
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

        holder.cl_list_contact_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentDetalContact fragmentDetalContact = new FragmentDetalContact();
                fragmentDetalContact.setParam(list.get(position).id,list
                        .get(position).img,list
                        .get(position).name,list
                        .get(position).number);




                ft.replace(R.id.ll_frag_detail_contact, fragmentDetalContact);
                ft.addToBackStack(null);
                ft.commit();
                ((MainActivity) context).ll_frag_detail_contact.setVisibility(View.VISIBLE);
                ((MainActivity) context).iv_add_contact.setVisibility(View.GONE);




            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
