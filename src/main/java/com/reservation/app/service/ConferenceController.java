package com.reservation.app.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.reservation.app.sesion.Sesion;
import com.reservation.app.usuario.Usuario;
import com.reservation.app.registroasistencia.RegistroAsistencia;
import com.reservation.app.exceptions.UsuarioNoEncontradoException;

import java.util.List;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;

//import javax.crypto.SecretKey;




@RestController
@RequestMapping("/api/conferencia")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    // Inyección de servicios
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    // Endpoints para Sesion
    @PostMapping("/sesiones")
    public Sesion crearSesion(@RequestBody Sesion sesion) {
        return conferenceService.guardarSesion(sesion);
    }

    @GetMapping("/sesiones/{id}")
    public Sesion obtenerSesion(@PathVariable Long id) {
        return conferenceService.obtenerSesionPorId(id);
    }

    @PutMapping("/sesiones/{id}")
    public Sesion actualizarSesion(@PathVariable Long id, @RequestBody Sesion sesion) {
        sesion.setId(id);
        return conferenceService.actualizarSesion(sesion);
    }

    @DeleteMapping("/sesiones/{id}")
    public void eliminarSesion(@PathVariable Long id) {
        conferenceService.eliminarSesion(id);
    }

    // Endpoints para RegistroAsistencia
    @PostMapping("/registros")
    public RegistroAsistencia crearRegistroAsistencia(@RequestBody RegistroAsistencia registroAsistencia) {
        //Usuario usuario = registroAsistencia.getUsuario();
        Sesion sesion = registroAsistencia.getSesion();

        // Obtener el ID del usuario desde el registro de asistencia
        Long userId = registroAsistencia.getUsuario().getId();
    
        // Obtener el usuario utilizando el ID
        Usuario usuario = conferenceService.obtenerUsuarioPorId(userId);
    
        // Validar si el usuario existe
        if(usuario == null) {
        // Manejar el caso donde el usuario no existe
        // Puedes lanzar una excepción, devolver un error, etc.
        throw new UsuarioNoEncontradoException("El usuario con ID " + userId + " no se encuentra en la base de datos.");
        }
    
        // Asignar el usuario al registro de asistencia
        registroAsistencia.setUsuario(usuario);

        conferenceService.validarRegistroUsuario(usuario, sesion);
        return conferenceService.guardarRegistroAsistencia(registroAsistencia);
    }

    @GetMapping("/registros/{id}")
    public RegistroAsistencia obtenerRegistroAsistencia(@PathVariable Long id) {
        return conferenceService.obtenerRegistroAsistenciaPorId(id);
    }

    @PutMapping("/registros/{id}")
    public RegistroAsistencia actualizarRegistroAsistencia(@PathVariable Long id, @RequestBody RegistroAsistencia registroAsistencia) {
        registroAsistencia.setId(id);
        return conferenceService.actualizarRegistroAsistencia(registroAsistencia);
    }

    @DeleteMapping("/registros/{id}")
    public void eliminarRegistroAsistencia(@PathVariable Long id) {
        conferenceService.eliminarRegistroAsistencia(id);
    }

    // Endpoints para Usuario
    @PostMapping("/usuarios")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return conferenceService.guardarUsuario(usuario);
    }

    @GetMapping("/usuarios/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return conferenceService.obtenerUsuarioPorId(id);
    }

    @PutMapping("/usuarios/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return conferenceService.actualizarUsuario(usuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        conferenceService.eliminarUsuario(id);
    }

    @GetMapping("/usuarios")
    public List<Usuario> obtenerListaUsuarios() {
        return conferenceService.getUsersSortedByName();
    }

    // Endpoint para buscar usuario por email
    @GetMapping("/usuarios/email/{email}")
    public Usuario obtenerUsuarioPorEmail(@PathVariable String email) {
        return conferenceService.findUserByEmail(email);
    }

    
    

    // Endpoint para validar el token
//@RestController
//@RequestMapping("/api/auth")

//public class AuthController {

   
//    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

//    @PostMapping("/login")
//    public String login(@RequestBody Usuario usuario) {
        // Validar credenciales
//        if (usuario.getCorreoElectronico().equals("admin") && usuario.getContrasena().equals("admin")) {
            // Generar token
            //byte[] key = "mySecretKey".getBytes();
//            String token = Jwts.builder()
//                    .setSubject(usuario.getCorreoElectronico())
//                    .signWith(key)
//                    .compact();
//            return token;
//        } else {
//            return "Credenciales incorrectas";
//        }
//    }

    
//}

}
    