package com.example.topicosprueba;

public class Usuario {
    String nota;
    String nombre;
    String telefono;
    String email;

    public Usuario() {
    }

    public Usuario(String nota, String nombre, String telefono, String email) {
        this.nota = nota;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNota() {
        return nota;
    }

    public void setUsuario(String nota) {
        this.nota = nota;
    }

    public String getNombre() { return nombre;}

    public void setContrasena(String contrasena) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
