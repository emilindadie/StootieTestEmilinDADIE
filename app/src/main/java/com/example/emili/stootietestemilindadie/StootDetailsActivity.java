package com.example.emili.stootietestemilindadie;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class StootDetailsActivity extends AppCompatActivity  implements OnMapReadyCallback {

    private static final String LOG_TAG = StootDetailsActivity.class.getSimpleName();
    ImageView profilImage;
    TextView prenom, nom, titleStoot, stootAdresse, prix, dateDePublication, budgetEnvisage, typeService;
    String url_stoot_detail = "https://bff-mobile-dev.stootie.com/stoot/mission/";
    private double longitude;
    private double latitude;
    String adresseByIntent;
    String adresse="";
    static String duree = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoot_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);

        //get Longitude and latitude from the previous activity
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);

        //get adresse from previous activity
        adresseByIntent = intent.getStringExtra("adresse");


        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_LONG).show();

        if(id == 0){
            Toast.makeText(this, "extra is null", Toast.LENGTH_LONG).show();
        }
        extractDetails(url_stoot_detail + String.valueOf(id));

        profilImage = (ImageView) findViewById(R.id.detailPhotoUser);
        prenom = (TextView) findViewById(R.id.detailPrenom);
        nom = (TextView) findViewById(R.id.detailNom);
        titleStoot = (TextView) findViewById(R.id.detailTitre);
        stootAdresse = (TextView) findViewById(R.id.adresseDetail);
        prix = (TextView) findViewById(R.id.detailPrix);
        dateDePublication = (TextView) findViewById(R.id.detailDate);
        budgetEnvisage = (TextView) findViewById(R.id.detailBudgetEnvigase);
        typeService = (TextView) findViewById(R.id.detailTypeService);
    }

    public void extractDetails(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //int socketTimeout = 60000;//30 seconds - change to what you want

        //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("Response: ", response);
                handleReponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(LOG_TAG, "reponse don't work");

            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String>  getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept-Version", "2.0.0");
                params.put("X-Request-Id", "5f3b4f3c-44ca-4447-89e4-759e00d9b2b2");
                return params;
            }

        };

        requestQueue.add(stringRequest);

    }

    private void handleReponse(String response) {

        try {
            JSONObject stootDetails = new JSONObject(response);

            //get Stoot title
            String title = stootDetails.getString("title");

            //get Stoot type of service
            String getTypeServicee = stootDetails.getString("stoot_type");

            //get Stoot prix
            int prixStoot = (int) stootDetails.getDouble("unit_price");

            //get Stoot user object
            JSONObject user = stootDetails.getJSONObject("user");

            //get publish date
            String dateDeCreation = stootDetails.getString("created_at");
            getDurationDate(dateDeCreation);

            String stootPrenom = user.getString("firstname");
            String stootNom = user.getString("lastname");

            titleStoot.setText(title);
            dateDePublication.setText(duree);
            prenom.setText(stootPrenom);
            nom.setText(stootNom);
            prix.setText(String.valueOf(prixStoot + " euro"));
            typeService.setText(getTypeServicee);

            if(user.has("profile_picture_url") && user.getString("profile_picture_url") != null) {

                String urlImagestoot = user.getString("profile_picture_url");
                Glide.with(this)
                        .load(urlImagestoot)
                        .override(100, 100)
                        .placeholder(R.drawable.anonyme)
                        .into(profilImage);
            }

            JSONObject answer_wizard = stootDetails.getJSONObject("answer_wizard");
            JSONObject wizard = answer_wizard.getJSONObject("wizard");
            JSONArray questions = wizard.getJSONArray("questions");


            //get Stoot budget
            JSONObject stootGetBudget = questions.getJSONObject(3);
            JSONObject answerBudget = stootGetBudget.getJSONObject("answer");
            int budget = answerBudget.getInt("value");

            //get Stoot adresse
            JSONObject stootGetAdresse = questions.getJSONObject(4);

            JSONObject answerAdresse = stootGetAdresse.getJSONObject("answer");
            JSONObject to = answerAdresse.getJSONObject("to");

            if(!to.isNull("city")){
                adresse = to.getString("city");
            }
            else {
                adresse = adresseByIntent;
            }

            stootAdresse.setText(adresse);
            budgetEnvisage.setText(String.valueOf(budget + " euro"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // and move the map's camera to the same location.
        LatLng stoot = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(stoot)
                .title(adresse));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stoot, 15));

    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void getDurationDate(String Pdate){

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        try {
            Date mDate = date.parse(Pdate);

            Date now = new Date(System.currentTimeMillis());
            long heure = getDateDifference(mDate, now, TimeUnit.HOURS);

            if (heure < 24){

                duree = "il y a "+String.valueOf(heure) + " h";
                // return jour + "j";
            }
            else if (heure >= 24 && heure < 168){
                duree = "il y a "+String.valueOf(heure / 24) + " j";

                // return  jour / 7 + "s";
            }
            else{
                duree = "il y a "+String.valueOf(heure / 168) + " s";
                // return  jour / 7 + "s";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //return duree;
    }

    private static long getDateDifference(Date date1, Date date2, TimeUnit timeUnit){

        long difference = date2.getTime() - date1.getTime();

        return timeUnit.convert(difference, TimeUnit.MILLISECONDS);

    }

}