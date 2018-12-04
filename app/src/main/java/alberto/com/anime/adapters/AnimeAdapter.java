package alberto.com.anime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import alberto.com.anime.base.Anime;
import alberto.com.anime.R;

public class AnimeAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int idLayout;
    private List<Anime> animes;

    public AnimeAdapter(Context contexto,int idLayout,List<Anime> anime){
        inflater=LayoutInflater.from(contexto);
        this.idLayout=idLayout;
        this.animes=anime;
    }

    static class ViewHolder{
        ImageView imageView;
        TextView txNombre;
        TextView txVisto;
        TextView txContinuacion;

        TextView txRecomendado;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {
       ViewHolder holder=null;

       if(convertView==null){
           convertView=inflater.inflate(idLayout,null);

           holder=new ViewHolder();
           holder.imageView=convertView.findViewById(R.id.imageView);
           holder.txNombre=convertView.findViewById(R.id.txNombre);
           holder.txVisto=convertView.findViewById(R.id.txVisto);
           holder.txContinuacion=convertView.findViewById(R.id.txContinuacion);
           holder.txRecomendado=convertView.findViewById(R.id.txRecomendacion);

           convertView.setTag(holder);
       }else {
           holder = (ViewHolder) convertView.getTag();
       }
       Anime anime=animes.get(posicion);
       holder.imageView.setImageBitmap(anime.getImagen());
       holder.txNombre.setText(anime.getNombre());
       holder.txVisto.setText(anime.getVisto());
       holder.txContinuacion.setText(anime.getContinua());
       holder.txRecomendado.setText(anime.getValoracion());
       return convertView;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
