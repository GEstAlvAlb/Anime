package alberto.com.anime.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.ParseException;

import alberto.com.anime.base.Anime;
import alberto.com.anime.R;
import alberto.com.anime.db.Database;
import alberto.com.anime.util.Util;

public class AltaAnime extends Activity implements View.OnClickListener {

    private static final int FOTO_EVENTO = 1;
    private String accion;
    private long idAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_anime);

        Button btAlta = findViewById(R.id.btAñadir);
        btAlta.setOnClickListener(this);
        Button btCancelar = findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(this);
        ImageButton ibImagen = findViewById(R.id.ibImagen);
        ibImagen.setOnClickListener(this);

        accion = getIntent().getStringExtra("accion");
        if (accion.equals("modificar")) {
            Anime anime = (Anime) getIntent().getSerializableExtra("evento");
            Bitmap imagenAnime = Util.getBitmap(getIntent().getByteArrayExtra("imagen"));
            relletarDatos(anime, imagenAnime);
            btAlta.setText("Guardar");

        }

    }

    private void relletarDatos(Anime anime, Bitmap imagenAnime) {

        EditText etNombre = findViewById(R.id.etNombre);
        EditText etFecha = findViewById(R.id.etFecha);
        EditText etVisto = findViewById(R.id.etVisto);
        EditText etContinua = findViewById(R.id.etContinuaicon);
        EditText etCuando = findViewById(R.id.etCuando);
        EditText etValoracion = findViewById(R.id.etValoracion);
        ImageButton ibImagen = findViewById(R.id.ibImagen);

        etNombre.setText(anime.getNombre());
        etFecha.setText(Util.formatearFecha(anime.getFecha()));
        etVisto.setText(anime.getVisto());
        etContinua.setText(anime.getContinua());
        etCuando.setText(Util.formatearFecha(anime.getCuando()));
        etValoracion.setText(String.valueOf(anime.getValoracion()));
        ibImagen.setImageBitmap(imagenAnime);

        idAnime = anime.getId();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btAñadir:
                EditText etNombre = findViewById(R.id.etNombre);
                EditText etFecha = findViewById(R.id.etFecha);
                EditText etVisto = findViewById(R.id.etVisto);
                EditText etContinua = findViewById(R.id.etContinuaicon);
                EditText etCuando = findViewById(R.id.etCuando);
                EditText etValoracion = findViewById(R.id.etValoracion);
                ImageButton ibImagen = findViewById(R.id.ibImagen);

                try {
                    if (!etContinua.getText().toString().equals("SI") || !etContinua.getText().toString().equals("Si") || !etContinua.getText().toString().equals("si") ||
                            !etContinua.getText().toString().equals("NO") || etContinua.getText().toString().equals("No") || etContinua.getText().toString().equals("no")) {
                        Toast.makeText(this, "Introduzca si o no en continua",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (etValoracion.getText().toString().equals(""))
                        etValoracion.setText("0");


                    Anime anime = new Anime();
                    anime.setNombre(etNombre.getText().toString());
                    anime.setFecha(Util.parsearFecha(etFecha.getText().toString()));
                    anime.setVisto(etVisto.getText().toString());
                    anime.setContinua(etContinua.getText().toString());
                    anime.setCuando(Util.parsearFecha(etCuando.getText().toString()));
                    anime.setValoracion(Integer.parseInt(etValoracion.getText().toString()));
                    anime.setImagen(((BitmapDrawable) ibImagen.getDrawable()).getBitmap());

                    Database db = new Database(this);
                    switch (accion) {
                        case "nuevo":
                            db.nuevoAnime(anime);
                            break;
                        case "modificar":
                            anime.setId(idAnime);
                            db.modificarAnime(anime);
                            break;
                        default:
                            break;
                    }
                    Toast.makeText(this, "El anime " + anime.getNombre() + " ha sido guardado", Toast.LENGTH_LONG).show();
                    etNombre.setText("");
                    etNombre.requestFocus();
                    etFecha.setText("");
                    etVisto.setText("");
                    etContinua.setText("");
                    etCuando.setText("");
                    etValoracion.setText("");


                } catch (ParseException e) {
                    Toast.makeText(this, "Formato de fecha no valido", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btCancelar:
                onBackPressed();
                break;
            case R.id.ibImagen:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, FOTO_EVENTO);
                break;
            default:
                break;

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((resultCode == RESULT_OK) && (data != null)) {
            switch (requestCode) {
                case FOTO_EVENTO:
                    Uri imagenSeleccionada = data.getData();
                    String[] ruta = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(imagenSeleccionada, ruta, null, null, null);
                    cursor.moveToFirst();

                    int indice = cursor.getColumnIndex(ruta[0]);
                    String picturePath = cursor.getString(indice);
                    cursor.close();

                    ImageButton ibImagen = findViewById(R.id.ibImagen);
                    ibImagen.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                default:
                    break;


            }
        }

    }
}
