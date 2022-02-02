package org.izv.dmc.concesionario_mastermercedes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class CarSingle extends AppCompatActivity {

    Context context;
    private Car car;
    private TextView tvTitle, tvPrice, tvRef, tvColor, tvDesc, tvUrl, tvYear, tvPotencia, tvNPuertas ;
    private ImageView iv;
    private EditText etUbi, etCombustible, etKm, etCambio;
    private Button btLeft, btRight;
    private int posicion = 1;



//constructor de la view


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_car);
        context=this;
        this.car = getIntent().getParcelableExtra("car");
        System.out.println(car.toString());

        // Action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initialize();
    }


   //inicializamos los campos
    private void initialize() {

        tvTitle =findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvDesc = findViewById(R.id.tvDesc);
        tvUrl = findViewById(R.id.tvUrl);
        tvRef=findViewById(R.id.tvRef);
        iv = findViewById(R.id.imageView);
        etUbi = findViewById(R.id.etUbi);
        etCombustible = findViewById(R.id.etCombustible);
        etKm = findViewById(R.id.etKm);
        tvYear = findViewById(R.id.tvYear);
        etCambio = findViewById(R.id.etCambio);
        tvColor = findViewById(R.id.tvColor);
        tvPotencia = findViewById(R.id.tvPotencia);
        tvNPuertas = findViewById(R.id.tvNPuertas);
        btLeft =findViewById(R.id.btLeft);
        btRight = findViewById(R.id.btRight);

        btLeft.setEnabled(false);
        FillFields();

        //añadimos los listeners de los botones
        btLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(posicion>=1){
                    posicion--;
                    if(posicion==1){
                        btLeft.setEnabled(false);
                    }
                    btRight.setEnabled(true);
                    Picasso.get().load(car.imagesUrl+"_"+posicion+".jpg").into(iv);
                }
            }
        });

        btRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int number=Integer.parseInt(car.nImages);
                if(posicion<number){

                    posicion++;
                    if(posicion==number){
                        btRight.setEnabled(false);
                    }

                    btLeft.setEnabled(true);
                    Picasso.get().load(car.imagesUrl+"_"+posicion+".jpg").into(iv);
                }
            }catch (NumberFormatException e){
                Log.v("xyzyx",e.toString());
            }
        }
        });

        //añadimos algunos listeners que indiquen de que elemento se tratan al pulsarlos a través de un Toast
        etCombustible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, "Combustible", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        etKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, "Km", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        etUbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, "Localizacion", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        etCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, "Cambio", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }


    //rellena con la información del coche los campos del layout
    @SuppressLint("SetTextI18n")
    private void FillFields() {
        tvRef.setText("Ref. "+car.ref+"");
        tvTitle.setText(car.title);
        tvPrice.setText(car.price+"€");
        tvDesc.setText(car.desc);
        tvUrl.setText(car.url);
        etCombustible.setText(car.combustible);
        etKm.setText(car.km);
        tvYear.setText("Año:\t"+car.year);
        tvColor.setText("Color:\t"+car.color);
        tvPotencia.setText("potencia: "+car.potencia+" CV");
        tvNPuertas.setText(car.nPuertas+" puertas");
        etCambio.setText(car.cambio);
        etUbi.setText(car.ubi);

        Picasso.get().load(car.imagesUrl+"_1.jpg").into(iv);
        urlListener(car.url);

    }


    //lleva a la pagina de mil anuncios de el post actual
    private void urlListener(String link) {
        tvUrl.setOnClickListener(view -> {
            Uri uri = Uri.parse(link);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
}