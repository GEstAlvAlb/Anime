package alberto.com.anime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alberto.com.anime.base.Anime;
import alberto.com.anime.util.Util;

import static alberto.com.anime.util.Constantes.CONTINUA;
import static alberto.com.anime.util.Constantes.CUANDO;
import static alberto.com.anime.util.Constantes.DATABASE_NAME;
import static alberto.com.anime.util.Constantes.FECHA;
import static alberto.com.anime.util.Constantes.IMAGEN;
import static alberto.com.anime.util.Constantes.NOMBRE;
import static alberto.com.anime.util.Constantes.TABLA_ANIME;
import static alberto.com.anime.util.Constantes.VALORACION;
import static alberto.com.anime.util.Constantes.VERSION;
import static alberto.com.anime.util.Constantes.VISTO;
import static android.provider.BaseColumns._ID;

public class Database extends SQLiteOpenHelper {
    public Database(Context context){super(context,DATABASE_NAME,null,VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLA_ANIME +"("+ _ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, "+NOMBRE+"TEXT, "+
                FECHA+"TEXT, "+ VISTO+"TEXT, "+CONTINUA+"TEXT, "
                +CUANDO+"TEXT, "+VALORACION+"INTEGER, "+IMAGEN+"BLOB)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE "+ TABLA_ANIME);
        onCreate(db);
    }

    public void nuevoAnime(Anime anime){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(NOMBRE,anime.getNombre());
        values.put(FECHA,Util.formatearFecha(anime.getFecha()));
        values.put(VISTO,anime.getVisto());
        values.put(CONTINUA,anime.getVisto());
        values.put(CUANDO,Util.formatearFecha(anime.getCuando()));
        values.put(VALORACION,anime.getValoracion());
        values.put(IMAGEN,Util.getBytes(anime.getImagen()));

        db.insertOrThrow(TABLA_ANIME,null,values);
        db.close();

    }

    public void eliminarAnime(Anime anime){
        SQLiteDatabase db=getWritableDatabase();
        String [] args={String.valueOf(anime.getId())};
        db.delete(TABLA_ANIME,_ID+" = ?",args);
        db.close();

    }
    public void modificarAnime(Anime anime){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put(NOMBRE,anime.getNombre());
        values.put(FECHA,Util.formatearFecha(anime.getFecha()));
        values.put(VISTO,anime.getVisto());
        values.put(CONTINUA,anime.getVisto());
        values.put(CUANDO,Util.formatearFecha(anime.getCuando()));
        values.put(VALORACION,anime.getValoracion());
        values.put(IMAGEN,Util.getBytes(anime.getImagen()));

        String [] args={String.valueOf(anime.getId())};
        db.update(TABLA_ANIME,values,_ID+" = ?",args);
        db.close();
    }

    public List<Anime> getAnimes(){
        final String[] COLUMNAS={_ID, NOMBRE, FECHA, VISTO, CONTINUA, CUANDO, VALORACION, IMAGEN};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=db.query(TABLA_ANIME,COLUMNAS,null,null,null,null,null);

        List<Anime> animes=new ArrayList<>();
        Anime anime=null;

        while (cursor.moveToNext()){
            anime=new Anime();
            anime.setId(cursor.getLong(0));
            anime.setNombre(cursor.getString(1));
            try {
                anime.setFecha(Util.parsearFecha(cursor.getString(2)));

            } catch (ParseException e) {
                anime.setFecha(new Date());
            }

            anime.setVisto(cursor.getString(3));
            anime.setContinua(cursor.getString(4));
            try {
                anime.setCuando(Util.parsearFecha(cursor.getString(5)));

            } catch (ParseException e) {
                anime.setCuando(new Date());
            }
            anime.setValoracion(cursor.getInt(6));
            anime.setImagen(Util.getBitmap(cursor.getBlob(7)));

            animes.add(anime);
        }
        return animes;
    }


    public List<Anime>getAnimes(String busqueda){
        return null;
    }





















}
