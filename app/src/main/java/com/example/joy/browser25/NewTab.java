package com.example.joy.browser25;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class NewTab extends AppCompatActivity {

    Button xyz;
    String title1,url;
    newTabTable newTabTable;
    public static Myappdatabase myappdatabase;
    List<newTabTable> l1;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tab);

        xyz = findViewById(R.id.add);
        gridView = findViewById(R.id.grids);

        Bundle bundle = getIntent().getExtras();
        title1 = bundle.getString("title");
        url = bundle.getString("url1");

        myappdatabase = Room.databaseBuilder(NewTab.this,Myappdatabase.class,"userdb").allowMainThreadQueries().build();

        xyz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title1.contains("null") || url.contains("null"))
                {
                    startActivity(new Intent(NewTab.this,MainActivity.class));
                    finish();
                }else {
                    newTabTable = new newTabTable();
                    newTabTable.setTitle(title1);
                    newTabTable.setUrl(url);

                    myappdatabase.mydao().insert_tab(newTabTable);
                    startActivity(new Intent(NewTab.this,MainActivity.class));
                    finish();
                }
            }
        });

        l1 = myappdatabase.mydao().tables();

        if (l1.isEmpty())
        {

        }
        else
        {
            gridView.setAdapter(new Custom_adapter(NewTab.this));
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewTab.this,browsing.class);
                intent.putExtra("key1",l1.get(i).getUrl());
                finish();
                startActivity(intent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                File file = new File(getFilesDir(),l1.get(i).getTitle()+".bitmap");
                file.delete();
                myappdatabase.mydao().deleteTab(l1.get(i));

                l1 = myappdatabase.mydao().tables();
                gridView.setAdapter(new Custom_adapter(NewTab.this));
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(title1.contains("null") || url.contains("null"))
        {
            startActivity(new Intent(NewTab.this,MainActivity.class));
            finish();
        }
        else
        {
            File file = new File(getFilesDir(),title1+".bitmap");
            file.delete();
            Intent intent = new Intent(NewTab.this,browsing.class);
            intent.putExtra("key1",url);
            finish();
            startActivity(intent);
        }
    }

    class Custom_adapter extends BaseAdapter
    {
        Context context;

        public Custom_adapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return l1.size();
        }

        @Override
        public Object getItem(int i) {
            return l1.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        class viewholder{

            ImageView imageView;
            TextView textView;

            public viewholder(View view) {

                imageView= view.findViewById(R.id.i);
                textView= view.findViewById(R.id.t);
            }
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View row = view;
            viewholder holder = null;

            if (row == null)
            {
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row=inflater.inflate(R.layout.single_tab,viewGroup,false);
                holder=new viewholder(row);
                row.setTag(holder);
            }
            else
            {
                holder = (viewholder) row.getTag();
            }

            newTabTable temp1 = l1.get(i);
            holder.textView.setText(temp1.getTitle());
            holder.imageView.setImageBitmap(dispaly(temp1.getTitle()));

            return row;
        }
    }



    private Bitmap dispaly(String title1)
    {
        Bitmap b = null;
        try {
            File file = new File(getFilesDir(), title1+".bitmap");
            FileInputStream fileInputStream = new FileInputStream(file);
            b = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

}

