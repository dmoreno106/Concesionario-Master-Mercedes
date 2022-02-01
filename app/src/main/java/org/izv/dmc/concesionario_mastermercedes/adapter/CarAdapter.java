package org.izv.dmc.concesionario_mastermercedes.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import org.izv.dmc.concesionario_mastermercedes.Car;
import org.izv.dmc.concesionario_mastermercedes.CarSingle;
import org.izv.dmc.concesionario_mastermercedes.R;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder>{

    private ArrayList<Car> carList = new ArrayList<>();
    private Context context;

    private View.OnClickListener listener;


    public CarAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflamos la view que usaremos para el recycler con el layout del item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new CarViewHolder(view);
    }

  //introducimos los valores del view holder
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {

        if (!carList.isEmpty()) {
            Car selectedCar  = carList.get(position);
            holder.car= selectedCar;
            holder.tvTitle.setText(selectedCar.title);
            holder.tvPrice.setText(selectedCar.price+" €");
            holder.etUbi.setText(selectedCar.ubi);
            holder.etKm.setText(selectedCar.km+" KM");
            holder.tvYear.setText("YEAR: "+selectedCar.year);
            holder.tvColor.setText("COLOR: "+selectedCar.color);
            holder.tvCombustible.setText("FUEL: "+selectedCar.combustible);
            String img = selectedCar.imagesUrls.get(0);
            String[] images = img.split(";");
            Picasso.get().load(images[0]).into(holder.ivAspect);
        }
    }

  //numero de items
    @Override
    public int getItemCount() {
        if (carList.isEmpty()) {
            return 0;
        }

        return carList.size();
    }

    public void setList(ArrayList<Car> carList){
        this.carList=carList;
    }

    public class CarViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvDesc, tvPrice,tvYear,tvColor,tvCombustible;
        ImageView ivAspect;
        EditText etUbi,etKm;
        Car car;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvRef);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            ivAspect = itemView.findViewById(R.id.ivAspect);
            etUbi=itemView.findViewById(R.id.etUbi);
            etKm=itemView.findViewById(R.id.etKm);
            tvYear=itemView.findViewById(R.id.tvYear);
            tvColor=itemView.findViewById(R.id.tvPrice);
            tvCombustible=itemView.findViewById(R.id.tvCombustible);

            //hacemos el intent a la informacion del coche al pulsar en el item
            itemView.setOnClickListener((View v) -> {
                Log.v("xyzyx", "View Holder");
                Intent intent = new Intent(context, CarSingle.class);
                intent.putExtra("car", car);
                context.startActivity(intent);
            });
        }
    }
}
