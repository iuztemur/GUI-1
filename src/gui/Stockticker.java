/*
Wellesley Arreza
wra216
CSE 241 Project
 */
package gui;

import java.awt.Component;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Wellesley
 */
public class Stockticker {

    private Connection con = null;

    public Stockticker() {

    }

    // main method
    public static void main(String[] args) {
        Stockticker s = new Stockticker();
        s.run(args[0]);

    }

    public boolean tickcheck(String t) {
        boolean flag = true;
        t=t.trim();
        //System.out.println("Input String : "+t);
        for (int i = 0; i < t.length(); i++) {
            if ((!(Character.isLetter(t.charAt(i)))) || t.equals("") ){

                flag = false;
            }

        }
        if (!flag) {
            System.out.println("Testing : Ticker is invalid  :  " + t);
        } else {
            System.out.println("Testing : Ticker is valid");
        }
        return flag;
    }

    public boolean timecheck(String t) {
        boolean flag = false;
        if (t.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}:[0-9]{2}/[0-9]{2}/[0-9]{2}[A|P][M]")) {
// yyyy/mm/dd:hh/mi/ss AM
            System.out.println("Timestamp does match");
            return true;
        }

        System.out.println("Timestamp doesn't match : " + t);
        return flag;

    }

    public boolean pcheck(String p) {
        boolean flag = true;
        char c = '.';
        Scanner s = new Scanner(p.replaceAll(" ",""));
        if (s.hasNextDouble()) {
            double d = s.nextDouble();
            String price = Double.toString(d);
            String[] split = price.split("\\.");
            int length = split[1].length();

            if (length <= 2) {
                System.out.println("Price is valid");
                flag = true;
            } else {
                System.out.println("Price is invalid. More than two decimal places :  " + p);
                flag = false;
            }

        } else {
            System.out.println("Price is invalid :  " + p);
            flag = false;
        }
        s.close();
        return flag;
    }

    public String convert(String t) {
        try {
            //  28-MAR-14 10.50.12.739119000 PM
            //1992/10/04:10/32/32AM
            SimpleDateFormat s = new SimpleDateFormat("dd-MMM-yy hh.mm.ss a");
            //1992/10/04:10/32/32AM
            SimpleDateFormat old = new SimpleDateFormat("yyyy/MM/dd:hh/mm/ssa");
            Date date = old.parse(t);
            String str = s.format(date);
            System.out.println("Converted Date :" + str);
            return str;
        } catch (ParseException ex) {
            Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Invalid Date");
        return "Invalid Date";
    }

    public boolean compare(String t) {
// checks if date is <= current_timestamp. we dont want year 9999

        return true;
    }

    public void update(String str) {
        try {
            Statement s = con.createStatement();

            int chk = s.executeUpdate(str);
            if (chk == 0) {
                System.out.println("Update error.  String :" + str);
            }
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean qCheck(String q){
         boolean flag = true;
        char c = '.';
        Scanner s = new Scanner(q.replaceAll(" ",""));
        if (s.hasNextInt()) {
            int d = s.nextInt(); 
        } else {
            System.out.println("Input String is invalid :  " + q);
            flag = false;
        }
        s.close();
        return flag;
    }

    public void write() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("stocklist.txt");
            boolean found = true;
            String str = "";
            str += (String.format("%-15s", "ticker") + " \t ");
            str += (String.format("%-30s", "name") + " \t ");
            str += (String.format("%-15s", "price") + " \t ");
            str += (String.format("%-15s", "volume") + " \t ");
            str += (String.format("%-40s", "time") + " \t ");
            str += System.getProperty("line.separator");
                  
                    
                    
            try {
                Statement s = con.createStatement();

                ResultSet result = s.executeQuery("select * from stock");

                ResultSetMetaData rsmd;
                if (!result.next()) {
                    System.out.println("Empty Stock Data");
                } else {
                    rsmd = result.getMetaData();
                    do {

//                        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//
//                            str += result.getString(i) + " ";
//
//                        }
                     
                        str += (String.format("%-15s", result.getString(1)) + " \t ");
                        str += (String.format("%-30s", result.getString(2)) + " \t ");
                        str += (String.format("%-15s", result.getString(3)) + " \t ");
                        str += (String.format("%-15s", result.getString(4)) + " \t ");
                        str += (String.format("%-40s", result.getString(5)) + " \t ");
                        
                        
                        
                        
                        String newline = System.getProperty("line.separator");
                        fw.write(str + newline);
                        System.out.println(str);
                        str = "";
                    } while (result.next());

                }
                fw.close();
                s.close();

            } catch (SQLException ex) {
                Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            System.out.println("Invalid File Path for StockTicker :"+ex.toString());
            Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean findstock(String str) {
        boolean found = true;
        try {
            Statement s = con.createStatement();
            String q = "select ticker from stock where ticker = '" + str + "'";
            ResultSet result = s.executeQuery(q);

            if (!result.next()) {
                System.out.println("Empty result.");
                found = false;
                s.close();
            } else {
                do {

                    found = true;
                } while (result.next());
            }

            s.close();

        } catch (SQLException ex) {
            Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return found;
    }

    public void run(String args) {
        System.out.println("StockTicker BEGIN:");
        String ticker = "";
        String time = "";
        String price = "";
        boolean found;
//    try{
//    Class.forName("oracle.jdbc.driver.OracleDriver");
//    con=DriverManager.getConnection(
//        "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241","wra216",
//       "P810339776");
//      System.out.println("connection successfully made.");      
//      String q;
//      
//      //find the stock
//      found=findstock(ticker);
//      if(found){
//          //updates
//          q="update stock set date="+time+", price="+price+" where ticker="+ticker+";";
//          update(q);
//      }
//      else{
//          //inserts
//          q="insert into stock(ticker,time,price) values("+ticker+","+time+","+price;
//          update(q);
//      }
//     
//      write();
//      con.close();
//      
//    }catch(Exception e){
//       e.printStackTrace();
//   }     

        Scanner ourScanner;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", "wra216",
                    "P810339776");
            System.out.println("connection successfully made.");
            String q;

            int count = 0;
            ourScanner = new Scanner(new File(args));
            System.out.println("BEGIN");
            ourScanner.useDelimiter(",");
            String[] arr = new String[3];
            
        // separate by comma whether or not there is a new line
            // take that string
            // split it into an array.
            // store the parts.

            while (ourScanner.hasNext()) {
                System.out.println("Loop = "+count);
                
                String line = ourScanner.next();
                System.out.println("line"+line);
                line = line.replaceAll("[\n\r]", "");
   
                arr = line.split("[ ]+");
//                 System.out.println("length" +arr.length);
                if(arr.length<3){
                    System.out.println("length" +arr.length);
                    System.out.println("UPDATE SKIPPED");
                    continue;
                }
                boolean tchk = tickcheck(arr[0].trim());
               
               
                
                // ticker check
                if (!tchk) {
                    System.out.println("Ticker input error");
                    System.out.println("UPDATE SKIPPED");
                    continue;
                }
                else{
                     ticker = arr[0];
                     System.out.println("Ticker test success. Ticker :  " + ticker);
                }

                time = arr[1];
                System.out.println("Time :" + time);
                boolean timechk = timecheck(time);
                String conv = "Invalid Date";
                // date check
                if (timechk) {
                    conv = convert(time);
                    if (conv.equals("Invalid Date")) {
                        System.out.println("UPDATE SKIPPED");
                        continue;
                    }
                } else {
                    System.out.println(conv);
                    System.out.println("UPDATE SKIPPED");
                    continue;
                }
                boolean comp = compare(time);

                System.out.println(arr[2]);
                boolean pchk = pcheck(arr[2]);
                if (pchk) {
                    price = arr[2];
                } else {
                    System.out.println("Invalid Price");
                    System.out.println("UPDATE SKIPPED");
                    continue;
                }

                System.out.println("Price : " + price);

                if (tchk && timechk && (!conv.equals("Invalid Date")) && comp && pchk) {
                    // execute sql statements and updates.
                    System.out.println("Successful Reading We Are now Updating with queries");
                    //find the stock
                    found = findstock(ticker);
                    if (found) {
                        //updates
                        q = "update stock set time = '" + conv + "' , price = '" + price + "' where ticker = '" + ticker + "'";
                        update(q);
                    } else {
                        //inserts
                        q = "insert into stock(ticker,name,time,volume,price) values( '" + ticker + "' , '"
                                + ticker + "' , '" + conv + "' ,0,'" + price + "')";
                        update(q);
                    }

                //write out to new file
                } else {
                    System.out.println("Update for : " + ticker + " has been skipped for errors");
                    System.out.println("UPDATE SKIPPED");
                    continue;
                }

            }

            System.out.println("END OF FILE");

            write();
            ourScanner.close();
            con.close();
            count++;
        } catch (FileNotFoundException ex) {
            System.out.println("File exception Error in Stockticker class :" + ex.toString());
            System.out.println("Stock ticker cancelled");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("Stock ticker cancelled");
        } catch (Exception ex) {
            System.out.println(ex.toString());
             System.out.println("Stock ticker cancelled");
        }

    }
}

/*

Sql query
Select ticker from stock where ticker='ticker';

if( no results)
Insert into stock(ticker,timestamp,price) values(ticker,timestamp,price)

else( if found)
update stock
set ticker=ticker, timestamp=timestamp, price=price;
where ticker='ticker';


Scanner ourScanner = new Scanner("Rockefeller;John;43;180.5")
ourScanner.useDelimiter(";");


try{
    FileWriter fw = new FileWriter(Filename);
    fw.write("My first written file!\n"); //\n signifies a new line
    fw.write("Hello World!\n");
    fw.close();
  }
  catch(IOException ex) //Did we generate an error?
  {
    System.out.println("Could not write to file"); //Explain what happened
    System.exit(0); //Exit gracefully
  }



*/
