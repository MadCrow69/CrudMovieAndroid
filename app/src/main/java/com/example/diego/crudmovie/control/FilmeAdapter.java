package com.example.diego.crudmovie.control;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;
import com.example.diego.crudmovie.dao.FilmeDAO;
import com.example.diego.crudmovie.model.Filme;
import com.example.diego.crudmovie.R;


public class FilmeAdapter extends RecyclerView.Adapter<FilmeHolder> {

    private final List<Filme> filmes;


    public FilmeAdapter(Context context) {
        FilmeDAO dao = new FilmeDAO(context);
        List<Filme> temp = dao.retornarTodos();
        if( temp.size() == 0){
            new ImportarFilmes(context).Importar();
        }
        this.filmes = dao.retornarTodos();
    }

    public void atualizarFilme(Filme filme){
        filmes.set(filmes.indexOf(filme), filme);
        notifyItemChanged(filmes.indexOf(filme));
    }

    public void adicionarFilme(Filme filme){
        filmes.add(filme);
        notifyItemInserted(getItemCount());
    }

    public void removerFilme(Filme filme){
        int position = filmes.indexOf(filme);
        filmes.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public FilmeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilmeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(FilmeHolder holder, int position) {
        holder.nomeFilme.setText(filmes.get(position).getTitle());
        holder.textYear.setText(filmes.get(position).getYear());
        holder.textRating.setText(filmes.get(position).getRating());
        //holder.urlThunbmail.setText(filmes.get(position).getThumbnailUrl());

        final Filme filme = filmes.get(position);

        holder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este filme?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FilmeDAO dao = new FilmeDAO(view.getContext());
                                boolean sucesso = dao.excluir(filme.getId());
                                if(sucesso) {
                                    removerFilme(filme);
                                    Snackbar.make(view, "Excluiu!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else{
                                    Snackbar.make(view, "Erro ao excluir o filme!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });

        holder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("filme", filme);
                activity.finish();
                activity.startActivity(intent);
            }
        });
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();

        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return filmes != null ? filmes.size() : 0;
    }
}