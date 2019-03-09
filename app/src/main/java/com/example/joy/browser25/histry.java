package com.example.joy.browser25;

import android.app.Dialog;
import android.app.ListActivity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class histry extends AppCompatActivity {

    ListView listView1;
    public static Myappdatabase myappdatabase;
    List<Thistry> urllist;
    ArrayList<Thistry> stacklist1;
    Dialog mydialog;
    Button ok1 , cancel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histry);

        //retriving histries from database
        myappdatabase = Room.databaseBuilder(histry.this,Myappdatabase.class,"userdb").allowMainThreadQueries().build();
        urllist = myappdatabase.mydao().display();

        stacklist1 = new ArrayList<>();

        //creating stack form of histries
         for (Thistry temp : urllist)
         {
             stacklist1.add(0 , temp);
         }

         //setting up list view
         listView1 = findViewById(R.id.list23);
         listView1.setAdapter(new customadapter(histry.this));

         //loading urls from history
         listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                 TextView t = view.findViewById(R.id.tt);
                 Intent intent = new Intent(histry.this , browsing.class);
                 intent.putExtra("key1" , t.getText().toString());
                 startActivity(intent);
                 finish();
             }
         });


         //deleting history
         listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                 //setting up dialogue box for deleting
                 mydialog = new Dialog(histry.this);
                 mydialog.setContentView(R.layout.del_alert);
                 mydialog.show();

                 //linking buttons in dialogue box
                 ok1 = mydialog.findViewById(R.id.ok);
                 cancel1 = mydialog.findViewById(R.id.cancel);

                 //ok event listner
                 ok1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                         //deleting url from database
                         myappdatabase.mydao().del_his(stacklist1.get(i));

                         //refreshing listview
                         urllist = myappdatabase.mydao().display();

                         stacklist1 = new ArrayList<>();

                         for (Thistry temp : urllist)
                         {
                             stacklist1.add(0 , temp);
                         }
                         listView1.setAdapter(new customadapter(histry.this));
                         mydialog.cancel();
                     }
                 });

                 //cancel event listner
                 cancel1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         mydialog.cancel();
                     }
                 });

                 return true;

             }
         });


    }

    //custom adapter
    class customadapter extends BaseAdapter
    {

        Context context;

        public customadapter(Context context) {
            this.context = context;
        }


        @Override
        public int getCount() {
            return stacklist1.size();
        }

        @Override
        public Object getItem(int i) {
            return stacklist1.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public class  view_holder
        {
            TextView textView;

            public view_holder(View view)
            {
                textView = view.findViewById(R.id.tt);
            }
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View row = view;
            view_holder holder = null;

            if (row==null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.hist, viewGroup, false);
                holder = new view_holder(row);
                row.setTag(holder);
            }
            else
            {
                holder= (view_holder) row.getTag();
            }

            Thistry temp=stacklist1.get(i);
            holder.textView.setText(temp.getHurl());

            return row;
        }
    }


}
