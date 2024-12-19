package dsa.proyectoandroid.g6.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // IMPORTANTE: Añadido para los logs
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dsa.proyectoandroid.g6.R;
import dsa.proyectoandroid.g6.RetrofitClient;
import dsa.proyectoandroid.g6.UserAdapter;
import dsa.proyectoandroid.g6.UserService;
import dsa.proyectoandroid.g6.models.SavedPreferences;
import dsa.proyectoandroid.g6.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditActivity extends AppCompatActivity {

    private int cont = 0;
    private UserAdapter userAdapter;
    private View btnInsignias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        btnInsignias = findViewById(R.id.btnInsignias);

        btnInsignias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = SavedPreferences.getInstance().getMy_user();
                if (currentUser == null) {
                    Toast.makeText(ProfileEditActivity.this, "Usuario no encontrado. Por favor, inicie sesión de nuevo.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String userId = currentUser.getId();
                if (userId == null || userId.isEmpty()) {
                    Toast.makeText(ProfileEditActivity.this, "El ID de usuario no es válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // LOG DEL ID ENVIADO
                Log.d("ProfileEditActivity", "userId enviado: " + userId);

                Intent intent = new Intent(ProfileEditActivity.this, InsigniasActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    public void changesClick(View v){
        if(cont == 0){
            Toast.makeText(ProfileEditActivity.this,
                    "¿Estás seguro de estos cambios? Presiona otra vez si estás de acuerdo",
                    Toast.LENGTH_LONG).show();
            cont++;
        } else {
            User currentUser = SavedPreferences.getInstance().getMy_user();
            if (currentUser == null) {
                Toast.makeText(ProfileEditActivity.this, "Usuario no encontrado. Por favor, inicie sesión de nuevo.", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = currentUser.getId();
            String nombre = ((EditText) findViewById(R.id.usertbx)).getText().toString();
            String pass1 = ((EditText) findViewById(R.id.passwdtbx)).getText().toString();

            User u = new User(id, nombre, pass1);
            UserService service = RetrofitClient.getRetrofitInstance().create(UserService.class);
            Call<User> call = service.updateUser(id, u);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()) {
                        User changedUser = response.body();
                        SavedPreferences.getInstance().setMy_user(changedUser);
                        Toast.makeText(ProfileEditActivity.this, "Usuario actualizado correctamente", Toast.LENGTH_LONG).show();
                        Intent Actv = new Intent(ProfileEditActivity.this, Dashboard.class);
                        startActivity(Actv);
                        finish();
                    } else if (response.code() == 404) {
                        Toast.makeText(ProfileEditActivity.this, "Usuario no encontrado", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ProfileEditActivity.this, "Error desconocido: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable throwable) {
                    Toast.makeText(ProfileEditActivity.this, "Error de conexión: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
