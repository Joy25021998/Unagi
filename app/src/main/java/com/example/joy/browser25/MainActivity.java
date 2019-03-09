package com.example.joy.browser25;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends FragmentActivity {

    int read_storage_permission = 1;
    int write_storage_permission = 2;
    EditText search12;
    Button go1;
    Spinner spinner1;
    String se = null;
    ImageView gmail1,yahoo12,facebook1,instagram1,you;

    Toolbar toolbar;
    public static Myappdatabase myappdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //linking with view
        gmail1=findViewById(R.id.gmail);
        yahoo12=findViewById(R.id.yahoo1);
        facebook1=findViewById(R.id.facebook);
        instagram1=findViewById(R.id.instagram);
        you=findViewById(R.id.u);
        go1 = findViewById(R.id.go);
        search12 = findViewById(R.id.search);
        spinner1 = findViewById(R.id.dropdown1);

        myappdatabase = Room.databaseBuilder(MainActivity.this,Myappdatabase.class,"userdb").allowMainThreadQueries().build();

        askpermissions(Manifest.permission.READ_EXTERNAL_STORAGE,read_storage_permission);
        askpermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,write_storage_permission);

        //setting up toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.home);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.histry1:
                        startActivity(new Intent(MainActivity.this,histry.class));
                        break;
                    case R.id.bookmarks1:
                        startActivity(new Intent(MainActivity.this , Display_bkmrk.class));
                        break;
                    case R.id.in:
                        startActivity(new Intent(MainActivity.this , incognito.class));
                        finish();
                        break;
                    case R.id.mytabsh1:
                        Intent intent = new Intent(MainActivity.this,NewTab.class);
                        intent.putExtra("title","null");
                        intent.putExtra("url1","null");
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        //spinner
        final String[] menu1 = {"Google" , "Yahoo"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,menu1);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                se = menu1[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //go button event click listner
        go1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(search12.getWindowToken(),0);

                String s = search12.getText().toString();
                String url12 = null;

                if (s.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Search box is empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (s.contains("http://") || s.contains("https://"))
                    {
                        url12 = s;
                    }
                    else if ((s.substring(0,2) == "www" || s.substring(0,2) == "WWW") && (s.endsWith(".com") || s.endsWith(".org") || s.endsWith(".edu") || s.endsWith(".co.in")))
                    {
                        url12 = "http://" + s ;
                    }
                    else
                    {
                        if (se == "Google")
                        {
                            try {
                                url12 = "http://www.google.co.in/search?q=" + URLEncoder.encode(s,"utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (se == "Yahoo")
                        {
                            try {
                                url12 = "http://search.yahoo.com/search?p=" + URLEncoder.encode(s,"utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }



                    Intent intent = new Intent(MainActivity.this,browsing.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("key1",url12);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    search12.setText("");
                    finish();
                }
            }
        });

        gmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , browsing.class);
                intent.putExtra("key1" , "http://www.gmail.com");
                startActivity(intent);
                finish();
            }
        });

        yahoo12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , browsing.class);
                intent.putExtra("key1" , "http://login.yahoo.com");
                startActivity(intent);
                finish();
            }
        });

        facebook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , browsing.class);
                intent.putExtra("key1" , "http://www.facebook.com");
                startActivity(intent);
                finish();
            }
        });

        instagram1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, browsing.class);
                intent.putExtra("key1", "http://www.instagram.com");
                startActivity(intent);
                finish();
            }
        });

        you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , browsing.class);
                intent.putExtra("key1" , "http://www.youtube.com");
                startActivity(intent);
                finish();
            }
        });
    }

    private void askpermissions(String permission1,int requestcode)
    {
        if(ContextCompat.checkSelfPermission(MainActivity.this,permission1) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {permission1},requestcode);
        }
    }
}
