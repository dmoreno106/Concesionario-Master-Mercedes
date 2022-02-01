package org.izv.dmc.concesionario_mastermercedes.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.dmc.concesionario_mastermercedes.Car;
import org.izv.dmc.concesionario_mastermercedes.CarSingle;
import org.izv.dmc.concesionario_mastermercedes.R;

//definimos los items del recycler view
 class CarViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivAspect;
    public TextView tvTitle, tvPrice,tvYear,tvColor,tvCombustible;
    public EditText etUbi,etKm;
    Context context;
    Car car;

    public CarViewHolder(@NonNull View itemView) {
        super(itemView);
        ivAspect = itemView.findViewById(R.id.ivAspect);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvPrice = itemView.findViewById(R.id.tvRef);
        etUbi = itemView.findViewById(R.id.etUbi);
       etKm = itemView.findViewById(R.id.etKm);
       tvYear=itemView.findViewById(R.id.tvYear);
       tvColor=itemView.findViewById(R.id.tvPrice);
       tvCombustible=itemView.findViewById(R.id.tvCombustible);


        itemView.setOnClickListener((View v) -> {
            Log.v("xyzyx", "Debo cambiar de escena");
            Intent intent = new Intent(context, CarSingle.class);
            intent.putExtra("car",car);
            context.startActivity(intent);
        });
    }

}
