package edu.fbansept.crud.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import edu.fbansept.crud.R;
import edu.fbansept.crud.model.Utilisateur;

public class UtilisateurActivity extends AppCompatActivity {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur);

        Intent intent = getIntent();
        Utilisateur utilisateur = (Utilisateur) intent.getSerializableExtra("utilisateur");
        Toast.makeText(this, utilisateur.getNom(), Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);

        telechargeGoogleMap();
    }

    private void telechargeGoogleMap() {
        mapFragment.getMapAsync(
                map -> {
                    googleMap = map;
                    googleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                    LatLng latlng = new LatLng(47.6367, 6.68642);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                }
        );
    }











































    /*

        private int PERMISSION_KEY = 1234;
    private boolean demandePermission = false;

    private double lastLatitude = 0;
    private double lastLongitude = 0;

    LocationManager lm;

    @SuppressLint("MissingPermission")
    private void telechargeGoogleMap() {
        mapFragment.getMapAsync(map -> {
            googleMap = map;
            googleMap.setMyLocationEnabled( true );
        });
    }

    @Override
    //@SuppressWarnings( "MissingPermission" ) // (si on cible les anciennes OS)
    protected void onResume() {
        super.onResume();
        if(!demandePermission) {
            verificationPermission();
        } else {
            demandePermission = false;
        }
    }

    private void verificationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            demandePermission = true;
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    PERMISSION_KEY);
            return;

        } else {
            activationLocalistation();
        }

    }

    //Attention nous nous autorisons à utiliser cette annotation car nous avons bien vérifier qu'en aucun cas
    //nous ne pouvons appeller cette méthode si les droits n'ont pas été acceptés...
    @SuppressLint("MissingPermission")
    private void activationLocalistation() {

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        }

        if (lm.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 10000, 0, this);
        }

        //location par adresse IP
        if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
        }

        if(googleMap == null) {
            telechargeGoogleMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_KEY) {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED){
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing Activity")
                        .setMessage("Vous êtes sur de ne pas vouloir profiter de cette super fonctionnalité :'( ?")
                        .setPositiveButton("Ok... je l'active ...", (dialog, which) -> verificationPermission())
                        .setNegativeButton("Non vraiment pas merci...", (dialog, which) -> finish())
                        .show();
            } else {
                activationLocalistation();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (lm != null) {
            lm.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        lastLatitude = location.getLatitude();
        lastLongitude = location.getLongitude();
        Toast.makeText(this, location.getLatitude() + ":" + location.getLongitude(), Toast.LENGTH_SHORT ).show();

        if(googleMap != null) {
            LatLng latLng = new LatLng(lastLatitude, lastLongitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
     */
}
