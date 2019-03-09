package com.example.joy.browser25;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Display_bkmrk extends AppCompatActivity {

    public static Myappdatabase myappdatabase;
    List<tbl_bk_mrk> urllist;
    ListView listView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bkmrk);

        //retriving bookmarks from database
        myappdatabase = Room.databaseBuilder(Display_bkmrk.this,Myappdatabase.class,"userdb").allowMainThreadQueries().build();
        urllist = myappdatabase.mydao().bmdis();

        //linking
        listView = findViewById(R.id.list25);
        listView.setAdapter(new customadapter(Display_bkmrk.this));


        //bookmark long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                //setting up remove and edit option
                dialog = new Dialog(Display_bkmrk.this);
                dialog.setContentView(R.layout.bk_opt);
                dialog.setTitle("Select any from beloew:");
                dialog.show();

                //bookmark delete
                TextView del = dialog.findViewById(R.id.del);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myappdatabase.mydao().del_bk(urllist.get(i));
                        dialog.cancel();
                        Toast.makeText(Display_bkmrk.this, "Removed successfully", Toast.LENGTH_SHORT).show();


                        //refreshing bookmark listview
                        urllist.clear();
                        urllist = urllist = myappdatabase.mydao().bmdis();
                        listView.setAdapter(new customadapter(Display_bkmrk.this));
                    }
                });


                //bookmark edit
                TextView ed = dialog.findViewById(R.id.edit);
                ed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //setting up bookamark edit dialogue
                        final Dialog dialog1 = new Dialog(Display_bkmrk.this);
                        dialog1.setContentView(R.layout.save_bkmark);
                        dialog1.setTitle("Bookmark Edit page:");

                        dialog1.show();

                       final EditText title1 , url1;
                        Button ok1 , cancel1;

                        title1 = dialog1.findViewById(R.id.stitle);
                        url1 = dialog1.findViewById(R.id.surl);
                        ok1 = dialog1.findViewById(R.id.save1);
                        cancel1 = dialog1.findViewById(R.id.cancel);

                        final tbl_bk_mrk t = urllist.get(i);
                        title1.setText(t.getTitle());
                        url1.setText(t.getUrl());

                        //cancelling bookmark edit
                        cancel1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.cancel();
                            }
                        });

                        //updating bookmark edit
                        ok1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                tbl_bk_mrk t1 = new tbl_bk_mrk();
                                t1.setId(t.getId());
                                t1.setTitle(title1.getText().toString());
                                t1.setUrl(url1.getText().toString());
                                myappdatabase.mydao().update_bk(t1);
                                Toast.makeText(Display_bkmrk.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                                dialog1.cancel();
                                dialog.cancel();

                                //bookmark list refreshing
                                urllist.clear();
                                urllist = urllist = myappdatabase.mydao().bmdis();

                                listView.setAdapter(new customadapter(Display_bkmrk.this));
                            }
                        });
                    }
                });

                return true;

            }
        });

        //bookmark click listner
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //fetching url from clicked bookmark
                tbl_bk_mrk temnp = urllist.get(i);
                String s = temnp.getUrl();

                //starting browsing browsing activity
                Intent intent = new Intent(Display_bkmrk.this , browsing.class);
                intent.putExtra("key1" , s);
                startActivity(intent);
                finish();
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
            return urllist.size();
        }

        @Override
        public Object getItem(int i) {
            return urllist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public class  view_holder1
        {
            TextView title , url;

            public view_holder1(View view)
            {
                title = view.findViewById(R.id.bktle);
                url = view.findViewById(R.id.bkurl);
            }
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View row = view;
            view_holder1 holder;

            if (row==null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.bkmrk_lst_itm, viewGroup, false);
                holder = new view_holder1(row);
                row.setTag(holder);
            }
            else
            {
                holder= (view_holder1) row.getTag();
            }

            tbl_bk_mrk temp = urllist.get(i);
            holder.title.setText(temp.getTitle());
            holder.url.setText(temp.getUrl());

            return row;
        }
    }
}
