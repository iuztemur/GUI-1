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

//take in an id as a parameter
// output trades to a file.

//
public class ProgTrader {

    
    private Connection con=null; 
    
    
    
    public ProgTrader() {
        
    
    }
    
    // main method
public static void main(String[] args){
  //args[1]= File that contains transactions
    //args[2]= Account ID
    
    ProgTrader s=new ProgTrader();
    //s.run(args[0]);
    s.run("1");
    
    //investmentID
    //action (buy/sell)
    //numberOfShares
    //trade ticker, action, quantity
    
    
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

public String findInvestment(String ticker, String type) {
        String price = "";
        System.out.println(ticker);
        System.out.println(type);
        try {
            Statement s = con.createStatement();
            String q = "select ticker, action, quantity from " + type + " where ticker = '" + ticker + "'";
            ResultSet result = s.executeQuery(q);
            ResultSetMetaData rsmd;

            if (!result.next()) {
                System.out.println("Investment does not exist");

                s.close();
            } else {
                rsmd = result.getMetaData();
                do {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        price = result.getString(i);

                    }
                } while (result.next());

            }

            s.close();

        } catch (SQLException ex) {
            System.out.println("Error When Trying to Find Investment.");
        }

        return price;
    }


public void write(String id) {
    //select ticker,action,quantity from trade natural join owns where customer_id=1;
    System.out.println("Program Trader BEGIN:");
        FileWriter fw = null;
        try {
            fw = new FileWriter("trades.txt");
            boolean found = true;
            String str = "";
            boolean numcheck=qCheck(id);
            // if the id isnt an integer.
            if(numcheck){
            try {
                Statement s = con.createStatement();
                String q = "select ticker, action, quantity from trade natural join owns where customer_id="+id;
                ResultSet result = s.executeQuery(q);
                str += (String.format("%-30s", "ticker") + " \t ");
                str += (String.format("%-15s", "action") + " \t ");
                str += (String.format("%-15s", "shares") + " \t ");
                str += System.getProperty("line.separator");        
                
                
                ResultSetMetaData rsmd;
                if (!result.next()) {
                    System.out.println("Empty Stock Data");
                } else {
                    rsmd = result.getMetaData();
                    do {

                        str += (String.format("%-30s", result.getString(1)) + " \t ");
                        str += (String.format("%-15s", result.getString(2)) + " \t ");
                        str += (String.format("%-15s", result.getString(3)) + " \t ");
                        
                
                        String newline = System.getProperty("line.separator");
                        fw.write(str + newline);
                        System.out.println(str);
                        str = "";
                    } while (result.next());

                }
                fw.close();
                s.close();
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                Logger.getLogger(ProgTrader.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            else{
                System.out.println("Invalid ID for ProgTrader Class");
            }
        } catch (IOException ex) {
            Logger.getLogger(ProgTrader.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception ex){
              Logger.getLogger(ProgTrader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }




public boolean tickcheck(String t){
boolean flag=true;
for(int i=0; i< t.length(); i++)
{
if(!(Character.isLetter(t.charAt(i)))){
    
    flag=false;
}

}
if(!flag){
    System.out.println("Ticker is invalid  :  " + t);
}
else{
System.out.println("Ticker is valid");
}
return flag;
}

public boolean timecheck(String t){
boolean flag=false;
if(t.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}:[0-9]{2}/[0-9]{2}/[0-9]{2}[A|P][M]")){
// yyyy/mm/dd:hh/mi/ss AM
    System.out.println("Timestamp does match");
return true;
}

System.out.println("Timestamp doesn't match : "+t);
return flag;

}

public boolean pcheck(String p){
boolean flag=true;
char c='.';
Scanner s= new Scanner(p.replaceAll(" ",""));
//for(int i=0; i< p.length(); i++)
//{
//if(!(Character.isDigit(p.charAt(i))) ||  ){
//    System.out.println("Price is invalid");
//    flag=false;
//}
//
//}
if(s.hasNextDouble()){
    double d=s.nextDouble();
    String price=Double.toString(d);
    String[] split=price.split("\\.");
    int length=split[1].length();
    
    if(length<=2)
    {
        System.out.println("Price is valid");
    flag=true;
    }
    else{
        System.out.println("Price is invalid. More than two decimal places :  " + p);
    flag=false;
    }
   
}
else
{
    System.out.println("Price is invalid :  " + p);
    flag=false;
}
s.close();
return flag;
}


public void run(String args){

    boolean found;
    Scanner ourScanner;
    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
    con=DriverManager.getConnection(
        "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241","wra216",
       "P810339776");
       int count = 0;
//            ourScanner = new Scanner(System.in);
//            System.out.println("BEGIN");
//            ourScanner.useDelimiter("[\r\n]+");
//            String[] arr = new String[3];
     write(args);
     //input
//     
//      set delimiter to \n
//      while (ourScanner.hasNext()) {
//                System.out.println("Lopp = "+count);
//                
//                String line = ourScanner.next();
//                System.out.println("line"+line);
//   
//                arr = line.split("[ ]+");
//                 System.out.println("length" +arr.length);
//                if(arr.length<3){
//                    System.out.println("length" +arr.length);
//                    System.out.println("UPDATE SKIPPED");
//                    continue;
//                }
//                
//               System.out.println(arr[0]);
//               System.out.println(arr[1]);
//               System.out.println(arr[2]);
//                ticker action quantity
//               String ticker=arr[0];
//               String action=arr[1];
//               String quantity=arr[2];               
//               
              
               //SELLING
               // use the FIFO method
               //select t_date, acc_number, transaction_id, ticker, action, quantity, price  from transaction natural join trade order by t_date 
//              int[] sellarr= getTrade
               // return array with quantity_purchased and price_purchased and acc_number
               
               // check if arr[0] quantity is <= quantity_purchased
               // if not check if arr[n+1] quantity is until array.length
              
               // calculate NOI=(price - price_purchased) * quantity
              
               // update transaction = sell at price
               // update trade = sell at price at quantity
               
               // use the acc_number
               // update investment account quantity_purchased -= quantity              
               
               // by default cash will debited to checking in cash mgm
              
              
               



               // BUYING
               
               
               // search for investment
//               boolean stock= searchStock(ticker);
//               boolean mf = searchMF(ticker);
//               String type="";
//               if(stock){
//                   type="stock";
//               }
//               else if(mf){
//                   type="mutual_funds";
//               }
//               
//                search stocks table for price
//               String price= findPrice(ticker,type);
//                check price
//               boolean pchk=pcheck(price);
//               
//               if(!pchk){
//                   System.out.println("Invalid Price");
//                   continue;
//               }
//                calculate expense
//               double expense= Double.parseDouble(price) * Integer.parseInt(quantity);
//               int[] casharr=getCashAcc();
//               double balance=0;
//               int act;
//               for(int i=0; i< casharr.length; i++){
//                   balance=findFunds();
//                   if(balance >=expense){
//                       act=i; // record account that can afford expense
//                   }
//                    find funds for checking
//               }
//               
//               for(int i=0; i< casharr.length; i++){
//                   balance=findFunds();
//                    find funds for savings
//                   if(balance >=expense){
//                       act=i; // record account that can afford expense
//                   }
//               }
//                query all of the cash mgm accounts of this customer
//                store into an array
//                while( true)
//                check each cash management account for funds
//               check cash checking (default)
//               double chking=findFunds();
//                check cash savings
//                if balance >= expense
//                credit balance for that account
//               
//                update transactions
//                update trades
//                update investment_acc
//               
// 
//            
               

            

            System.out.println("END OF FILE");

          
//            ourScanner.close();
            con.close();
            count++;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error in Program Trader : "+ ex.toString());
        }  catch (Exception ex) {
            System.out.println("Probable error in ProgTrader "+ex.toString());
        }
}

public String findPrice(String ticker, String type) {
        String price = "";
        System.out.println(ticker);
        System.out.println(type);
        try {
            Statement s = con.createStatement();
            String q = "select price from " + type + " where ticker = '" + ticker + "'";
            ResultSet result = s.executeQuery(q);
            ResultSetMetaData rsmd;

            if (!result.next()) {
                System.out.println("Investment does not exist");

                s.close();
            } else {
                rsmd = result.getMetaData();
                do {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        price = result.getString(i);

                    }
                } while (result.next());

            }

            s.close();

        } catch (SQLException ex) {
            System.out.println("Error When Trying to Find Investment.");
        }

        return price;
    }


public boolean findinvestment(String str, String type) {
        boolean found = true;
        try {
            Statement s = con.createStatement();
            String q = "select ticker from " + type + " where ticker = '" + str + "'";
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
            System.out.println("Error When Trying to Find Investment.");
        }

        return found;
    }

    public int findShares(String str, String type, String acc_number) {
        int amount = 0;
        try {
//            String text = account_field.getText().toString().trim();
            Statement s = con.createStatement();
            String q = "select shares from investment_acc where ticker = '" + str + "' AND acc_number = " + acc_number;
            ResultSet result = s.executeQuery(q);

            if (!result.next()) {
                System.out.println("No shares were found.");
                amount = 0;
                s.close();
            } else {
                ResultSetMetaData rsmd;
                rsmd = result.getMetaData();
                do {

                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        amount = Integer.parseInt(result.getString(i));

                    }

                } while (result.next());
            }

            s.close();

        } catch (SQLException ex) {
            System.out.println("Error. Cannot find Invesment Account");
        }

        return amount;
    }

    public String getBalance(String table, String ticker, String id) {
        String str = "0";
        try {
//            String text = account_field.getText().toString().trim();
            Statement s = con.createStatement();
            String q = "select amount from " + table + " where ticker = '" + ticker + "' AND acc_number = " + id;
            ResultSet result = s.executeQuery(q);

            if (!result.next()) {
                System.out.println("Empty result.");
                str = "0";
                s.close();
            } else {
                ResultSetMetaData rsmd;
                rsmd = result.getMetaData();
                do {

                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        str = result.getString(i);

                    }

                } while (result.next());
            }

            result.close();
            s.close();

        } catch (SQLException ex) {
            System.out.println("Error. Cannot find Invesment Account");
        }

        return str;

    }

    public boolean search_invest_act(String ticker, String acc_number) {
        boolean found = false;
        try {
//            String text = account_field.getText().toString().trim();
            Statement s = con.createStatement();
            String q = "select acc_number from investment_acc where ticker = '" + ticker + "' AND acc_number = " + acc_number;
            ResultSet result = s.executeQuery(q);

            if (!result.next()) {
                System.out.println("Empty result.");

                s.close();
            } else {
                ResultSetMetaData rsmd;
                rsmd = result.getMetaData();
                do {

                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        found = true;

                    }

                } while (result.next());

            }

            result.close();
            s.close();

        } catch (SQLException ex) {
            System.out.println("Error. Cannot find Invesment Account");
        }

        return found;
    }

