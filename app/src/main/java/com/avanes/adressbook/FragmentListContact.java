package com.avanes.adressbook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class FragmentListContact extends Fragment {
    View frag;
    RecyclerView rv_list_contact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frag = inflater.inflate(R.layout.fragment_list_contact, container, false);

        rv_list_contact = frag.findViewById(R.id.rv_list_contact);

        List<ClListContact> listContacts = new ArrayList<>();
        //   listContacts.add(new ClListContact("22", "ff", "qq", "tt"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        rv_list_contact.setLayoutManager(linearLayoutManager);

        AdapterListContact adapterListContact = new AdapterListContact(listContacts, getContext());
        rv_list_contact.setAdapter(adapterListContact);

        return frag;
    }

    public void setAdapter(List<ClListContact> list) {

        Collections.sort(list, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        AdapterListContact adapterListContact = new AdapterListContact(list, getContext());
        rv_list_contact.setAdapter(adapterListContact);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) &&
                ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED))) {
            ActivityCompat.requestPermissions(((MainActivity) getActivity()), new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 100);


        } else {
            ((MainActivity) getActivity()).readContact();
        }
    }
}