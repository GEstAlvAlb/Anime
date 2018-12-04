package alberto.com.anime.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import alberto.com.anime.base.Anime;
import alberto.com.anime.R;
import alberto.com.anime.adapters.AnimeAdapter;
import alberto.com.anime.db.Database;
import alberto.com.anime.util.Util;

public class MainActivity extends Activity {

    private List<Anime> animes;
    private AnimeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database db=new Database(this);
        animes=db.getAnimes();

        ListView lvAnimes= findViewById(R.id.lvAnimes);
        adapter=new AnimeAdapter(this,R.layout.layout_conjunto,animes);
        lvAnimes.setAdapter(adapter);
        registerForContextMenu(lvAnimes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refrecarListaAnimes();
    }

    private void refrecarListaAnimes() {
        animes.clear();
        Database db=new Database(this);
        animes.addAll(db.getAnimes());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contestual,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo) item.getSubMenu();
        int posicion= menuInfo.position;

        switch (item.getItemId()){
            case R.id.menuModificar:
                Intent intent=new Intent(this,AltaAnime.class);
                Anime anime=animes.get(posicion);
                intent.putExtra("accion","modificar");
                intent.putExtra("evento", anime);
                intent.putExtra("imagen",Util.getBytes(anime.getImagen()));
                startActivity(intent);
                return true;
            case R.id.menuEliminar:
                Database db=new Database(this);
                db.eliminarAnime(animes.get(posicion));
                refrecarListaAnimes();
                return true;
            default:
                return false;


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_superior,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.itNuevo:
                Intent intent=new Intent(this,AltaAnime.class);
                intent.putExtra("accion","nuevo");
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}


































