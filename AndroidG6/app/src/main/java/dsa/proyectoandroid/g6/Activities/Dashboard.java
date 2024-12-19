package dsa.proyectoandroid.g6.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dsa.proyectoandroid.g6.R;
import dsa.proyectoandroid.g6.models.SavedPreferences;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ((TextView) findViewById(R.id.userinfotV)).setText("User: " + SavedPreferences.getInstance().getMy_user().getName());
    }


    public void onClickShop(View v){
        Intent Actv = new Intent(Dashboard.this, shop.class);
        startActivity(Actv);
    }

    public void onClickProfile(View v){
        Intent Actv = new Intent(Dashboard.this, ProfileEditActivity.class);
        startActivity(Actv);
        finish();
    }

}