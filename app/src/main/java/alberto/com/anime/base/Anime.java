package alberto.com.anime.base;


import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class Anime implements Serializable {
    private long id;
    private String nombre;
    private Date fecha;
    private String visto;
    private String continua;
    private Date cuando;
    private int valoracion;
    private Bitmap imagen;

    public Anime(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }

    public String getContinua() {
        return continua;
    }

    public void setContinua(String continua) {
        this.continua = continua;
    }

    public Date getCuando() {
        return cuando;
    }

    public void setCuando(Date cuando) {
        this.cuando = cuando;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
