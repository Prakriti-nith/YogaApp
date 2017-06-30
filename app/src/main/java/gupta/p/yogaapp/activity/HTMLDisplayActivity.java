package gupta.p.yogaapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import gupta.p.yogaapp.R;

public class HTMLDisplayActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmldisplay);



        init();

        String description = getIntent().getStringExtra("des");

        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        if (description != null)
        {
            webView.loadDataWithBaseURL(null, description, "text/html", "utf-8", null);
        }
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webView);
    }
}
