package com.avanes.adressbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class FragmentDetalContact extends Fragment {
    View frag;
    ImageView iv_detail_img,iv_edit;
    TextView tv_detail_name_contact, tv_detail_phone_contact;
    String detail_img = "";
    String detail_name = "";
    String detail_phone = "";
    String detail_id = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        frag = inflater.inflate(R.layout.fragment_detal_contact, container, false);
        iv_detail_img = frag.findViewById(R.id.iv_detail_img);
        tv_detail_name_contact = frag.findViewById(R.id.tv_detail_name_contact);
        tv_detail_phone_contact = frag.findViewById(R.id.tv_detail_phone_contact);
        iv_edit =frag.findViewById(R.id.iv_edit);



//----------------------------------------------------------------------------------
        iv_edit.setOnClickListener(v -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            FragmentAddContact fa = new FragmentAddContact();
            fa.setParam(detail_id,detail_name,detail_phone,detail_img);
            ft.replace(R.id.ll_frag_add_contact,fa);
            ft.addToBackStack(null);
            ft.commit();
            ((MainActivity)getActivity()).ll_frag_add_contact.setVisibility(View.VISIBLE);

        });
//-----------------------------------------------------------------------------------









        updateUi();

        return frag;

    }

    public void setParam(String detail_id, String detail_img, String detail_name, String detail_phone) {
        this.detail_id = detail_id;
        this.detail_img = detail_img;
        this.detail_name = detail_name;
        this.detail_phone = detail_phone;
    }

    public void updateUi() {
        if (detail_img == null) {

            Picasso.get().load(R.drawable.not_user512).into(iv_detail_img);
        } else {
            Picasso.get().load(detail_img).into(iv_detail_img);
        }
        tv_detail_name_contact.setText(detail_name);
        tv_detail_phone_contact.setText(detail_phone);
    }



}

