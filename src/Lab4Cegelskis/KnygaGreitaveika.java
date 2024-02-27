/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab4Cegelskis;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import laborai.demo.Timekeeper;
import laborai.gui.MyException;
import laborai.studijosktu.HashType;
import laborai.studijosktu.MapKTUx;

/**
 *
 * @author zygis
 */
public class KnygaGreitaveika {
    
    public static final String FINISH_COMMAND = "finishCommand";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");

    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private final Timekeeper tk;
    private final String[] TEST_NAMES = {"add0.75", "add0.25", "get0.75", "get0.25", "rem0.75", "rem0.25"};
    private final int[] TESTED_AMOUNTS = {10000, 20000, 40000, 80000};
    //private final int[] TESTED_AMOUNTS = {100, 1000, 10000, 100000, 1000000, 10000000};

    private final MapKTUx<String, Knyga> booksMap
            = new MapKTUx(new String(), new Knyga(), 10, 0.75f, HashType.DIVISION);
    private final MapKTUx<String, Knyga> booksMap2
            = new MapKTUx(new String(), new Knyga(), 10, 0.25f, HashType.DIVISION);
    
    private final Knyga_MapX<String, Knyga> booksMapCustom
            = new Knyga_MapX(new String(), new Knyga(), 10, 0.75f, HashType.DIVISION);
    private final Knyga_MapX<String, Knyga> booksMap2Custom
            = new Knyga_MapX(new String(), new Knyga(), 10, 0.25f, HashType.DIVISION);
    
    private final Queue<String> chainsSizes = new LinkedList<>();
    
    public KnygaGreitaveika() {
        semaphore.release();
        tk = new Timekeeper(TESTED_AMOUNTS, resultsLogger, semaphore);
    }

    public void pradetiTyrima(boolean custom) {
        try {
            if(!custom)
                mapKTUTest();
            else
                books_MapTest();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void mapKTUTest() throws InterruptedException {
        try {
            chainsSizes.add(MESSAGES.getString("msg4"));
            chainsSizes.add("   kiekis      " + TEST_NAMES[0] + "   " + TEST_NAMES[1]);
            for (int k : TESTED_AMOUNTS) {
                Knyga[] bookArray = KnyguGamyba.createBooks(k);
                String[] booksIdArray = KnyguGamyba.createBooksIds(k);
                booksMap.clear();
                booksMap2.clear();
                tk.startAfterPause();
                tk.start();

                for (int i = 0; i < k; i++) {
                    booksMap.put(booksIdArray[i], bookArray[i]);
                }
                tk.finish(TEST_NAMES[0]);

                String str = "   " + k + "          " + booksMap.getMaxChainSize();
                for (int i = 0; i < k; i++) {
                    booksMap2.put(booksIdArray[i], bookArray[i]);
                }
                tk.finish(TEST_NAMES[1]);

                str += "         " + booksMap2.getMaxChainSize();
                chainsSizes.add(str);
                for (String s : booksIdArray) {
                    booksMap2.get(s);
                }
                tk.finish(TEST_NAMES[2]);

                for (String s : booksIdArray) {
                    booksMap2.get(s);
                }
                tk.finish(TEST_NAMES[3]);
                for (String s : booksIdArray) {
                    booksMap.remove(s);
                }
                tk.finish(TEST_NAMES[4]);

                for (String s : booksIdArray) {
                    booksMap2.remove(s);
                }
                tk.finish(TEST_NAMES[5]);
                tk.seriesFinish();
            }

            StringBuilder sb = new StringBuilder();
            chainsSizes.stream().forEach(p -> sb.append(p).append(System.lineSeparator()));
            tk.logResult(sb.toString());
            tk.logResult(FINISH_COMMAND);
        } catch (MyException e) {
            tk.logResult(e.getMessage());
        }
    }
    
    public void books_MapTest() throws InterruptedException {
        try {
            //chainsSizes.add(MESSAGES.getString("msg4"));
            //chainsSizes.add("   kiekis      " + TEST_NAMES[0] + "   " + TEST_NAMES[1]);
            for (int k : TESTED_AMOUNTS) {
                Knyga[] booksArray = KnyguGamyba.createBooks(k);
                String[] booksIdArray = KnyguGamyba.createBooksIds(k);
                booksMapCustom.clear();
                booksMap2Custom.clear();
                tk.startAfterPause();
                tk.start();

                for (int i = 0; i < k; i++) {
                    booksMapCustom.put(booksIdArray[i], booksArray[i]);
                }
                tk.finish(TEST_NAMES[0]);

                //String str = "   " + k + "          " + cpuMapCustom.getMaxChainSize();
                for (int i = 0; i < k; i++) {
                    booksMap2Custom.put(booksIdArray[i], booksArray[i]);
                }
                tk.finish(TEST_NAMES[1]);

                //str += "         " + cpuMap2Custom.getMaxChainSize();
                //chainsSizes.add(str);
                for (String s : booksIdArray) {
                    booksMap2Custom.get(s);
                }
                tk.finish(TEST_NAMES[2]);

                for (String s : booksIdArray) {
                    booksMap2Custom.get(s);
                }
                tk.finish(TEST_NAMES[3]);
                for (String s : booksIdArray) {
                    booksMapCustom.remove(s);
                }
                tk.finish(TEST_NAMES[4]);

                for (String s : booksIdArray) {
                    booksMap2Custom.remove(s);
                }
                tk.finish(TEST_NAMES[5]);
                tk.seriesFinish();
            }

            //StringBuilder sb = new StringBuilder();
            //chainsSizes.stream().forEach(p -> sb.append(p).append(System.lineSeparator()));
            //tk.logResult(sb.toString());
            tk.logResult(FINISH_COMMAND);
        } catch (MyException e) {
            tk.logResult(e.getMessage());
        }
    }

    public BlockingQueue<String> getResultsLogger() {
        return resultsLogger;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
    
}
