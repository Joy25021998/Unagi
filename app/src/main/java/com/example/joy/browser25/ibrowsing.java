package com.example.joy.browser25;

import android.app.Dialog;
import android.app.DownloadManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.rdrei.android.dirchooser.DirectoryChooserActivity;
import net.rdrei.android.dirchooser.DirectoryChooserConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ibrowsing extends AppCompatActivity {

    EditText title,url;
    WebView webView;
    EditText search1;
    Button go1;
    ProgressBar progressBar1;
    Toolbar toolbar;
    Dialog mydia;
    String currentUrl;
    ImageView back , for1 , home1 , reload , mark1 , share;
    public static Myappdatabase myappdatabase;

    private static final int REQUEST_DIRECTORY = 0;
    String path,file_title;
    TextView te;
    LinearLayout linearLayout;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibrowsing);

        path = "Storage/emulated/0/Download";

        myappdatabase = Room.databaseBuilder(ibrowsing.this,Myappdatabase.class,"userdb").allowMainThreadQueries().build();

        //linking with view
        relativeLayout = findViewById(R.id.sw);
        linearLayout = findViewById(R.id.linearlayout);
        webView = findViewById(R.id.web1);
        search1 = findViewById(R.id.searchtxt);
        go1 = findViewById(R.id.go1);
        progressBar1 = findViewById(R.id.prog);
        toolbar = findViewById(R.id.toolbar2);
        back = findViewById(R.id.back);
        for1 = findViewById(R.id.for1);
        home1 = findViewById(R.id.home1);
        reload = findViewById(R.id.reload);
        mark1 = findViewById(R.id.mark1);
        share = findViewById(R.id.share);

        //fetching url from prev intent
        Bundle bundle = getIntent().getExtras();
        String url1 = bundle.getString("key1");

        //setting up browser
        webView.setWebViewClient(new ourclientview());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        //setting up download manager
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String s, String s1, String s2, String s3, long l) {

                final Dialog dialog = new Dialog(ibrowsing.this);
                dialog.setContentView(R.layout.download_dialog);

                String t = URLUtil.guessFileName(s,null,s1);
                final EditText e = dialog.findViewById(R.id.stitle);
                te = dialog.findViewById(R.id.surl);
                Button b = dialog.findViewById(R.id.save1);
                Button c = dialog.findViewById(R.id.cancel);

                e.setText(t);
                dialog.show();

                te.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dir();
                    }
                });

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        file_title = e.getText().toString();
                        download(s);
                        dialog.dismiss();
                    }
                });

                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });


            }
        });


        //setting up progress bar
        progressBar1.setMax(100);
        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar1.setProgress(newProgress);
            }
        });

        //loading url
        webView.loadUrl(url1);

        //browsing go button event listner
        go1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url12 = null;

                String s = search1.getText().toString();
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
                    try {
                        url12 = "http://www.google.co.in/search?q=" + URLEncoder.encode(s,"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                webView.loadUrl(url12);
            }
        });

        //setting up toolbar
        toolbar.inflateMenu(R.menu.br_tool);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.his1:
                        startActivity(new Intent(ibrowsing.this, histry.class));
                        break;

                    case R.id.book:
                        startActivity(new Intent(ibrowsing.this , Display_bkmrk.class));
                        break;

                    case R.id.incognito:
                        finish();
                        startActivity(new Intent(ibrowsing.this , incognito.class));
                        break;
                }
                return true;
            }
        });

        //page back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    startActivity(new Intent(ibrowsing.this, MainActivity.class));
                    finish();
                }
            }
        });

        //page forword
        for1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoForward()) {
                    webView.goForward();
                }
            }
        });

        //page reload
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
            }
        });

        //save bookmark
        mark1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydia = new Dialog(ibrowsing.this);
                mydia.setContentView(R.layout.save_bkmark);
                mydia.setTitle("Bookmark saving page.");
                mydia.show();

                title = mydia.findViewById(R.id.stitle);
                url = mydia.findViewById(R.id.surl);
                Button cancel = mydia.findViewById(R.id.cancel);
                Button save =  mydia.findViewById(R.id.save1);

                title.setText(webView.getTitle());
                url.setText(webView.getUrl());

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        tbl_bk_mrk a = new tbl_bk_mrk();
                        a.setTitle(title.getText().toString());
                        a.setUrl(url.getText().toString());
                        myappdatabase.mydao().ins_mrk(a);
                        Toast.makeText(ibrowsing.this, "Bookmark added", Toast.LENGTH_SHORT).show();
                        mydia.cancel();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mydia.cancel();
                    }
                });
            }
        });

        //setting up sharing url
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT , currentUrl);
                    intent.putExtra(Intent.EXTRA_SUBJECT , "Copied url.");
                    startActivity(Intent.createChooser(intent , "Share using.."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //directing to home page
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ibrowsing.this , MainActivity.class));
                finish();

            }
        });
    }


    //oncreate out


    //back press listner
    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {
            finish();
            startActivity(new Intent(ibrowsing.this,MainActivity.class));
        }


    }

    public class ourclientview extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            search1.setText(url);
            Thistry thistry = new Thistry();
            thistry.setHurl(url);
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            super.onPageStarted(view, url, favicon);
            progressBar1.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
            progressBar1.setVisibility(View.GONE);
            currentUrl = url;
        }


    }

    void download(String s)
    {
        DownloadManager.Request myrequest = new DownloadManager.Request(Uri.parse(s));
        myrequest.allowScanningByMediaScanner();
        myrequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        path = path.substring(19);
        myrequest.setDestinationInExternalPublicDir(path,file_title);

        DownloadManager mymanager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        mymanager.enqueue(myrequest);

        Toast.makeText(ibrowsing.this, "downloading "+file_title, Toast.LENGTH_SHORT).show();
    }


    //dir chooser
    public void dir()
    {
        final Intent chooserIntent = new Intent(this, DirectoryChooserActivity.class);
        final DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                .newDirectoryName("dirChoosersample")
                .allowReadOnlyDirectory(true)
                .allowNewDirectoryNameModification(true)
                .initialDirectory(Environment.getExternalStorageDirectory().getPath())
                .build();

        chooserIntent.putExtra(DirectoryChooserActivity.EXTRA_CONFIG,config);

        startActivityForResult(chooserIntent,REQUEST_DIRECTORY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_DIRECTORY)
        {
            if (resultCode == DirectoryChooserActivity.RESULT_CODE_DIR_SELECTED)
            {
                handleDirectoryChoice(data.getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR));
            }
            else
            {
                Toast.makeText(this, "error 404:dir not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleDirectoryChoice(String stringExtra)
    {
        path = stringExtra;
        te.setText(path);
    }


    private Bitmap takescreenshot(View v)
    {
        Bitmap screenschot = null;

        try {
            int width = v.getMeasuredWidth();
            int height = v.getMeasuredHeight();

            screenschot = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

            Canvas c = new Canvas(screenschot);
            v.draw(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return screenschot;
    }

    private String save(Bitmap bm)
    {
        File file = getFilesDir();
        FileOutputStream fileOutputStream = null;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {

            fileOutputStream = openFileOutput(webView.getTitle()+".bitmap" , Context.MODE_PRIVATE);
            bm.compress(Bitmap.CompressFormat.JPEG,50,bao);
            fileOutputStream.write(bao.toByteArray());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.toString();
    }
}
