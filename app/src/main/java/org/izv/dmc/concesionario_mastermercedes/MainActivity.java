package  org.izv.dmc.concesionario_mastermercedes;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.izv.dmc.concesionario_mastermercedes.R;
import org.izv.dmc.concesionario_mastermercedes.adapter.CarAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "jdbc:mariadb://146.59.237.189/dam208_dmcconcesionario";
    private static final String USER = "dam208_dmc";
    private static final String PASSWORD = "dam208_dmc";

    public Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_main);
        new InfoAsyncTask().execute();

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
    }

    //Inflamos el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //opciones del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_mi_perfil) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_preferences) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_Info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_web) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.milanuncios.com/tienda/santogal-concesionario-oficial-mercedes-63238.htm"));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    //conexion a la base de datos
    @SuppressLint("StaticFieldLeak")
    public class InfoAsyncTask extends AsyncTask<Void, Void, ArrayList<Car>> {
        @Override
        protected ArrayList<Car> doInBackground(Void... voids) {
            ArrayList<Car> info = new ArrayList<>();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

                String sql = "SELECT * FROM coches";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {

                    int ref = resultSet.getInt("ref");
                    String title = resultSet.getString("titulo");
                    String desc = resultSet.getString("descripcion");
                    String price = resultSet.getString("precio");
                    String url = resultSet.getString("url");
                    String km = resultSet.getString("km");
                    String combustible = resultSet.getString("combustible");
                    String cambio = resultSet.getString("cambio");
                    String color = resultSet.getString("color");
                    String potencia = resultSet.getString("potencia");
                    String year = resultSet.getString("year");
                    String nPuertas = resultSet.getString("npuertas");
                    String images = resultSet.getString("imagenes");
                    String ubi = resultSet.getString("localizacion");
                    String nImages=resultSet.getString("nImages");
                    Car car = new Car(ref, title, desc, price, url,combustible,km,cambio, color, potencia, nPuertas, year,ubi,images,nImages);
                    info.add(car);

                }
                return info;
            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading car information", e);
            }
            return info;
        }

        //llenamos el recyclerView con los items
        @Override
        protected void onPostExecute(ArrayList<Car> result) {
            if (!result.isEmpty()) {
                RecyclerView rv = findViewById(R.id.carRecicler);
                rv.setLayoutManager(new LinearLayoutManager(context));
                CarAdapter carAdapter = new CarAdapter(context);
                rv.setAdapter(carAdapter);
                carAdapter.setList(result);
            }
        }
    }
}
