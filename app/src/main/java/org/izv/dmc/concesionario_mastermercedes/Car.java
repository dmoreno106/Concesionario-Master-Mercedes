package org.izv.dmc.concesionario_mastermercedes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

//hacemos el objeto coche parcelable e introducimos sus métodos
public class Car implements Parcelable {
    public  int ref;
    public String title;
    public String desc;
    public String price;
    public String url;
    public String combustible;
    public String  km;
    public String cambio;
    public String color;
    public String potencia;
    public String nPuertas;
    public String year;
    public String ubi;
    public String imagesUrl;
    public String nImages;

    //constructor
    public Car(int ref, String title, String desc, String price,
               String url,String combustible, String km, String cambio,
               String color, String potencia, String nPuertas,String year,
               String ubi,String imagesUrl,String nImages) {
        this.ref = ref;
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.url = url;
        this.combustible = combustible;
        this.km = km;
        this.cambio = cambio;
        this.color = color;
        this.potencia = potencia;
        this.nPuertas = nPuertas;
        this.year = year;
        this.ubi = ubi;
        this.imagesUrl=imagesUrl;
        this.nImages=nImages;
    }


    protected Car(Parcel in) {
        ref = in.readInt();
        title = in.readString();
        desc= in.readString();
        price = in.readString();
        url = in.readString();
        combustible = in.readString();
        km = in.readString();
        cambio = in.readString();
        color = in.readString();
        potencia = in.readString();
        nPuertas = in.readString();
        year = in.readString();
        ubi = in.readString();
        imagesUrl = in.readString();
        nImages=in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public int describeContents() { return 0; }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ref);
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeString(price);
        parcel.writeString(url);
        parcel.writeString(combustible);
        parcel.writeString(km);
        parcel.writeString(cambio);
        parcel.writeString(color);
        parcel.writeString(potencia);
        parcel.writeString(nPuertas);
        parcel.writeString(year);
        parcel.writeString(ubi);
        parcel.writeString(imagesUrl);
        parcel.writeString(nImages);
    }

    //toString para pruebas en consola
    @Override
    public String toString() {
        return "Car{" +
                "ref=" + ref +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", combustible='" + combustible + '\'' +
                ", km='" + km + '\'' +
                ", cambio='" + cambio + '\'' +
                ", color='" + color + '\'' +
                ", potencia='" + potencia + '\'' +
                ", nPuertas='" + nPuertas + '\'' +
                ", year='" + year + '\'' +
                ", ubi='" + ubi + '\'' +
                ", imagesUrls=" + imagesUrl+
                '}';
    }
}
