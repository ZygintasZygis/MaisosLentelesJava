/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab4Cegelskis;

import java.util.Locale;
import laborai.studijosktu.HashType;
import laborai.studijosktu.Ks;
import laborai.studijosktu.MapKTUx;

/**
 *
 * @author zygis
 */
public class KnyguTestai {
    
    public static void main(String[] args) {
        Locale.setDefault(new Locale("LT")); // Suvienodiname skaičių formatus
        mapTest();
        //greitaveikosTestas();
    }
    
    
    public static void mapTest() {
        Knyga k1 = new Knyga("Knyga30", "Kategorija30", 50.23, "Autorius30", 500, 2006);
        Knyga k2 = new Knyga("Knyga31", "Kategorija31", 50.56, "Autorius31", 550, 2007);
        Knyga k3 = new Knyga("Knyga32", "Kategorija32", 50.78, "Autorius32", 600, 2008);
        Knyga k4 = new Knyga("Knyga33", "Kategorija33", 53.20, "Autorius33", 420, 2009);
        Knyga k5 = new Knyga.Builder().buildRandom();
        Knyga k6 = new Knyga("Knyga34", "Kategorija34", 35.30, "Autorius34", 305, 2010);
        Knyga k7 = new Knyga("Knyga35", "Kategorija35", 25.50, "Autorius35", 150, 2011);
        Knyga k8 = new Knyga("Knyga36", "Kategorija36", 28.30, "Autorius36", 600, 2012);
        // Raktų masyvas
        String[] booksId = {"Knyga156", "Knyga102", "Knyga178", "Knyga171", "Knyga105", "Knyga106", "Knyga107", "Knyga108"};
        int id = 0;
        MapKTUx<String, Knyga> map
                = new MapKTUx(new String(), new Knyga(), HashType.DIVISION);
        // Reikšmių masyvas
        Knyga[] books = {k1, k2, k3, k4, k5, k6, k7};
        for (Knyga c : books) {
            map.put(booksId[id++], c);
        }
        map.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Ar egzistuoja pora atvaizdyje? " + booksId[6]);
        Ks.oun(map.contains(booksId[6]));
        Ks.oun("Ar egzistuoja pora atvaizdyje? " + booksId[7]);
        Ks.oun(map.contains(booksId[7]));
        Ks.oun("Pašalinamos poros iš atvaizdžio: " + booksId[1] + " ir " + booksId[7]);
        Ks.oun(map.remove(booksId[1]));
        Ks.oun(map.remove(booksId[7]));
        Ks.oun("Atvaizdžio papildymas pasirinkta pora: " + booksId[7] + " " + k8);
        map.put(booksId[7], k8);
        map.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Atliekame porų paiešką atvaizdyje: " + booksId[2] + " ir " + booksId[7]);
        Ks.oun(map.get(booksId[2]));
        Ks.oun(map.get(booksId[7]));
        Ks.oun("Išspausdiname atvaizdžio poras String eilute:");
        Ks.ounn(map);
    }
    
    //Konsoliniame režime
    private static void speedTest() {
        System.out.println("Greitaveikos tyrimas:\n");
        KnygaGreitaveika gt = new KnygaGreitaveika();
        //Šioje gijoje atliekamas greitaveikos tyrimas
        new Thread(() -> gt.pradetiTyrima(false),
                "Greitaveikos_tyrimo_gija").start();
        try {
            String result;
            while (!(result = gt.getResultsLogger().take())
                    .equals(KnygaGreitaveika.FINISH_COMMAND)) {
                System.out.println(result);
                gt.getSemaphore().release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        gt.getSemaphore().release();
    }
    
}
