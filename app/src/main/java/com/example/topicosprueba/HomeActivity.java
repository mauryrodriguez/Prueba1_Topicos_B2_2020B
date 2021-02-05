package com.example.topicosprueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    EditText etUsuario, etContrasena, etTelefono, etEmail;
    Button btnConsultar, btnConsultarUsuario, btnAgregar, btnEditar, btnEliminar,btnlogout;;
    RecyclerView rvUsuarios;
    DatabaseReference databaseReference;
    List<Usuario> listaUsuarios = new ArrayList<>();
    AdaptadorUsuario adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnlogout = findViewById(R.id.logoutButton);
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        rvUsuarios = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new GridLayoutManager(this, 1));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, AuthActivity.class));
            }
        });


        obtenerUsuarios();

        btnAgregar.setOnClickListener(view -> agregarUsuario());

        btnEditar.setOnClickListener(view -> editarUsuario());

        btnEliminar.setOnClickListener(view -> eliminarUsuario());

        btnConsultar.setOnClickListener(view -> obtenerUsuarios());

    }

    public void obtenerUsuario() {
        listaUsuarios.clear();
        String usuario = etUsuario.getText().toString();

        Query query = databaseReference.child("usuario").orderByChild("usuario").equalTo(usuario);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaUsuarios.add(objeto.getValue(Usuario.class));
                }

                adaptador = new AdaptadorUsuario(HomeActivity.this, listaUsuarios);
                rvUsuarios.setAdapter(adaptador);

                limpiarCampos();
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
    }

    public void obtenerUsuarios() {
        listaUsuarios.clear();
        databaseReference.child("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaUsuarios.add(objeto.getValue(Usuario.class));
                }

                adaptador = new AdaptadorUsuario(HomeActivity.this, listaUsuarios);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }

    public void agregarUsuario() {
        listaUsuarios.clear();
        String user=etUsuario.getText().toString();
        String name=etContrasena.getText().toString();
        String tel=etTelefono.getText().toString();
        String emai=etEmail.getText().toString();

        if(user.isEmpty()||name.isEmpty()||tel.isEmpty()||emai.isEmpty()){
            Toast.makeText(HomeActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            Usuario usuario = new Usuario(
                    user,name,tel,emai
            );


            databaseReference.child("usuario").push().setValue(usuario,
                    new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            Toast.makeText(HomeActivity.this, "Nota Añadida", Toast.LENGTH_SHORT).show();
                        }
                    });

            limpiarCampos();
        }

    }

    public void editarUsuario() {
        listaUsuarios.clear();
        String user=etUsuario.getText().toString();
        String name=etContrasena.getText().toString();
        String tel=etTelefono.getText().toString();
        String emai=etEmail.getText().toString();

        if(user.isEmpty()||name.isEmpty()||tel.isEmpty()||emai.isEmpty()){
            Toast.makeText(HomeActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            Usuario usuario = new Usuario(
                    user,name,tel,emai
            );
            Query query = databaseReference.child("usuario").orderByChild("usuario").equalTo(usuario.getNota());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String key ="";
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        key = ds.getKey(); // Obtiene el id del registro para poderlo editar
                    }
                    if(key.isEmpty()){
                        Toast.makeText(HomeActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    }else{
                        databaseReference.child("usuario").child(key).setValue(usuario);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            limpiarCampos();
        }



    }

    public void eliminarUsuario() {
        listaUsuarios.clear();
        String usuario = etUsuario.getText().toString();

        if(usuario.isEmpty()){
            Toast.makeText(HomeActivity.this, "Ingrese usuario correcto", Toast.LENGTH_SHORT).show();
        }else{
            Query query = databaseReference.child("usuario").orderByChild("usuario").equalTo(usuario);
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                        objeto.getRef().removeValue();
                    }
                    Toast.makeText(HomeActivity.this, "Se elimino el usuario", Toast.LENGTH_SHORT).show();
                }


                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            limpiarCampos();
        }


    }

    public void limpiarCampos() {
        etUsuario.setText("");
        etContrasena.setText("");
        etTelefono.setText("");
        etEmail.setText("");
    }
}