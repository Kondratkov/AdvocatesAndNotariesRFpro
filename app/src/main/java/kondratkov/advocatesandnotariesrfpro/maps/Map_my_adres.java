package kondratkov.advocatesandnotariesrfpro.maps;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import kondratkov.advocatesandnotariesrfpro.IN;
import kondratkov.advocatesandnotariesrfpro.R;
import kondratkov.advocatesandnotariesrfpro.account.Address;
import kondratkov.advocatesandnotariesrfpro.input.SignUP;
public class Map_my_adres extends Activity implements GoogleMap.OnMapClickListener {

    private GoogleMap map1;
    private static final String DEBUG_TAG = "qwerty";

    public double X = 0;
    public double Y = 0;
    public String STREET;
    public String STREETNUMBER;

    public TextView map_new_tv_address;
    public ProgressBar map_new_progressBar;

    public IN in;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location mLocation;
    private static final long MINIMUM_DISTANCE_FOR_UPDATES = 1000; // в метрах
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 360000; // в мс
    public LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_my_adres);

        in = new IN();
        map_new_tv_address = (TextView) findViewById(R.id.map_new_tv_address);
        map_new_progressBar = (ProgressBar) findViewById(R.id.map_new_progressBar);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment1 = (MapFragment) fragmentManager
                .findFragmentById(R.id.map_new);
        map1 = mapFragment1.getMap();
        map1.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        String provider = mLocationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = mLocationManager.getLastKnownLocation(provider);
        mLocationListener = new MyLocationListener();
        // Регистрируемся для обновлений
        mLocationManager.requestLocationUpdates(provider,
                MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_FOR_UPDATES,
                mLocationListener);
        Looper looper = null;
        mLocationManager.requestSingleUpdate(criteria, mLocationListener, looper);
        if (mLocation != null) {
            Log.d("qwerty", "MY LOCATION " + mLocation.getLatitude() + " " + mLocation.getLongitude());
            latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        }else if(in.get_city().Latitude>-1){
            X = in.get_city().Latitude;
            Y = in.get_city().Longitude;
            latLng = new LatLng(in.get_city().Latitude, in.get_city().Longitude);
        }else{
            Log.d("qwerty", "MY LOCATION NULL");
            latLng = new LatLng(58.75222, 38.61556);
        }
        map1.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        map1.getUiSettings().setCompassEnabled(true);
        map1.getUiSettings().setMyLocationButtonEnabled(true);


        map1.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition camera) {
                //Log.d(DEBUG_TAG, "onCameraChange: " + camera.target.latitude + "," + camera.target.longitude);
                map_new_tv_address.setText("");
                map_new_progressBar.setVisibility(ProgressBar.VISIBLE);
                X = camera.target.latitude;
                Y = camera.target.longitude;
                new FileReadTask().execute();
            }
        });
        //showCurrentLocation(mLocation);
    }

    //////////////////////////////////////////////////////////////////////////////
    protected void showCurrentLocation(Location location) {
        if (location != null) {
            Log.d("qwerty", "MY LOCATION "+location.getLatitude()+" "+location.getLongitude());
        }
        else{
            Log.d("qwerty", "MY LOCATION NULL");
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.removeUpdates(mLocationListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        //mLocationManager.removeGpsStatusListener(mLocationListener);

    }

    // Прослушиваем изменения
    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = "Новое местоположение Долгота: " + location.getLongitude() + " Широта: " + location.getLatitude();
            Toast.makeText(Map_my_adres.this, message, Toast.LENGTH_LONG)
                    .show();
            showCurrentLocation(mLocation);
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(Map_my_adres.this, "Статус провайдера изменился",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(Map_my_adres.this,
                    "Провайдер заблокирован пользователем. GPS выключен",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(Map_my_adres.this,
                    "Провайдер включен пользователем. GPS включён",
                    Toast.LENGTH_LONG).show();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////

    public void start_json(String s){
        try {
            JSONObject json = new JSONObject(s);
            map_new_tv_address.setText(
                    //город
                    json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(3).getString("short_name")+" "+
                            // улица
                            json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(1).getString("short_name")+" "+
                            // дом
                            json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0).getString("short_name")
            );
            STREET = json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(1).getString("short_name") ;
            STREETNUMBER = json.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0).getString("short_name");

            for (int j =0 ; j<json.getJSONArray("results").length(); j++ ){
                Log.d("qwerty", "");
                Log.d("qwerty", "j " + String.valueOf(json.getJSONArray("results").getJSONObject(j).getJSONArray("address_components")));
                for(int i = 0; i<json.getJSONArray("results").getJSONObject(j).getJSONArray("address_components").length(); i++){
                    Log.d("qwerty", "");
                    Log.d("qwerty", i+" "+String.valueOf(json.getJSONArray("results").getJSONObject(j).getJSONArray("address_components").getJSONObject(i)));
                }
            }/**/
            map_new_progressBar.setVisibility(ProgressBar.INVISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.map_new_but_add:
                if(map_new_tv_address.getText().equals("")){
                    Log.d("qwerty","ошибка дружище? tv PUST!");
                }
                else{
                    in.set_coor_x(X);
                    in.set_coor_y(Y);
                    if (String.valueOf(map_new_tv_address.getText()).equals("Поиск адреса")){}
                    else{
                        in.set_address(String.valueOf(map_new_tv_address.getText()));
                    }
                    Address address = new Address();
                    address.Street = STREET;
                    address.StreetNumber = STREETNUMBER;
                    in.setAddress(address);

                    Map_my_adres.this.finish();
                }
                break;
            case R.id.map_new_but_cancel:
                Map_my_adres.this.finish();
                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    private class FileReadTask extends AsyncTask<Void, Void, Void> {

        String textResult;

        @Override
        protected Void doInBackground(Void... params) {

            URL textUrl;

            try {
                textUrl = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + X + "," +
                        Y +"&sensor=false&language=ru");

                BufferedReader bufferReader = new BufferedReader(
                        new InputStreamReader(textUrl.openStream()));

                String StringBuffer;
                String stringText = "";
                while ((StringBuffer = bufferReader.readLine()) != null) {
                    stringText += StringBuffer;
                }
                bufferReader.close();

                textResult = stringText;
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                textResult = e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                textResult = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Log.d("qwerty", "OTVET " + textResult);
            if(textResult!= null) {
                try {
                    JSONObject js = new JSONObject(textResult);
                    if(js.getString("status").equals("OK")){
                        start_json(textResult);
                    }
                    else {
                        Log.d("qwerty", "STATUS "+js.getString("status"));
                        map_new_tv_address.setText("");
                        map_new_progressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }
}


/*public void my_address (){
        if (Geocoder.isPresent()){
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                List<Address> addresses = geocoder.getFromLocation(X, Y, 1);

                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder addressBuilder = new StringBuilder(
                            "Адрес:\n");
                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                        addressBuilder
                                .append(returnedAddress.getAddressLine(i)).append(
                                "\n");
                    }
                    Log.d("qwerty", "address " + addressBuilder.toString());
                } else {
                    Log.d("qwerty","Нет адресов!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("qwerty","Не могу получить адрес!");
            }}
        else{
            Log.d("qwerty","Стучу напрямую!");
        }
    }*/