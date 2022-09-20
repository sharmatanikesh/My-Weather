package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView result;
    TextView hum_result2;
    ImageView vehicle;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.temperature);
        hum_result2 = findViewById(R.id.humidity);
        vehicle = findViewById(R.id.vehiclePrefer);


    }

    public void getWeather(View view) {
              String url =" https://api.openweathermap.org/data/2.5/weather?q=delhi&appid=293d5dd5998488808bc4fce3b22ebe57";
          String appId = "293d5dd5998488808bc4fce3b22ebe57";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response){
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    //description
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject jsonArrayWeather  = jsonArray.getJSONObject(0);
                    String description = jsonArrayWeather.getString("description");

                    if(description.equals("mist")){
                        vehicle.setImageResource(R.drawable.icons8_subway_100);
                        Toast.makeText(MainActivity.this, "Subway", Toast.LENGTH_SHORT).show();
                    }

                    if(description.equals("sunny")){
                        vehicle.setImageResource(R.drawable.icons8_bike_64);
                        Toast.makeText(MainActivity.this, "Bike", Toast.LENGTH_SHORT).show();
                    }

                    if(description.equals("cloudy")){
                        vehicle.setImageResource(R.drawable.icons8_bicycle_100);
                        Toast.makeText(MainActivity.this, "Bicycle", Toast.LENGTH_SHORT).show();
                    }

                    if(description.equals("rainy")){
                        vehicle.setImageResource(R.drawable.icons8_car_100);
                        Toast.makeText(MainActivity.this, "Car", Toast.LENGTH_SHORT).show();
                    }

                    if(description.equals("haze")){
                        vehicle.setImageResource(R.drawable.icons8_car_100);
                        Toast.makeText(MainActivity.this, "Car", Toast.LENGTH_SHORT).show();
                    }


                    //temperature
                    JSONObject object = jsonObject.getJSONObject("main");
                    String temperature = object.getString("temp");
                    Double temp = Double.parseDouble(temperature) - 273.15;
                    String feels_like = object.getString("feels_like");
                    Double feelsTemp = Double.parseDouble(feels_like) -273.15;
                    String res = feelsTemp.toString().substring(0,5);

                    result.setText("Temperature: "+ temp.toString().substring(0,5) + " °C\n"
                            + "Feels like: " + res+ " °C" );

                    //pressure and humidity
                    String pressure = object.getString("pressure");
                    String humidity = object.getString("humidity");


                    //wind
                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");


                    //clouds
                    JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");


                    //output
                    hum_result2.setText("Humidity: "+humidity +"%\n" + "Pressure: " +pressure+ "pascal\n"
                                        +"Cloudiness: "+ clouds +"%\n"+ "Wind Speed: " + wind+"m/s\n"
                                        +"Description: " + description);



                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);


    }
}