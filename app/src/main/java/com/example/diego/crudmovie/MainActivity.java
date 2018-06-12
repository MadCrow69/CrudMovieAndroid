package com.example.diego.crudmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.diego.crudmovie.control.FilmeAdapter;
import com.example.diego.crudmovie.control.ImportarFilmes;
import com.example.diego.crudmovie.dao.FilmeDAO;
import com.example.diego.crudmovie.model.Filme;
import com.example.diego.crudmovie.R;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    Filme filmeEditado = null;

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //verifica se começou agora ou se veio de uma edição
        Intent intent = getIntent();
        if(intent.hasExtra("filme")){
            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            filmeEditado = (Filme) intent.getSerializableExtra("filme");

            EditText txtTitle = (EditText)findViewById(R.id.txtTitle);
            EditText txtThumbnailUrl = (EditText)findViewById(R.id.txtThumbnailUrl);
            EditText txtYear = (EditText)findViewById(R.id.txtYear);
            EditText txtRating = (EditText)findViewById(R.id.txtRating);
            EditText txtGenre = (EditText)findViewById(R.id.txtGenre);


            txtTitle.setText(filmeEditado.getTitle());
            txtThumbnailUrl.setText(filmeEditado.getThumbnailUrl());
            txtYear.setText(filmeEditado.getYear());
            txtRating.setText(filmeEditado.getRating());
            txtGenre.setText(filmeEditado.getGenre());

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }
        });

        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //carregando os campos
                EditText txtTitle = (EditText)findViewById(R.id.txtTitle);
                EditText txtThumbnailUrl = (EditText)findViewById(R.id.txtThumbnailUrl);
                EditText txtYear = (EditText)findViewById(R.id.txtYear);
                EditText txtRating = (EditText)findViewById(R.id.txtRating);
                EditText txtGenre = (EditText)findViewById(R.id.txtGenre);


                //pegando os valores
                String title = txtTitle.getText().toString();
                String thumbnailUrl = txtThumbnailUrl.getText().toString();
                String year = txtYear.getText().toString();
                String rating = txtRating.getText().toString();
                String genre = txtGenre.getText().toString();


                //salvando os dados
                FilmeDAO dao = new FilmeDAO(getBaseContext());
                boolean sucesso;
                if(filmeEditado != null)
                    sucesso = dao.salvar(filmeEditado.getId(), title, thumbnailUrl, year, rating, genre);

                else {
                    sucesso = dao.salvar(title, thumbnailUrl, year, rating, genre);
                    filmeEditado = null;
                }

                if(sucesso) {
                    Filme filme = dao.retornarUltimo();
                    if(filmeEditado != null){
                        adapter.atualizarFilme(filme);
                    }
                    else {
                        adapter.adicionarFilme(filme);
                    }


                    //limpa os campos
                    filmeEditado = null;
                    txtTitle.setText("");
                    txtThumbnailUrl.setText("");
                    txtYear.setText("");
                    txtRating.setText("");
                    txtGenre.setText("");


                    Snackbar.make(view, "Salvou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                    findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
                }else{
                    Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        configurarRecycler();
    }

    RecyclerView recyclerView;
    private FilmeAdapter adapter;

    private void configurarRecycler() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        //FilmeDAO dao = new FilmeDAO(this);

        adapter = new FilmeAdapter(this);
        recyclerView.setAdapter(adapter);

        // Configurando um separador entre linhas, para uma melhor visualização.
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
