/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab4Cegelskis;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import laborai.gui.MyException;

/**
 *
 * @author zygis
 */
public class KnyguGamyba {
    
    private static final String ID_CODE = "Knyga";
    private static int Nr = 100;
    private Knyga[] knygos;
    private String[] raktai;
    private int amount = 0;
    private int idAmount = 0;
    
    public static Knyga[] createBooks(int amount) {
        Knyga[] knygos = IntStream.range(0, amount)
                .mapToObj(i -> new Knyga.Builder().buildRandom())
                .toArray(Knyga[]::new);
        Collections.shuffle(Arrays.asList(knygos));
        return knygos;
    }
    
    public static String[] createBooksIds(int amount) {
        String[] raktai = IntStream.range(0, amount)
                .mapToObj(i -> ID_CODE + (Nr++))
                .toArray(String[]::new);
        Collections.shuffle(Arrays.asList(raktai));
        return raktai;
    }
    
    public Knyga[] createAndSellBooks(int setSize,
            int setRequiredSize) throws MyException {
        if (setRequiredSize > setSize) {
            setRequiredSize = setSize;
        }
        knygos = createBooks(setSize);
        raktai = createBooksIds(setSize);
        this.amount = setRequiredSize;
        return Arrays.copyOf(knygos, setRequiredSize);
    }
    
    // Imamas po vienas elementas iš sugeneruoto masyvo. Kai elementai baigiasi sugeneruojama
    // nuosava situacija ir išmetamas pranešimas.
    public Knyga sellBooks() {
        if (knygos == null) {
            throw new MyException("BooksNotGenerated");
        }
        if (amount < knygos.length) {
            return knygos[amount++];
        } else {
            throw new MyException("allSetStoredToMap");
        }
    }
    
    public String getBooksIdFromDatabase() {
        if (raktai == null) {
            throw new MyException("booksIdsNotGenerated");
        }
        if (idAmount >= raktai.length) {
            idAmount = 0;
        }
        return raktai[idAmount++];
    }
}
