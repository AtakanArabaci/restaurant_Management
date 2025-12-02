package controller;

import model.Masa;

import java.util.ArrayList;

public class GarsonController {

    public ArrayList<Masa> masalariGetir() {
        ArrayList<Masa> list = new ArrayList<>();

        list.add(new Masa(1, "BOŞ"));
        list.add(new Masa(2, "DOLU"));
        list.add(new Masa(3, "REZERVE"));
        list.add(new Masa(4, "BOŞ"));
        list.add(new Masa(5, "BOŞ"));
        list.add(new Masa(6, "REZERVE"));
        list.add(new Masa(7, "BOŞ"));
        list.add(new Masa(8, "BOŞ"));

        return list;
    }
}
