/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab4Cegelskis;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;

/**
 *
 * @author zygis
 */
public final class Knyga implements KTUable{
    
    private String name;
    private String category;
    private double price;
    private String author;
    private int pageNumber;
    private int printYear;
    
    public Knyga(){}
    
    public Knyga(String n, String c, double p, String a, int pn, int py){
        this.name = n;
        this.category = c;
        this.price = p;
        this.author = a;
        this.pageNumber = pn;
        this.printYear = py;
    }
    
    public Knyga(String e){
        this.parse(e);
    }

    @Override
    public Knyga create(String dataString) {
        return new Knyga(dataString);
    }

    @Override
    public String validate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void parse(String e) {
        try{
            Scanner sc = new Scanner(e);
            this.name = sc.next();
            this.category = sc.next();
            this.price = sc.nextDouble();
            this.author = sc.next();
            this.pageNumber = sc.nextInt();
            this.printYear = sc.nextInt();
        }catch(InputMismatchException d) {
            Ks.ern("Blogas duomenų formatas apie auto -> " + e);
        } catch (NoSuchElementException d) {
            Ks.ern("Trūksta duomenų apie auto -> " + e);
        }
    }
    
    @Override
    public String toString(){
        return name + "_" + category + "_" + price + "_" + author + "_" + pageNumber + "_" + printYear;
    }
    
    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }
    public double getPrice(){
        return price;
    }
    public String getAuthor(){
        return author;
    }
    public int getPageNumber(){
        return pageNumber;
    }
    public int getPrintYear(){
        return printYear;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(name,category,price,author,pageNumber,printYear);
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        Knyga newtemp = (Knyga) obj;
        if(!Objects.equals(this.name, newtemp.name)){
            return false;
        }
        if(!Objects.equals(this.category, newtemp.category)){
            return false;
        }
        if(Double.doubleToLongBits(this.price) != Double.doubleToLongBits(newtemp.price)){
            return false;
        }
        if(!Objects.equals(this.author, newtemp.author)){
            return false;
        }
        if(this.pageNumber != newtemp.pageNumber){
            return false;
        }
        if(this.printYear != newtemp.printYear){
            return false;
        }
        return true;            
    }
    
    public static class Builder {

        private final static Random RANDOM = new Random(2005);  // Atsitiktinių generatorius
        private final static String[][] MODELIAI = { // galimų automobilių markių ir jų modelių masyvas
            {"Knyga1", "Kategorija1","Autorius1"},
            {"Knyga2", "Kategorija2","Autorius2"},
            {"Knyga3", "Kategorija3","Autorius3"},
            {"Knyga4", "Kategorija4","Autorius4"},
            {"Knyga5", "Kategorija5","Autorius5"},
            {"Knyga6", "Kategorija6","Autorius6"},
            {"Knyga7", "Kategorija7","Autorius7"}
        };

        private String name = "";
        private String category = "";
        private double price = -1.0;
        private String author = "";
        private int pageNumber = -1;
        private int printYear = -1;

        public Knyga build() {
            return new Knyga(this);
        }

        public Knyga buildRandom() {
            int ma = RANDOM.nextInt(MODELIAI.length);             
            return new Knyga(MODELIAI[ma][0],
                    MODELIAI[ma][1],
                    20 + RANDOM.nextDouble(40),
                    MODELIAI[ma][2],
                    200 + RANDOM.nextInt(400),
                    1990 + RANDOM.nextInt(33));
        }

        public Builder printYear(int py) {
            this.printYear = py;
            return this;
        }

        public Builder name(String n) {
            this.name = n;
            return this;
        }

        public Builder category(String c) {
            this.category = c;
            return this;
        }

        public Builder author(String a) {
            this.author = a;
            return this;
        }

        public Builder price(double p) {
            this.price = p;
            return this;
        }
        
        public Builder pageNumber(int pn){
            this.pageNumber = pn;
            return this;
        }
    }
}
