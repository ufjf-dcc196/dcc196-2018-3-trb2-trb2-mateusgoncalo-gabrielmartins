package com.example.mateu.dcc196_exercicio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button btnInserir;
    private Button btnRemover;
    private RecyclerView rvLista;
    private TextView txtNomeSerie;
    private TextView txtNumeroTemporada;
    private TextView txtNumeroEpisodio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
