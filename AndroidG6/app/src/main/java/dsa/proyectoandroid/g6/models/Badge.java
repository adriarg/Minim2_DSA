package dsa.proyectoandroid.g6.models;

public class Badge {
    private String name;
    private String avatar;

    // Constructor vacío requerido por Retrofit y deserializadores
    public Badge() {}

    // Constructor con parámetros
    public Badge(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Badge{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
