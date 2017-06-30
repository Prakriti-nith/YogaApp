package gupta.p.yogaapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import gupta.p.yogaapp.R;
import gupta.p.yogaapp.adapter.MyYogaAdapter;
import gupta.p.yogaapp.helper.URLHelper;
import gupta.p.yogaapp.pojo.YogaListPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gupta.p.yogaapp.activity.HTMLDisplayActivity;

public class YogaListingActivity extends AppCompatActivity {

    private ListView listView;

    private ArrayList<YogaListPojo> arrayList = new ArrayList<>();

    private MyYogaAdapter myYogaAdapter;
    private String which;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_listing);

        which = getIntent().getStringExtra("which");
        getSupportActionBar().setTitle(which);

        init();
        methodListener();
        myYogaAdapter = new MyYogaAdapter(this, R.layout.list_item, arrayList);
        listView.setAdapter(myYogaAdapter);

        fetchDataFromServer();
    }

    private void init() {
        listView = (ListView) findViewById(R.id.listView);
    }

    private void methodListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String description = arrayList.get(position).getDescription();

                Intent i = new Intent(YogaListingActivity.this, HTMLDisplayActivity.class);
                i.putExtra("des", description);
                i.putExtra("title", arrayList.get(position).getCategory());
                startActivity(i);


            }
        });
    }

    private void fetchDataFromServer() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();

        StringRequest request =
                new StringRequest(Request.Method.POST, URLHelper.YOGA_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.cancel();
                                parseYogaJSON(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.cancel();
                                Toast.makeText(YogaListingActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        Log.d("1234", "getParams: " + which);
                        map.put("cat", which);
                        return map;
                    }
                };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    private void parseYogaJSON(String response) {
        try {
            JSONObject object = new JSONObject(response);

            JSONArray array = object.getJSONArray("item");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                YogaListPojo pojo = new YogaListPojo();

                pojo.setId(jsonObject.getString("id"));
                pojo.setCategory(jsonObject.getString("category"));
                pojo.setDescription(jsonObject.getString("description"));
                pojo.setImg(jsonObject.getString("img"));

                arrayList.add(pojo);
            }

            myYogaAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}