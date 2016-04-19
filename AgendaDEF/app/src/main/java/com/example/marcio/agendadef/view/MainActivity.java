package com.example.marcio.agendadef.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marcio.agendadef.R;
import com.example.marcio.agendadef.model.Contato;
import com.example.marcio.agendadef.util.FireBaseUtil;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private Firebase firebase;

    @Bind(R.id.lista)
    ListView listView;

    private List<Contato> contatos = new ArrayList<Contato>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        firebase = FireBaseUtil.getFirebase();
    }

    @OnClick(R.id.fab)
    public void onFabCad(View view) {
        Intent i = new Intent(getBaseContext(), Update.class);
        startActivity(i);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Firebase myFirebase = firebase.child("AgendaDEF");
        FirebaseListAdapter<Contato> adapter =
                new FirebaseListAdapter<Contato>(this,
                        Contato.class,
                        R.layout.item_layout,
                        myFirebase) {
                    @Override
                    protected void populateView(View convertView, Contato con, int i) {
                        ViewHolder holder;
                        holder = new ViewHolder(convertView);
                        holder.nome.setText(con.getNome());
                        holder.telefone.setText(con.getValor());
                        String url = con.getImagem();
                        if (url != null) {
                            Bitmap imagemFoto = BitmapFactory.decodeFile(url);
                            holder.photo.setImageBitmap(imagemFoto);
                            holder.photo.setTag(url);
                        }

                    }
                };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("men", "update" + contatos.get(position).getNome());
                Intent i = new Intent(getBaseContext(), Update.class);
                i.putExtra("AgendaDEF", contatos.get(position));
                startActivity(i);
            }
        });

        myFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    contatos.add(data.getValue(Contato.class));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    static class ViewHolder {
        @Bind(R.id.tvNome)
        TextView nome;
        @Bind(R.id.tvTelefone)
        TextView telefone;
        @Bind(R.id.imageView)
        ImageView photo;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
