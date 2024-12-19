package dsa.proyectoandroid.g6.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import dsa.proyectoandroid.g6.Adapters.BadgeAdapter;
import dsa.proyectoandroid.g6.R;
import dsa.proyectoandroid.g6.RetrofitClient;
import dsa.proyectoandroid.g6.UserService;
import dsa.proyectoandroid.g6.models.Badge;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsigniasActivity extends AppCompatActivity {

    private ListView listViewInsignias;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insignias);

        listViewInsignias = findViewById(R.id.listViewInsignias);
        progressBar = findViewById(R.id.progressBar);

        String userId = getIntent().getStringExtra("userId");

        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "El ID de usuario no es válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        obtenerInsignias(userId);
    }

    private void obtenerInsignias(String userId) {
        progressBar.setVisibility(View.VISIBLE);
        UserService userService = RetrofitClient.getRetrofitInstance().create(UserService.class);
        Call<List<Badge>> call = userService.getUserBadges(userId);

        call.enqueue(new Callback<List<Badge>>() {
            @Override
            public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Badge> insignias = response.body();
                    mostrarInsigniasEnLista(insignias);
                } else {
                    Toast.makeText(InsigniasActivity.this, "No se encontraron insignias", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Badge>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InsigniasActivity.this, "Error al obtener insignias: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarInsigniasEnLista(List<Badge> insignias) {
        if (insignias == null || insignias.isEmpty()) {
            Toast.makeText(this, "No se encontraron insignias para mostrar", Toast.LENGTH_SHORT).show();
            return;
        }

        BadgeAdapter adapter = new BadgeAdapter(this, insignias);
        listViewInsignias.setAdapter(adapter);
    }
}