    public void buySecurity(String id, String cashid, String ticker, String security,
            int quantity, double price, double funds, String cashtype) {

        // search for how much shares do you own
        int amount = findShares(ticker, security, id);
        // calculate expense
        double expense = quantity * price;
        // retrieve current balance of account
        double balance = Double.parseDouble(getBalance("investment_acc", ticker, id));
        balance += expense;
        System.out.println("new Balance : " + balance);
        amount += quantity;
        //update investments
        updateInvestments(amount, ticker, id, "" + balance);

        //update transactions
        String tid = "" + updateTrans(expense, security);
        //update trades
        updateTrades(id, ticker, tid, security, "buy", price, quantity);

        double bal = funds - expense;

        //update cash mgm account
        updateCashAcc(cashid, bal, cashtype, "" + expense);

    }

    public double findfunds(String id, String type) {
        double amount = 0;
        try {

           
            Statement s = con.createStatement();
            String q = "select " + type + " from cash_mgm_acc where acc_number = " + id;
            ResultSet result = s.executeQuery(q);

            if (!result.next()) {
                System.out.println("Could not find funds in cash mgm account.");
                s.close();
            } else {
                ResultSetMetaData rsmd;
                rsmd = result.getMetaData();
                do {

                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        amount = Double.parseDouble(result.getString(i));

                    }

                } while (result.next());
            }

            s.close();

        } catch (SQLException ex) {
            System.out.println("Error When Trying to Find Balance.");
        }
        return amount;
    }

    public void sellSecurity(String id, String cashid, String ticker,
            String type, int quantity, double price, double funds, String cashtype) {
//        String main_acc = account_field.getText().toString().trim();

        // search for how much shares do you own
        int amount = findShares(ticker, type, id);

        if (amount == 0) {
            //delete investment tuple if you dont own any shares.
            updateInvestments(amount, "0", ticker, id);
        } else if (quantity > amount) {
            System.out.println("Error. You don't have enough shares.");
        } else {

            // retrieve current balance of account
            double balance = Double.parseDouble(getBalance("investment_acc", ticker, id));

            //calculate revenue
            double revenue = quantity * price;
        //update investments

            updateInvestments(amount, "" + revenue, ticker, id);
            System.out.println("Revenue earned :" + revenue);
            //update transactions
            String act_id = "" + updateTrans(revenue, type);
            //update trades
//            updateTrades(main_acc, ticker, act_id, type, "sell", price, quantity);

            double bal = funds + revenue;
            //update cash mgm account
            updateCashAcc(cashid, bal, cashtype, "" + revenue);
        }

    }

    public String getMaxID(String table, String column) {

        String id = "0";
        try {
//            String text = account_field.getText().toString().trim();
            Statement s = con.createStatement();
            //SELECT COALESCE(max(loan_id), 0) from loans
            String q = "select coalesce(max(" + column + "),0) from " + table;
            //select max(transaction_id) from transaction
            ResultSet result = s.executeQuery(q);

            if (!result.next()) {
                System.out.println("Could not find maximum id.");
                s.close();
                return "0";
            } else {
                ResultSetMetaData rsmd;
                rsmd = result.getMetaData();
                do {

                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        id = result.getString(i);

                    }

                } while (result.next());
            }

            s.close();

        } catch (SQLException ex) {
            System.out.println("Error When Trying to Find Max ID");
        } catch (NullPointerException ex) {
            id = "0";
            return id;
        }

        return id;

    }

    public void updateCashAcc(String id, double balance, String cashtype, String expense) {

        try {
            Statement s = con.createStatement();
            String q = "update cash_mgm_acc set " + cashtype + "=" + balance + ", date_last_accessed ="
                    + " current_timestamp where acc_number = " + id;
            int update = s.executeUpdate(q);
            if (update == 0) {
                System.out.println("Updating Cash MGM account Failed: Possible incorrect  id");
            }

            s.close();

        } catch (SQLException ex) {
            System.out.println("Error When Trying to update cash acct :" + ex.toString());
        }

    }

    public String updateTrans(double amount, String type) {
    //insert transaction
        //return transaction id  
        int t_id = Integer.parseInt(getMaxID("transaction", "transaction_id")) + 1;
        String str = "";
        try {

            Statement s = con.createStatement();
            String q = "insert into transaction(transaction_id,amount, t_date, type) values( " + t_id + ","
                    + amount + ", current_timestamp , '" + type + "')";
            int update = s.executeUpdate(q);
            if (update == 0) {
                System.out.println("Updating Transactions Failed: Possible incorrect ticker or id");
            } else {
                System.out.println("Updating Transactions Complete");
            }

            s.close();
        } catch (SQLException ex) {
            System.out.println("Error When Trying to Update Transactions. " + ex.getMessage() + " " + ex.toString());
        }
        return str + t_id;

    }

    public void updateTrades(String id, String ticker, String tID, String type, String action, double price, int quantity) {
    // there is a check in the trades table
        // type must be 'Stocks' or 'Mutual Funds'
        System.out.println(tID);
        String str = "";
        if (type.equals("stock")) {
            str = "Stocks";
        } else if (type.equals("mutual_funds")) {
            str = "Mutual Funds";
        }
        try {
            Statement s = con.createStatement();
            String q = "insert into trade( acc_number, ticker, transaction_id, type, action, purchase_price,"
                    + "quantity) values( "
                    + id + ",'" + ticker + "'," + tID + ",'" + type + "','" + action + "'," + price + "," + quantity + ")";
            int update = s.executeUpdate(q);
            if (update == 0) {
                System.out.println("Updating Trades Failed: Possible incorrect ticker or id");
            } else {
                System.out.println("Successful update of Trades");
            }
            s.close();
        } catch (SQLException ex) {
            System.out.println("Error When Trying to Update Trades " + ex.toString());
        }

    }

    public void updateInvestments(int quantity, String ticker, String id, String balance) {
        try {
            boolean found = search_invest_act(ticker, id);

            if (quantity == 0) {
                //if the amount of stocks/mf the client has is 0
                //then that person doesn't own it anymore
                //delete the investment tuple.
                deleteInvestment(ticker, id);
            } else if (found) {
                Statement s = con.createStatement();
                String q = "update investment_acc set shares= " + quantity + ", amount=" + balance
                        + " where ticker = '" + ticker + "' AND acc_number = " + id;
                int update = s.executeUpdate(q);
                if (update == 0) {
                    System.out.println("Updating Investments Failed: Possible incorrect ticker or id");
                }

                s.close();
            } else if (!found) {
                Statement s = con.createStatement();
                String q = "insert into investment_acc(shares, amount,acc_number,ticker) values("
                        + quantity + "," + balance + "," + id + ",'" + ticker + "')";
                int update = s.executeUpdate(q);
                if (update == 0) {
                    System.out.println("Updating Investments Failed: Possible incorrect ticker or id");
                }

                s.close();
            }

        } catch (SQLException ex) {
            System.out.println("Error When Trying to Update Investment. :  " + ex.toString());
        }
    }

    public void deleteInvestment(String ticker, String id) {
        try {
            Statement s = con.createStatement();
            String q = "delete from investment_acc where ticker = '" + ticker + "' AND acc_number = " + id;
            int update = s.executeUpdate(q);
            if (update == 0) {
                System.out.println("Deleting Investments Failed: Possible incorrect ticker or id");
            }
            s.close();

        } catch (SQLException ex) {
            System.out.println("Error When Trying to Find Investment.");
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
 