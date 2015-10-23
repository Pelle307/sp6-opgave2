/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author noncowi
 */
public class CallableSP {
    private static ExecutorService executor = Executors.newFixedThreadPool(4);
    private static List<Future<String>> list = new ArrayList<>();
    
    public static List<String> urls = new ArrayList<String>() {
        {

            //Class A
            add("http://cphbusinessjb.cloudapp.net/CA2/");
            add("http://ca2-ebski.rhcloud.com/CA2New/");
            add("http://ca2-chrislind.rhcloud.com/CA2Final/");
            add("http://ca2-pernille.rhcloud.com/NYCA2/");
            add("https://ca2-jonasrafn.rhcloud.com:8443/company.jsp");
            add("http://ca2javathehutt-smcphbusiness.rhcloud.com/ca2/index.jsp");

            //Class B
            add("https://ca2-ssteinaa.rhcloud.com/CA2/");
            add("http://tomcat-nharbo.rhcloud.com/CA2/");
            add("https://ca2-cphol24.rhcloud.com/3.semCa.2/");
            add("https://ca2-ksw.rhcloud.com/DeGuleSider/");
            add("http://ca2-ab207.rhcloud.com/CA2/index.html");
            add("http://ca2-sindt.rhcloud.com/CA2/index.jsp");
            add("http://ca2gruppe8-tocvfan.rhcloud.com/");
            add("https://ca-ichti.rhcloud.com/CA2/");

            //Class COS
            add("https://ca2-9fitteen.rhcloud.com:8443/CA2/");
            add("https://cagroup04-coolnerds.rhcloud.com/CA_v1/index.html");
            add("http://catwo-2ndsemester.rhcloud.com/CA2/");
        }
    };
    public static void main(String[] args) throws InterruptedException {

        for (final String url : urls) {
           
            
            Callable<String> task = new Callable<String>() {
                
                @Override
                public String call() throws Exception {
                    Document doc;
                    try{
                    doc = Jsoup.connect(url).get();
                    }
                    catch(IOException ex){
                        doc =null;
                    }
                    if(doc!=null){
                    Elements authors = doc.select("#authors");
                    String author = authors.text();
                    Elements klasse = doc.select("#class");
                    String klass = klasse.text();
                    Elements group = doc.select("#group");
                    String groups = group.text();
                    return "url: " + url + " id: " + klass + " groupNumber: " + groups;
                    }else{
                        return "could not connect to website";
                    }
                }
            };
            Future<String> future = executor.submit(task);
            
            //add Future to the list, we can get return value using Future
            list.add(future);
            
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
        for (Future<String> fut : list) {
            try {
                System.out.println(fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        
    }
//    public void getData(String url) throws IOException{
//        Document doc = Jsoup.connect("http://catwo-2ndsemester.rhcloud.com/CA2/ ").get();
//        Elements authors = doc.select("#authors");
//        String author = authors.text();
//        Elements iD = doc.select("#id");
//        String ID = iD.text();
//        Elements group = doc.select("#group");
//        String groups = group.text();
//        
//        System.out.println("");
//    }

    
}
