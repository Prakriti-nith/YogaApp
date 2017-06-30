package gupta.p.yogaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import gupta.p.yogaapp.R;
import gupta.p.yogaapp.helper.DownloadHelper;

public class Dashboard extends AppCompatActivity {

    private ImageView img1, img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().setTitle("Select Category");

        init();
    }

    private void init() {
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);

        DownloadHelper.loadImageWithGlide(this, R.drawable.logo, img1);
        DownloadHelper.loadImageWithGlide(this, R.drawable.images, img2);
    }

    public void yoga(View view) {
        Intent i = new Intent(this, YogaListingActivity.class);
        i.putExtra("which", "Yoga");
        startActivity(i);
    }

    public void cardio(View view) {
        Intent i = new Intent(this, YogaListingActivity.class);
        i.putExtra("which", "Exercise");
        startActivity(i);
    }

}
