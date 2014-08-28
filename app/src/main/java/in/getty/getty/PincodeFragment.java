package in.getty.getty;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class PincodeFragment extends Fragment {

    private ProgressDialog dialog = null;
    TextView result;
    private Button get_pin_button;
    private Context context;
    View rootView;


    public PincodeFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_main, container, false);
        result = (TextView)rootView.findViewById(R.id.pintext);

        get_pin_button = (Button)rootView.findViewById(R.id.button_getpincode);
        get_pin_button.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                dialog = ProgressDialog.show( context, "", "Please wait..", true);
                GetCurrentAddress currentadd = new GetCurrentAddress();
                currentadd.execute();
            }
        });
        context = getActivity();



        return rootView;
    }


    public  String getAddress(Context ctx, double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String locality=address.getLocality();
                String city=address.getCountryName();
                String region_code=address.getCountryCode();
                String zipcode=address.getPostalCode();
                double lat =address.getLatitude();
                double lon= address.getLongitude();
                result.append(locality+" ");
                result.append(city+" "+ region_code+" ");
                result.append(zipcode);
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }


    public String getPincode(Context ctx, double latitude, double longitude){
        Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
        String pincode = "-1";
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses.size() > 0){
                Address address = addresses.get(0);
                pincode = address.getPostalCode();
            }
        }catch(IOException e){
            Log.e("Could not find pincode", e.getMessage());
        }
        return pincode;
    }


    private class GetCurrentAddress extends AsyncTask< String, String, String> implements LocationListener {
        String provider;
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("Latitude","disable");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Latitude","enable");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Latitude","status");
        }

        @Override
        protected String doInBackground(String... urls) {
           LocationManager locationManager;
             LocationListener locationListener;
            locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);;
            Criteria c=new Criteria();
            provider=locationManager.getBestProvider(c, false);
            Location location = locationManager.getLastKnownLocation(provider);

            double latitude=12.916523125961666;
            double longitude=77.61959824603072;
            if(location!=null){
                latitude = location.getLatitude();
                longitude= location.getLongitude();
            }
            String pincode= getPincode(context, latitude, longitude);
            return pincode;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            //updated this line
            TextView tt = (TextView) rootView.findViewById(R.id.pintext);
            tt.setText("Sample Text"); //Causing APP TO CRASH
        }
        @Override
        protected void onPostExecute(String resultString) {
            dialog.dismiss();
            TextView tt = (TextView) rootView.findViewById(R.id.pintext);
            tt.setText(resultString);

            Log.d("Pincode ", resultString);
        }



    }
}