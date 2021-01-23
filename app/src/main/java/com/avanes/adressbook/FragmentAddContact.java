package com.avanes.adressbook;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FragmentAddContact extends Fragment {
    ArrayList<ContentProviderOperation> ops;
    TextView tv_save, tv_add_contact_title;
    ImageView iv_add_photo_user;
    View frag;
    EditText et_add_name_contact, et_add_number_phone;
    String id = "";
    String name = "";
    String phone = "";
    String img;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frag = inflater.inflate(R.layout.fragment_add_contact, container, false);
//----------------------------------------------------------------------------------------------
        tv_save = frag.findViewById(R.id.tv_save);
        et_add_name_contact = frag.findViewById(R.id.et_add_name_contact);
        et_add_number_phone = frag.findViewById(R.id.et_add_number_phone);
        iv_add_photo_user = frag.findViewById(R.id.iv_add_photo_user);
        tv_add_contact_title = frag.findViewById(R.id.tv_add_contact_title);


//==============================================================================================
        tv_save.setOnClickListener(v -> {
            String nameContact = et_add_name_contact.getText().toString()
                    .trim();
            String phoneContact = et_add_number_phone.getText().toString()
                    .trim();

            if (!(nameContact.equals("")) && !(phoneContact.equals(""))) {
                if (id.equals("")) {
                    ops = addContact(nameContact, phoneContact);




                    try {
                        ContentProviderResult[] contentProviderResult = getContext().getContentResolver()
                                .applyBatch(ContactsContract.AUTHORITY, ops);


                    } catch (RemoteException | OperationApplicationException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Log.e("ide","Test");
                    editContact(nameContact,phoneContact);
                }
                Log.e("ide","Tes88t");
                ((MainActivity) getActivity()).ll_frag_add_contact.setVisibility(View.GONE);
                ((MainActivity) getActivity()).readContact();

            } else {
                et_add_name_contact.setHintTextColor(getContext().getResources().getColor(R.color.red));
                et_add_number_phone.setHintTextColor(getContext().getResources().getColor(R.color.red));
            }
        });
        updateUi();
//=====================================================================================================================
        return frag;
    }

    public ArrayList<ContentProviderOperation> addContact(String name, String phone) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        int rawContactInsertIndex = ops.size();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name).build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());
        return ops;


    }

    public void editContact(String name, String phone) {

        // Create content values object.
        ContentValues contentValues = new ContentValues();

        // Put new phone number value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);

        // Create query condition, query with the raw contact id.
        StringBuffer whereClauseBuf = new StringBuffer();

        // Specify the update contact id.
        whereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID);
        whereClauseBuf.append("=");
        whereClauseBuf.append(id);

        // Specify the row data mimetype to phone mimetype( vnd.android.cursor.item/phone_v2 )
        whereClauseBuf.append(" and ");
        whereClauseBuf.append(ContactsContract.Data.MIMETYPE);
        whereClauseBuf.append(" = '");
        String mimetype = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE;
        whereClauseBuf.append(mimetype);
        whereClauseBuf.append("'");

        // Specify phone type.
        whereClauseBuf.append(" and ");
        whereClauseBuf.append(ContactsContract.CommonDataKinds.Phone.TYPE);
        whereClauseBuf.append(" = ");
        whereClauseBuf.append(ContactsContract.CommonDataKinds.Phone.TYPE_HOME);

        // Update phone info through Data uri.Otherwise it may throw java.lang.UnsupportedOperationException.
        Uri dataUri = ContactsContract.Data.CONTENT_URI;

        // Get update data count.
        int updateCount = getActivity().getContentResolver().update(dataUri, contentValues, whereClauseBuf.toString(), null);



    }

    public void setParam(String id, String name, String phone, String img) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.img = img;


    }

    public void updateUi() {
        if (id.equals("")) {

            tv_add_contact_title.setText("Создать контакт");
        } else {

            tv_add_contact_title.setText("Редактировать");
        }
        if (img == null) {

            Picasso.get().load(R.drawable.not_user512).into(iv_add_photo_user);
        } else {
            Picasso.get().load(img).into(iv_add_photo_user);
        }
        et_add_name_contact.setText(name);
        et_add_number_phone.setText(phone);


    }


}


