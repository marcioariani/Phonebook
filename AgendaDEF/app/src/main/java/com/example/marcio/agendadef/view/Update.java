package com.example.marcio.agendadef.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.marcio.agendadef.R;
import com.example.marcio.agendadef.model.Contato;
import com.example.marcio.agendadef.util.FireBaseUtil;
import com.firebase.client.Firebase;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Update extends AppCompatActivity {
    private Firebase firebase;

    @Bind(R.id.imagem)
    ImageView picture;
    @Bind(R.id.edtTelefone)
    EditText edtTelefone;
    @Bind(R.id.edtNome)
    EditText edtNome;

    @Bind(R.id.fabDel)
    FloatingActionButton fbDell;

    private boolean update = false;
    private Contato contato;
    private String localFoto;
    private static final int FOTO = 1;
    private boolean fotoResource = false;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebase = FireBaseUtil.getFirebase();
        updateUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        contato = new Contato();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            contato = (Contato) b.get("AgendaDEF");
            update = true;
            fbDell.setVisibility(View.VISIBLE);
            updateUI();
            localFoto = contato.getImagem();
        }
    }

    @OnClick(R.id.fabDel)
    public void fabDel(View view) {
        firebase.child("AgendaDEF").child(contato.getId()).removeValue();
        Toast.makeText(getApplicationContext(), "Contato excluido!", Toast.LENGTH_SHORT).show();
        finish();
    }


    @OnClick(R.id.fabCad)
    public void fabcad(View v) {
        Firebase posRef = firebase.child("AgendaDEF");
        Firebase newPost ;
        contato.setNome(edtNome.getText().toString());
        contato.setValor(edtTelefone.getText().toString());
        contato.setImagem((String) picture.getTag());

        if (update) {
            newPost = posRef.child(contato.getId());
            Toast.makeText(getApplicationContext(), "Contato alterado!", Toast.LENGTH_SHORT).show();
        } else {
            newPost = posRef.push();
            contato.setId(newPost.getKey());
            Toast.makeText(getApplicationContext(),"Novo contato adicionado!", Toast.LENGTH_SHORT).show();
        }
        newPost.setValue(contato);
        finish();
    }


    public void updateUI() {
        if (contato == null) {
            edtNome.setText(null);
            edtTelefone.setText(null);
        } else {
            edtNome.setText(contato.getNome());
            setFoto(contato.getImagem());
            edtTelefone.setText(contato.getValor());
        }
    }

    public void carregaFoto() {
        fotoResource = true;
        localFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(localFoto)));
        startActivityForResult(intentCamera, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FOTO) {
            if (resultCode == Activity.RESULT_OK) {
                setFoto(localFoto);
            } else {
                this.localFoto = null;
            }
        }
    }

    private void setFoto(String url) {
        if (url != null) {
            Bitmap imagemFoto = BitmapFactory.decodeFile(url);
            picture.setImageBitmap(imagemFoto);
            picture.setTag(url);
        }
    }

    @OnClick(R.id.fabFhoto)
    public void foto(View v) {
        carregaFoto();
    }

}
