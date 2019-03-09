package com.example.joy.browser25;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class incognito extends AppCompatActivity {

    EditText search12;
    Button go1;
    Spinner spinner1;
    String se = null;
    ImageView gmail1,yahoo12,facebook1,instagram1,you;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incognito);

        go1 = findViewById(R.id.go);
        search12 = findViewById(R.id.search);
        spinner1 = findViewById(R.id.dropdown1);
        gmail1=findViewById(R.id.gmail);
        yahoo12=findViewById(R.id.yahoo1);
        facebook1=findViewById(R.id.facebook);
        instagram1=findViewById(R.id.instagram);
        you=findViewById(R.id.u);

        //spinner
        final String[] menu1 = {"Google" , "Yahoo"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(incognito.this,android.R.layout.simple_spinner_dropdown_item,menu1);
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
                    Toast.makeText(incognito.this, "Search box is empty", Toast.LENGTH_SHORT).show();
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



                    Intent intent = new Intent(incognito.this,ibrowsing.class);
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
                Intent intent = new Intent(incognito.this , browsing.class);
                intent.putExtra("key1" , "http://www.gmail.com");
                startActivity(intent);
                finish();
            }
        });

        yahoo12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(incognito.this , browsing.class);
                intent.putExtra("key1" , "http://login.yahoo.com");
                startActivity(intent);
                finish();
            }
        });

        facebook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(incognito.this , browsing.class);
                intent.putExtra("key1" , "http://www.facebook.com");
                startActivity(intent);
                finish();
            }
        });

        instagram1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(incognito.this, browsing.class);
                intent.putExtra("key1", "http://www.instagram.com");
                startActivity(intent);
                finish();
            }
        });

        you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(incognito.this , browsing.class);
                intent.putExtra("key1" , "http://www.youtube.com");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(incognito.this , MainActivity.class));
        finish();
    }
}

