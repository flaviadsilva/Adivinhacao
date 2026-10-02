package com.example.adivinahcao;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class JogoActivity extends AppCompatActivity {

    private int secreto;
    private int tentativas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jogo);

        int margem = (int) (24 * getResources().getDisplayMetrics().density);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(barras.left + margem, barras.top + margem, barras.right + margem, barras.bottom + margem);
            return insets;
        });

        // tira os dados da "bagagem" do Intent
        String nomeRecebido = getIntent().getStringExtra("nome");
        final String nome = (nomeRecebido != null) ? nomeRecebido : "Jogador";
        final int maximo = getIntent().getIntExtra("maximo", 10);

        secreto = new Random().nextInt(maximo) + 1; // de 1 até maximo

        TextView txtBoasVindas = findViewById(R.id.txtBoasVindas);
        EditText edtPalpite = findViewById(R.id.edtPalpite);
        Button btnChutar = findViewById(R.id.btnChutar);
        TextView txtDica = findViewById(R.id.txtDica);
        TextView txtTentativas = findViewById(R.id.txtTentativas);
        Button btnJogarNovamente = findViewById(R.id.btnJogarNovamente);

        txtBoasVindas.setText(nome + ", pensei num número de 1 a " + maximo + "!");

        btnChutar.setOnClickListener(v -> {
            String texto = edtPalpite.getText().toString().trim();

            // evita NumberFormatException
            int palpite;
            try {
                palpite = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                edtPalpite.setError("Digite um número de 1 a " + maximo);
                return;
            }

            if (palpite < 1 || palpite > maximo) {
                edtPalpite.setError("Digite um número de 1 a " + maximo);
                return;
            }

            tentativas++;
            txtTentativas.setText("Tentativas: " + tentativas);

            String direcao = (secreto > palpite) ? "MAIOR" : "MENOR";

            if (palpite == secreto) {
                txtDica.setText(nome + " acertou em " + tentativas + " tentativas!");
                btnChutar.setEnabled(false);
                btnJogarNovamente.setVisibility(View.VISIBLE);
            } else if (Math.abs(palpite - secreto) <= maximo / 10) {
                txtDica.setText("Quente! O número é " + direcao + ".");
            } else {
                txtDica.setText("Frio! O número é " + direcao + ".");
            }

            edtPalpite.getText().clear();
        });

        // desafio: volta para a primeira tela sem empilhar outra MainActivity
        btnJogarNovamente.setOnClickListener(v -> finish());
    }
}
