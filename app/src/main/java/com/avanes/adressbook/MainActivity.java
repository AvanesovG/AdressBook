package com.avanes.adressbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tv_name_user, tv_exit, tv_sing_out;
    LinearLayout ll_bt_top_menu, ll_frag_add_contact;
    ConstraintLayout cl_menu, cl_panel_find, cl_include_menu;
    ImageView iv_user, iv_bt_find, iv_bt_back, iv_add_contact;
    GoogleSignInClient googleSignInClient;
    TextView tv_add_contact;
    EditText et_find_contact;
    List<ClListContact> listContacts;


    Context context;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((requestCode == 100) && (grantResults[0] == 0)) {
            readContact();
        } else {
            finish();
        }
    }

    public void readContact() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Cursor cursor = this.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null);
            listContacts.clear();

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {


                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String nameUser = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String phoneUser = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    String imgContact = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                    if (Integer.parseInt(phoneUser) > 0) {
                        Cursor cursor2 = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.Contacts.Photo.CONTACT_ID + " = ?", new String[]{id}, null);
                        Log.e("tesr", "" + nameUser);
                        String phone = "";
                        while (cursor2.moveToNext()) {
                            //  String NORMALIZED_NUMBER = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
                            phone = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));

                        }
                        Log.e("test", "" + phone);
                        Log.e("photo", "" + imgContact);
                        listContacts.add(new ClListContact(id, nameUser, phone, imgContact));

                    }

                }
            }


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentListContact fragmentListContact = (FragmentListContact) fragmentManager.findFragmentById(R.id.ll_frag_list_contact);
            //  if (fragmentListContact != null) {
            fragmentListContact.setAdapter(listContacts);
            //  }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//Делаем прозрачный StatusBar и делаем приложение на весь экран.
//---------------------------------------------------------------------
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0x00000000);
            Log.e("test", "test");
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

//----------------------------------------------------------------------
        listContacts = new ArrayList<>();

//----------------------------------------------------------------------
        tv_name_user = findViewById(R.id.tv_name_user);
        ll_bt_top_menu = findViewById(R.id.ll_bt_top_menu);
        cl_menu = findViewById(R.id.cl_menu);
        iv_user = findViewById(R.id.iv_user);
        tv_exit = findViewById(R.id.tv_exit);
        tv_sing_out = findViewById(R.id.tv_sing_out);
        tv_add_contact = findViewById(R.id.tv_add_contact);
        ll_frag_add_contact = findViewById(R.id.ll_frag_add_contact);
        iv_bt_find = findViewById(R.id.iv_bt_find);
        iv_bt_back = findViewById(R.id.iv_bt_back);
        cl_include_menu = findViewById(R.id.cl_include_menu);
        cl_panel_find = findViewById(R.id.cl_panel_find);
        et_find_contact = findViewById(R.id.et_find_contact);
        tv_add_contact = findViewById(R.id.tv_add_contact);
        iv_add_contact = findViewById(R.id.iv_add_contact);
//------------------------------------------------------------------------

        //  Bundle arguments = getIntent().getExtras();
        //  String name_user = arguments.getString("name_user");
        //  String img_user = arguments.getString("img_user");
        //  tv_name_user.setText(name_user);
        //  if (img_user.equals("")) {
        //      Picasso.get().load(R.drawable.not_user).into(iv_user);
        //  } else {
        //      Picasso.get().load(img_user).into(iv_user);
        //  }

        //  GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        //          .requestEmail()
        //          .build();
        //  googleSignInClient = GoogleSignIn.getClient(this, gso);


        //    System.out.println(name_user);

        //    ll_bt_top_menu.setOnClickListener(v -> {
        //        if (cl_menu.getVisibility() == View.GONE) {
        //            cl_menu.setVisibility(View.VISIBLE);
        //        } else {
        //            cl_menu.setVisibility(View.GONE);
        //        }
        //    });
////-------------------------------------------------------------------------------
        //    tv_exit.setOnClickListener(v -> finish());
        //-------------------------------------------------------------------------
        tv_sing_out.setOnClickListener(v -> googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent intent = new Intent(getApplicationContext(), AuchActivity.class);
            startActivity(intent);
            finish();
        }));
//----------------------------------------------------------------------------

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentListContact fragmentListContact = new FragmentListContact();
        fragmentTransaction.replace(R.id.ll_frag_list_contact, fragmentListContact);
        fragmentTransaction.commitNow();

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                ll_frag_add_contact.setVisibility(View.GONE);
                iv_add_contact.setVisibility(View.VISIBLE);

            }
        });
//--------------------------------------------------------------------------
        context = this;
        tv_add_contact.setOnClickListener(v -> {
                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                    FragmentAddContact fragmentAddContact = new FragmentAddContact();
                    fragmentTransaction1.replace(R.id.ll_frag_add_contact, fragmentAddContact);
                    fragmentTransaction1.addToBackStack(null);
                    fragmentTransaction1.commit();
                    ll_frag_add_contact.setVisibility(View.VISIBLE);
                    tv_add_contact.setVisibility(View.GONE);

                }
        );
//---------------------------------------------------------------------
        iv_bt_find.setOnClickListener(v -> {

            cl_include_menu.setVisibility(View.GONE);
            cl_panel_find.setVisibility(View.VISIBLE);
            et_find_contact.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(et_find_contact, InputMethodManager.SHOW_IMPLICIT);
        });

        //---------------------------------------------------------------


        iv_bt_back.setOnClickListener(v -> {
            cl_panel_find.setVisibility(View.GONE);
            cl_include_menu.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        });

        //---------------------------------------------------------------
        et_find_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().toLowerCase().trim();
                Log.e("tesr111", "" + text);
                List<ClListContact> listContact = new ArrayList<>();
                listContacts.forEach(name -> {
                    if (name.getName().toLowerCase().trim().contains(text)) {
                        listContact.add(name);
                    }
                });
                // for (int i=0;i< listContacts.size();i++) {

                //       if (listContacts.get(i).name.contains(text)) {
                //           listContact.add(listContacts.get(i));
                //       }

                //   }
                Log.e("size", "" + listContact.size());
                Log.e("size2", "" + listContacts.size());
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentListContact fragmentListContact = (FragmentListContact) fragmentManager
                        .findFragmentById(R.id.ll_frag_list_contact);
                //  if (fragmentListContact != null) {
                fragmentListContact.setAdapter(listContact);
                //  }


            }
        });

        iv_add_contact.setOnClickListener(v -> {
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            FragmentAddContact addContact = new FragmentAddContact();
            fragmentTransaction1.replace(R.id.ll_frag_add_contact, addContact);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();
            ll_frag_add_contact.setVisibility(View.VISIBLE);
            iv_add_contact.setVisibility(View.GONE);



        });

    }


}