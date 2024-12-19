package dsa.proyecto.G4.services;

import dsa.proyecto.G4.UserManager;
import dsa.proyecto.G4.UserManagerImpl;
import dsa.proyecto.G4.models.Badge;
import dsa.proyecto.G4.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.ArrayList;

@Api(value = "/usuarios", description = "Endpoint to User Service")
@Path("/usuarios")
public class UserService {

    private UserManager userManager;

    public UserService() {
        this.userManager = UserManagerImpl.getInstance();

        // Datos de ejemplo
        if (userManager.countUsers()==0) {
            this.userManager.addUsuario(new User("13", "Alice", "123"));
            this.userManager.addUsuario(new User("14", "Bob", "456"));
        }
    }

    @GET
    @ApiOperation(value = "Obtener todos los usuarios", notes = "Devuelve una lista de todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class, responseContainer = "List"),
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios() {
        List<User> usuarios = this.userManager.getUsuarios();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(usuarios) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Obtener un usuario por nombre", notes = "Devuelve el usuario con el nombre especificado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Path("/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarioPorNombre(@PathParam("nombre") String nombre) {
        User user = this.userManager.getUsuarioPorNombre(nombre);
        if (user == null) return Response.status(404).build();
        return Response.status(200).entity(user).build();
    }

    @POST
    @ApiOperation(value = "Crear un nuevo usuario", notes = "Crea un nuevo usuario con la información proporcionada")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code= 406,message = "Ya existe un usuario con tu nombre y contraseña elije otra contraseña"),
            @ApiResponse(code = 500, message = "Error de validación")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newUsuario(User usuario) {
        //cambios 4.7
        if (usuario.getNombre() == null || usuario.getContraseña() == null) {
            return Response.status(500).entity("Error de validación").build();
        } else if (this.userManager.buscaUsuario(usuario)== null) {
            return Response.status(406).entity("Usuario  existente").build();
        }else {
            this.userManager.addUsuario(usuario);
            return Response.status(201).entity(usuario).build();
        }
    }

    @POST
    @Path("/login")
    @ApiOperation(value = "Iniciar sesión", notes = "Valida las credenciales del usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login exitoso"),
            @ApiResponse(code = 401, message = "Credenciales incorrectas")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User credentials) {
        // Busca el usuario por nombre
        User user = this.userManager.getUsuarioPorNombre(credentials.getNombre());

        // Verifica si el usuario existe y si la contraseña coincide
        if (user != null && user.getContraseña().equals(credentials.getContraseña())) {
            // Si la autenticación es exitosa
            return Response.status(200).entity(user).build();
        } else {
            // Si las credenciales son incorrectas
            return Response.status(401).entity("Credenciales incorrectas").build();
        }
    }

    @PUT
    @ApiOperation(value= "update a User", notes = "asdasd")
    @ApiResponses(value = {
           @ApiResponse(code=201,message = "Successful"),
           @ApiResponse(code=404, message = "User not found")
    })
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") String id, User user){//cambios 4.7
        User u = this.userManager.updateUser(id,user);
        if(u == null) return Response.status(404).build();
        return Response.status(201).entity(u).build();
    }

    @GET
    @Path("/{id}/badges")
    @ApiOperation(value = "Obtener insignias de un usuario por ID", notes = "Devuelve las insignias asociadas al usuario especificado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Badge.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserBadges(@PathParam("id") String id) {
        List<Badge> badges = new ArrayList<>();

        // Datos de prueba del enunciado, pruebo con id=13 y id=14 ya que no tengo un put para añadir insignias, añado tambien algunas fotos extras
        if (id.equals("13")) {
            badges.add(new Badge("Master del universo", "https://cdn.pixabay.com/photo/2017/07/11/15/51/kermit-2493979_1280.png"));
            badges.add(new Badge("Reina de DSA", "https://img.freepik.com/vector-gratis/elegante-reina-corona-oro_1308-174936.jpg"));
        } else if (id.equals("14")) {
            badges.add(new Badge("Becario enfurecido", "https://cdn.pixabay.com/photo/2017/07/11/15/51/kermit-2493979_1280.png"));
            badges.add(new Badge("El Bachiller", "https://images.vexels.com/content/205462/preview/pile-of-books-illustration-b5a415.png"));
        } else {
            return Response.status(404).entity("Usuario no encontrado").build();
        }

        // Usar GenericEntity para ayudar a Jersey a reconocer el tipo de la lista
        GenericEntity<List<Badge>> entity = new GenericEntity<List<Badge>>(badges) {};
        return Response.ok(entity).build();
    }




}
