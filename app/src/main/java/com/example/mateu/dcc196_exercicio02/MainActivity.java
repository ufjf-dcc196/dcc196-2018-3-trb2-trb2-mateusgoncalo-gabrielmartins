package com.example.mateu.dcc196_exercicio02;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button btnInserir;
    private Button btnRemover;
    private RecyclerView rvLista;
    private EditText txtNomeSerie;
    private EditText txtNumeroTemporada;
    private EditText txtNumeroEpisodio;
    private SerieDbHelper dbHelper;
    private SerieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SerieDbHelper(getApplicationContext());

        rvLista = (RecyclerView) findViewById(R.id.recycleView);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SerieAdapter(getSeries());
        rvLista.setAdapter(adapter);

        txtNomeSerie = (EditText) findViewById(R.id.nomeSerie);
        txtNumeroTemporada = (EditText) findViewById(R.id.numeroTemporada);
        txtNumeroEpisodio = (EditText) findViewById(R.id.numeroEpisodio);

        btnInserir = (Button) findViewById(R.id.btnInserir);
        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(SerieContract.Serie.COLUMN_NAME_NOME, txtNomeSerie.getText().toString());
                valores.put(SerieContract.Serie.COLUMN_NAME_TEMPORADA, txtNumeroTemporada.getText().toString());
                valores.put(SerieContract.Serie.COLUMN_NAME_EPISODIO, txtNumeroEpisodio.getText().toString());
                long id = db.insert(SerieContract.Serie.TABLE_NAME,null, valores);
                Log.i("DBINFO", "registro criado com id: "+id);
                adapter.setCursor(getSeries());
            }
        });

        adapter.setOnClickListener(new SerieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String select = SerieContract.Serie.COLUMN_NAME_REGISTRO+" = ?";
                String [] selectArgs = {String.valueOf(position)};
                db.delete(SerieContract.Serie.TABLE_NAME, select, selectArgs);
                adapter.notifyItemRemoved(position);
                adapter.setCursor(getSeries());
            }
        });
    }

    private Cursor getSeries()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String []visao = {
                SerieContract.Serie.COLUMN_NAME_REGISTRO,
                SerieContract.Serie.COLUMN_NAME_NOME,
                SerieContract.Serie.COLUMN_NAME_TEMPORADA,
                SerieContract.Serie.COLUMN_NAME_EPISODIO,
        };
        String sort = SerieContract.Serie.COLUMN_NAME_NOME+ " ASC";
        return db.query(SerieContract.Serie.TABLE_NAME, visao,null,null,null,null, sort);
    }
}
