package dsa.proyectoandroid.g6.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import dsa.proyectoandroid.g6.R;
import dsa.proyectoandroid.g6.models.Badge;

public class BadgeAdapter extends android.widget.ArrayAdapter<Badge> {
    private final Context context;
    private final List<Badge> badges;

    public BadgeAdapter(@NonNull Context context, @NonNull List<Badge> badges) {
        super(context, R.layout.item_badge, badges);
        this.context = context;
        this.badges = badges;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_badge, parent, false);
        }

        // Obtener elementos de la vista
        ImageView badgeImage = convertView.findViewById(R.id.badgeImage);
        TextView badgeName = convertView.findViewById(R.id.badgeName);

        // Obtener el badge actual
        Badge badge = badges.get(position);

        // Configurar los datos
        badgeName.setText(badge.getName());

        // Cargar la imagen manualmente
        new ImageLoaderTask(badgeImage).execute(badge.getAvatar());

        return convertView;
    }

    // Clase interna para cargar la imagen en segundo plano
    private static class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageView;

        public ImageLoaderTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            Bitmap bitmap = null;

            try {
                // Crear conexión HTTP y descargar la imagen
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
