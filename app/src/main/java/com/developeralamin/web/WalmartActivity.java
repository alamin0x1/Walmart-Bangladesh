package com.developeralamin.web;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.developeralamin.web.databinding.ActivityWalmartBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WalmartActivity extends AppCompatActivity {

    private ActivityWalmartBinding binding;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalmartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("walmart");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String webUrl = snapshot.getValue(String.class);

                binding.webView.loadUrl(webUrl);

                binding.webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                binding.webView.setFitsSystemWindows(false);
                binding.webView.setVerticalScrollBarEnabled(false);
                binding.webView.setPadding(15, 15, 15, 15);

                binding.webView.getSettings().setLoadsImagesAutomatically(true);
                binding.webView.getSettings().setJavaScriptEnabled(true);
                binding.webView.getSettings().setAppCacheEnabled(true);
                binding.webView.getSettings().setAllowFileAccess(true);
                binding.webView.getSettings().setLoadWithOverviewMode(true);
                binding.webView.getSettings().setUseWideViewPort(true);
                binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
                binding.webView.setWebViewClient(new WebViewClient());
                binding.webView.setWebChromeClient(new WebChromeClient());

                binding.webView.getSettings().setBlockNetworkLoads(false);
                binding.webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
                binding.webView.getSettings().setDomStorageEnabled(true);


                binding.webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        binding.progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail to get URL.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}