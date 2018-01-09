package com.example.jan.gps_koordinate;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

public class DrawerTest extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_kviz) {
            Intent kviz = new Intent(DrawerTest.this, KvizActivity.class);
            startActivity(kviz);
        } else if (id == R.id.nav_rezultati) {
            Intent rezultati = new Intent(DrawerTest.this, RezultatiActivity.class);
            startActivity(rezultati);

        } else if (id == R.id.nav_simulacije) {
            Intent simulacije = new Intent(DrawerTest.this, SimulacijeActivity.class);
            startActivity(simulacije);
        } else if (id == R.id.nav_zemljevid) {
            Intent zemljevid = new Intent(DrawerTest.this, ZemljevidActivity.class);
            startActivity(zemljevid);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
