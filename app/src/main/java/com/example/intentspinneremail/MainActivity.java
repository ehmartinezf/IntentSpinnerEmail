package com.example.intentspinneremail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText etNombre = findViewById(R.id.etNombre);
        EditText etCorreo = findViewById(R.id.etCorreo);
        Spinner spTipo = findViewById(R.id.spTipo);
        EditText etMensaje = findViewById(R.id.etMensaje);
        CheckBox cbCopia = findViewById(R.id.cbCopia);
        Button btnPreparar = findViewById(R.id.btnPreparar);

        btnPreparar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String tipo = spTipo.getSelectedItem().toString();
            String mensaje = etMensaje.getText().toString().trim();

            if (nombre.isEmpty()) {
                etNombre.setError("Escribe tu nombre");
                return;
            }

            if (correo.isEmpty()) {
                etCorreo.setError("Escribe tu correo");
                return;
            }

            if (mensaje.isEmpty()) {
                etMensaje.setError("Escribe un mensaje");
                return;
            }

            String asunto = tipo + " de " + nombre;

            String direccion = "mailto:e.hmartinezf@ies-sabadell.cat"
                    + "?subject=" + Uri.encode(asunto)
                    + "&body=" + Uri.encode(mensaje);

            if (cbCopia.isChecked()) {
                direccion = direccion + "&cc=" + Uri.encode(correo);
            }

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(direccion));

            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
