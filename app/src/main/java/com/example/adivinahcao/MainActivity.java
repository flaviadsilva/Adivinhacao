package com.example.adivinahcao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

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

        // empurra o conteúdo para longe da barra de status + 24dp de margem
        int margem = (int) (24 * getResources().getDisplayMetrics().density);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(barras.left + margem, barras.top + margem, barras.right + margem, barras.bottom + margem);
            return insets;
        });

        EditText edtNome = findViewById(R.id.edtNome);
        RadioGroup rgLimite = findViewById(R.id.rgLimite);
        Button btnJogar = findViewById(R.id.btnJogar);

        btnJogar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString().trim();

            if (nome.isEmpty()) {
                edtNome.setError("Digite seu nome");
                return;
            }

            // descobre qual RadioButton está marcado
            int maximo;
            int marcado = rgLimite.getCheckedRadioButtonId();
            if (marcado == R.id.rb10) {
                maximo = 10;
            } else if (marcado == R.id.rb100) {
                maximo = 100;
            } else {
                maximo = 50;
            }

            // coloca os dados na "bagagem" e abre a JogoActivity
            Intent intent = new Intent(MainActivity.this, JogoActivity.class);
            intent.putExtra("nome", nome);
            intent.putExtra("maximo", maximo);
            startActivity(intent);
        });
    }
}
