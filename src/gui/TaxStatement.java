/*
Wellesley Arreza
wra216
CSE 241 Project
 */

package gui;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wellesley
 */
public class TaxStatement {
 private Connection con = null;
 //String[] newarr = arr.toArray(new String[arr.size()]);
 List<String> arr = new ArrayList<String>();
 private List<String> acc_number=new ArrayList<String>();
 private List<String> transaction_id=new ArrayList<String>();
 private List<String> type=new ArrayList<String>();
 private List<String> ticker=new ArrayList<String>();
 private List<String> action=new ArrayList<String>();
 private List<String> purchase_price=new ArrayList<String>();
  private List<String> quantity=new ArrayList<String>();
 private List<String> quantity_left=new ArrayList<String>();
 private List<String> t_date=new ArrayList<String>();
 private List<String> amount=new ArrayList<String>();
 private List<String> customer_id=new ArrayList<String>();
 private List<String> purchase_date=new ArrayList<String>();
 
 private List<String> distributions=new ArrayList<String>();
 private List<String> fromID=new ArrayList<String>();
  private List<String> toID=new ArrayList<String>();
 private List<String> asset=new ArrayList<String>();
 private List<String> date=new ArrayList<String>();
 private List<String> T_ID=new ArrayList<String>();
 
    public TaxStatement() {
        
    }
    
    public static void main(String[] args){
        TaxStatement ts= new TaxStatement();
        ts.run("1","2014");
    }
    
    public void clear(){
        acc_number.clear();
        transaction_id.clear();
        type.clear();
        ticker.clear();
        action.clear();
        purchase_price.clear();
        quantity.clear();
        quantity_left.clear();
        t_date.clear();
        amount.clear();
        customer_id.clear();

        distributions.clear();
        toID.clear();
        fromID.clear();
        asset.clear();
        date.clear();
        T_ID.clear();
    }
    
    public String[] findDate(String id) {

//        String q = "select year_borrowed, month_borrowed, day_borrowed, interest_paid from loans where loan_id = " + id;
        String q = "select to_char(date_borrowed, 'MM/DD/YYYY HH:MI:SSAM') as date_borrowed, interest_paid from loans where loan_id = " + id;
        //to_char(date_borrowed, 'MM/DD/YYYY HH:MI:SSAM') as date_borrowed
//        int[] arr = new int[4];
        String[] arr = new String[2];
        try {
            Statement s = con.createStatement();
            ResultSet result;
            result = s.executeQuery(q);
            ResultSetMetaData rsmd;
            if (!result.next()) {
                System.out.println("No Loan found");
            } else {
                rsmd = result.getMetaData();
                int j = 0;
                do {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {

//                        arr[i - 1] = Integer.parseInt(result.getString(i));
                          arr[i-1]= result.getString(i);
                    }
                } while (result.next());
            }
            s.close();
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
    
    public int getYear(String recent,String original){
        System.out.println("Original year "+original);
        System.out.println("Recent Year "+recent);
        try {
            // if month, day, hour , minute, seconds, 
            // is >= to original
            // do Year2 - Year 1 >0
            // update interest owed.
            
            //  28-MAR-14 10.50.12.739119000 PM
            //1992/10/04:10/32/32AM
            //String t="1992/04/17:07/19/59PM";
            //04/17/1994 10:59:10PM
            SimpleDateFormat old = new SimpleDateFormat("MM/dd/yyyy hh:mm:ssa");
            //1992/10/04:10/32/32AM
            SimpleDateFormat current = new SimpleDateFormat("yyyy/MM/dd:hh/mm/ssa");
           //2014-04-17 19:59:10.4649
            SimpleDateFormat year = new SimpleDateFormat("yyyy");
            SimpleDateFormat month = new SimpleDateFormat("MM");
            SimpleDateFormat day = new SimpleDateFormat("dd");
            SimpleDateFormat hour = new SimpleDateFormat("hh");
            SimpleDateFormat minutes = new SimpleDateFormat("mm");
            SimpleDateFormat seconds = new SimpleDateFormat("ss");
            SimpleDateFormat period = new SimpleDateFormat("a");
//            System.out.println("Before parsing"+t);
            
            Date date = old.parse(original);
            String ofYear = year.format(date);
            String ofMonth = month.format(date);
            String ofDay = day.format(date);
            String ofHour = hour.format(date);
            String ofMinutes = minutes.format(date);
            String ofSeconds = seconds.format(date);
            String ofPeriod = period.format(date);
            
            
            System.out.println("Converted Date :" + ofYear);
            System.out.println("Converted Date :" + ofMonth);
            System.out.println("Converted Date :" + ofDay);
            System.out.println("Converted Date :" + ofHour);
            System.out.println("Converted Date :" + ofMinutes);
            System.out.println("Converted Date :" + ofSeconds);
            System.out.println("Converted Date :" + ofPeriod);
            System.out.println("Converted Double :" +Integer.parseInt(ofMonth));
             
            
            Calendar cal= Calendar.getInstance();
           
            
         
            
//            String fYear = year.format(cal.getTime());
//            String fMonth = month.format(cal.getTime());
//            String fDay = day.format(cal.getTime());
//            String fHour = hour.format(cal.getTime());
//            String fMinutes = minutes.format(cal.getTime());
//            String fSeconds = seconds.format(cal.getTime());
//            String fPeriod = period.format(cal.getTime());
//            DateFormat dateFormat= new SimpleDateFormat();
//            System.out.println(dateFormat.format(cal.getTime()));
            
            Date date2 = old.parse(recent);
            String fYear = year.format(date2);
            String fMonth = month.format(date2);
            String fDay = day.format(date2);
            String fHour = hour.format(date2);
            String fMinutes = minutes.format(date2);
            String fSeconds = seconds.format(date2);
            String fPeriod = period.format(date2);
            
            
            System.out.println("Converted old Date :" + fYear);
            System.out.println("Converted Date :" + fMonth);
            System.out.println("Converted Date :" + fDay);
            System.out.println("Converted Date :" + fHour);
            System.out.println("Converted Date :" + fMinutes);
            System.out.println("Converted Date :" + fSeconds);
            System.out.println("Converted Date :" + fPeriod);
            System.out.println("Converted Double :" +Integer.parseInt(fMonth));
           
            
            
            boolean yearcheck2= Integer.parseInt(fYear)==Integer.parseInt(ofYear);
            boolean monthcheck= Integer.parseInt(fMonth)<Integer.parseInt(ofMonth);
            boolean monthcheck2= Integer.parseInt(fMonth)==Integer.parseInt(ofMonth);
            boolean daycheck= Integer.parseInt(fDay)<Integer.parseInt(ofDay);
            boolean daycheck2= Integer.parseInt(fDay)==Integer.parseInt(ofDay);
            boolean hourcheck= Integer.parseInt(fHour)<Integer.parseInt(ofHour);
            boolean hourcheck2= Integer.parseInt(fHour)==Integer.parseInt(ofHour);
            boolean minutescheck= Integer.parseInt(fMinutes)<Integer.parseInt(ofMinutes);
            boolean minutescheck2= Integer.parseInt(fMinutes)==Integer.parseInt(ofMinutes);
            boolean secondscheck= Integer.parseInt(fSeconds)<Integer.parseInt(ofSeconds);
            boolean secondscheck2= Integer.parseInt(fSeconds)==Integer.parseInt(ofSeconds);
            boolean periodcheck=fPeriod.equals("AM") && ofPeriod.equals("PM");
            boolean periodcheck2=fPeriod.equals(ofPeriod);
            int num= Integer.parseInt(fYear) - Integer.parseInt(ofYear);
            
            // Current year - original year = # of years
            
            if(num>0){
                
            if(yearcheck2 && monthcheck){ // if years are equal and this month is before original
                num--;
            }
            else if(monthcheck2 && daycheck){ // year,month is equal and day is before original.
                num--;
            }
            else if(monthcheck2 && daycheck2 && periodcheck ){ // if year,month,day is equal and its PM
                num--;
            }
            else if(monthcheck2 && daycheck2 && periodcheck2 && hourcheck){ 
            // if year,month,day,period are equal and hour is before original
                num--;
            }
            else if(monthcheck2 && daycheck2 && periodcheck2 && hourcheck2 && minutescheck){
                            // if year,month,day,period,hour are equal and minutes is before original
                num--;
            }
            else if(monthcheck2 && daycheck2  && periodcheck2 && hourcheck2 && minutescheck2 && secondscheck){
                            // if year,month,day,period,hour, minutes are equal and seconds is before original
                num--;
            }
            /*
            We have many cases.
            Case 1: month> month
            Case 2: month>= month day>day
            Case 3: month>= month day>=day hour
            
            
            */
          
       
                    }
            System.out.println("Number of years for interest"+num);  
            return num;
        } catch (ParseException ex) {
            System.out.println("Error when calculating years for interest " + ex.toString());
        }
        
     
        return 0;
    }
    
    public String convertDate(String t){
        String conversion="";
     try {
         SimpleDateFormat old = new SimpleDateFormat("MM/dd/yyyy hh:mm:ssa");
         //1992/10/04:10/32/32AM
         SimpleDateFormat current = new SimpleDateFormat("yyyy/MM/dd:hh/mm/ssa");
         //2014-04-17 19:59:10.4649
         SimpleDateFormat convert = new SimpleDateFormat("MM/dd/yyyy");
         
//            System.out.println("Before parsing"+t);
         
         Date date = old.parse(t);
         conversion = convert.format(date);
     } catch (ParseException ex) {
         Logger.getLogger(TaxStatement.class.getName()).log(Level.SEVERE, null, ex);
     }
     return conversion;
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
    
    
    public void run(String c_id, String year){
        System.out.println("TaxStatement BEGIN:");
        String report="Customer ID : "+c_id+System.getProperty("line.separator")
                +"Year : "+year+System.getProperty("line.separator");
        String long_term="";
        String short_term="";
        String transfers="";
        String total_transfer="";
        /*
        First Determine the amount of years
        if years <=1 then add to the short_term string
        
        */
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", "wra216",
                    "P810339776");
            
            boolean found=getYearlyData(c_id,year);
            boolean transfersfound=getTransfers(c_id,year);
            double shortNI=0;
            double longNI=0;
            
             
            
            
            
            if(found){
                
                int length=transaction_id.size();
                
                String newline = System.getProperty("line.separator");
                //report+="ticker \t quantity \t original price \t date sold \t revenue \t original cost \t gain/loss ";
                short_term+="Short Term Assets";
                short_term+=newline;
                short_term+=newline;
                short_term+=(String.format("%-20s", "ticker")+" \t "); 
                short_term+=(String.format("%-15s", "quantity")+ " \t ");
                 short_term+=(String.format("%-20s", "original price")+ " \t ");
                 short_term+=(String.format("%-10s", "date bought")+ " \t ");
                    short_term+=(String.format("%-10s", "date sold")+ " \t ");
                    short_term+=(String.format("%-20s", "revenue")+ " \t ");
                    short_term+=(String.format("%-20s", "original cost")+ " \t ");
                    short_term+=(String.format("%-22s", "gain/loss")+" \t ");
               short_term+=newline;
                short_term+=newline;
                
                long_term+="Long Term Assets";
                long_term+=newline;
                long_term+=newline;
                long_term+=(String.format("%-20s", "ticker")+" \t "); 
                long_term+=(String.format("%-15s", "quantity")+ " \t ");
                 long_term+=(String.format("%-20s", "original price")+ " \t ");
                 long_term+=(String.format("%-10s", "date bought")+ " \t ");
                    long_term+=(String.format("%-10s", "date sold")+ " \t ");
                    long_term+=(String.format("%-20s", "revenue")+ " \t ");
                    long_term+=(String.format("%-20s", "original cost")+ " \t ");
                    long_term+=(String.format("%-22s", "gain/loss")+" \t ");
               long_term+=newline;
                long_term+=newline;
                
            
                
                for(int i=0; i<length; i++){
                    
                    /*
                     Short Term Gains
                     Query sells within the year
    
                     Long Term Gains
                     Query all Sells
    
                     Ticker
                     Quantity
                     Date Sold
                     Proceeds
                     Original cost
                     Gain/Loss
                    
                     */
                    // format date later
                    int quantity_sold=Integer.parseInt(quantity.get(i));
                    double original_cost= Double.parseDouble(purchase_price.get(i)) * quantity_sold;
//                    short_term+=(String.format("%-20s", ticker.get(i))+" \t "); 
//                    short_term+=(String.format("%-15s", quantity.get(i))+ " \t ");
//                    short_term+=(String.format("%-20s", purchase_price.get(i))+ " \t ");
//                    short_term+=(String.format("%-10s", t_date.get(i))+ " \t ");
//                    short_term+=(String.format("%-20s", amount.get(i))+ " \t ");
//                    short_term+=(String.format("%-20s", ""+original_cost)+ " \t ");
//                    
//                    long_term+=(String.format("%-20s", ticker.get(i))+" \t "); 
//                    long_term+=(String.format("%-15s", quantity.get(i))+ " \t ");
//                    long_term+=(String.format("%-20s", purchase_price.get(i))+ " \t ");
//                    long_term+=(String.format("%-10s", t_date.get(i))+ " \t ");
//                    long_term+=(String.format("%-20s", amount.get(i))+ " \t ");
//                    long_term+=(String.format("%-20s", ""+original_cost)+ " \t ");
                    
                    double revenue=Double.parseDouble(amount.get(i));
                    double gain_loss= revenue - original_cost;
                    
                    BigDecimal bd = new BigDecimal(Double.toString(gain_loss));
                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    gain_loss=bd.doubleValue();
                  
                    System.out.println(purchase_date.get(i));
                    
                    
                    int netYear= getYear(t_date.get(i),purchase_date.get(i));
                    
                    System.out.println("Years that we held the asset :" +netYear);
                    String cDate=convertDate(t_date.get(i));
                    String pDate=convertDate(purchase_date.get(i));
                    //SHORT TERM ASSETS
                    if(netYear <=1){
                    shortNI+=gain_loss;
                    short_term+=(String.format("%-20s", ticker.get(i))+" \t "); 
                    short_term+=(String.format("%-15s", quantity.get(i))+ " \t ");
                    short_term+=(String.format("%-20s", purchase_price.get(i))+ " \t ");
                    short_term+=(String.format("%-10s", pDate)+ " \t ");
                    short_term+=(String.format("%-10s", cDate)+ " \t ");
                    short_term+=(String.format("%-20s", amount.get(i))+ " \t ");
                    short_term+=(String.format("%-20s", ""+original_cost)+ " \t ");
                    if(gain_loss<0){
                        
                        short_term+=(String.format("%-20.2f", gain_loss)+" \t ");
                    }
                    else if(gain_loss>=0){
                        short_term+=(String.format("%-20.2f", gain_loss)+" \t ");
                    }
                    short_term+=newline;
                   
                    
                    }
                    
                    // LONG TERM ASSETS
                    else if(netYear > 1){
                        longNI+=gain_loss;
                        long_term+=(String.format("%-20s", ticker.get(i))+" \t "); 
                    long_term+=(String.format("%-15s", quantity.get(i))+ " \t ");
                    long_term+=(String.format("%-20s", purchase_price.get(i))+ " \t ");
                    long_term+=(String.format("%-10s", pDate)+ " \t ");
                    long_term+=(String.format("%-10s", cDate)+ " \t ");
                    long_term+=(String.format("%-20s", amount.get(i))+ " \t ");
                    long_term+=(String.format("%-20s", ""+original_cost)+ " \t ");
                    long_term+=(String.format("%-20.2f", gain_loss)+" \t ");
                    long_term+=newline;
                   
                    }
                    
                  
                   
                   
                }
                
                
                short_term+=newline;
                   short_term+=newline;
                   short_term+=newline;
                   short_term+="Total Gain/Loss : \t";
                   short_term+=shortNI;
               
                   long_term+=newline;
                   long_term+=newline;
                   long_term+="Total Gain/Loss: \t";
                   long_term+=longNI;
                
            }
             double distributioncount=0;
            if(transfersfound){
               
                int tlength=T_ID.size();
                String newline = System.getProperty("line.separator");
                transfers += "Distributions/Transfers to Retirement Accounts";
                transfers += newline;
                transfers += newline;
                transfers += (String.format("%-15s", "From Account") + " \t ");
                transfers += (String.format("%-15s", "To Account") + " \t ");
                transfers += (String.format("%-20s", "Asset") + " \t ");
                transfers += (String.format("%-20s", "Transaction ID") + " \t ");
                transfers += (String.format("%-20s", "Date Transferred") + " \t ");
                transfers += (String.format("%-25s", "Equivalent Cash Amount") + " \t ");
                    
                transfers+=newline;
                transfers+=newline;
                System.out.println("tlength"+tlength);
                for(int i=0; i<tlength; i++){
                    transfers += (String.format("%-15s", fromID.get(i)) + " \t ");
                    transfers += (String.format("%-15s", toID.get(i)) + " \t ");
                    transfers += (String.format("%-20s", asset.get(i)) + " \t ");
                    transfers += (String.format("%-20s", T_ID.get(i)) + " \t ");
                    transfers += (String.format("%-20s", date.get(i)) + " \t ");
                    transfers += (String.format("%-25s", distributions.get(i)) + " \t ");
                    distributioncount+=Double.parseDouble(distributions.get(i));
                /*
                 private List<String> distributions=new ArrayList<String>();
                     private List<String> fromID=new ArrayList<String>();
                     private List<String> toID=new ArrayList<String>();
                     private List<String> asset=new ArrayList<String>();
                     private List<String> date=new ArrayList<String>();
                     private List<String> T_ID=new ArrayList<String>();
                     */
                    transfers+=newline;
                
                }
                transfers+=newline;
                transfers+=newline;
                
                
                
            }
            transfers+="Total Distribution :";
                transfers+=(" "+distributioncount);
            String newline = System.getProperty("line.separator");
            report += short_term + newline + newline + newline + long_term
                    + newline+ newline+ newline+transfers;
            //clear();
            System.out.println(report);
            write(report, year);
            
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaxStatement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TaxStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //year
    // customer id
    
    //show capital gains
    
    // identify each form of income for which there is distinct tax treatment
    // distinguish them between retirement and non retirement
    
    // short term and long term capital gains
    
    /*
    
   Select all sells in trade
    get transaction date (Date Sold)
    
    
    
    
    
    Short Term Gains
    Query sells within the year
    
    Long Term Gains
    Query all Sells
    
    Ticker
    Quantity
    Date Sold
    Proceeds
    Original cost
    Gain/Loss
    
    Distributions
    Act ID
    Asset
    Quantity/Amount
    Date
    
    */
    
    
    public boolean getTransfers(String c_id, String year){
     boolean found=false;
     int count=0;
     try {   //TO_CHAR(t_date, 'MM/DD/YYYY') as t_date
            //MM/dd/yyyy hh:mm:ssa
                Statement s = con.createStatement();
               String q = "select from_acc_number, to_acc_number, transfer.type, transfer.transaction_id,TO_CHAR(t_date, 'MM/DD/YYYY') as t_date, amount as revenue "
                 + " from transfer,transaction, account,owns "
                 + " where account.acc_type='retirement' "
                 + " AND transfer.to_acc_number=account.ACC_NUMBER AND transaction.transaction_id=transfer.TRANSACTION_ID "
                 + " AND owns.customer_id = "+ c_id
                 + " AND account.acc_number= owns.acc_number "
                 + " AND TO_CHAR(t_date, 'YYYY')= "+year;
                ResultSet result = s.executeQuery(q);

                ResultSetMetaData rsmd;
                if (!result.next()) {
                    System.out.println("Empty Stock Data");
                    found=false;
                    count=0;
                } else {
                    rsmd = result.getMetaData();   
                    
                    do {  
                        fromID.add(result.getString(1));
                        toID.add(result.getString(2));                     
                        asset.add(result.getString(3));
                        T_ID.add(result.getString(4));
                        date.add(result.getString(5));
                        distributions.add(result.getString(6));
                    } while (result.next());
                    found=true;
                }
               // fw.close();
                s.close();
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//            Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
        return found;
    }
    
    
    
    
    
    public boolean getYearlyData(String c_id, String year){
        boolean found=false;
        int count=0;
        try {   //TO_CHAR(t_date, 'MM/DD/YYYY') as t_date
            //MM/dd/yyyy hh:mm:ssa
                Statement s = con.createStatement();
                String q = "select acc_number, transaction_id, type, ticker, action,"
                        + " purchase_price, quantity, quantity_left,"
                        + "to_char(t_date,'MM/DD/YYYY HH:MI:SSAM'), amount, customer_id,"
                        + "to_char(purchase_date,'MM/DD/YYYY HH:MI:SSAM') "
                        + " from trade natural join transaction natural join owns natural join account"
                        + " where action='sell' AND acc_type='investment' AND customer_id= "+c_id+" AND TO_CHAR(t_date, 'YYYY')= "
                        + year +" order by t_date";
                ResultSet result = s.executeQuery(q);

                ResultSetMetaData rsmd;
                if (!result.next()) {
                    System.out.println("Empty Stock Data");
                    found=false;
                    count=0;
                } else {
                    rsmd = result.getMetaData();   
                    
                    do {  
                             acc_number.add(result.getString(1));
                            transaction_id.add(result.getString(2));
                             type.add(result.getString(3));
                             ticker.add(result.getString(4));
                            action.add(result.getString(5));
                             purchase_price.add(result.getString(6));
                             quantity.add(result.getString(7));
                              quantity_left.add(result.getString(8));
                              t_date.add(result.getString(9));
                              amount.add(result.getString(10));
                              customer_id.add(result.getString(11));
                              purchase_date.add(result.getString(12));
                    } while (result.next());
                    found=true;
                }
               // fw.close();
                s.close();
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//            Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
        return found;
    }
    public void write(String report, String year) {
    //select ticker,action,quantity from trade natural join owns where customer_id=1;
        FileWriter fw = null;
        try {
            String title="taxstatement"+year+".txt";
            fw = new FileWriter(title);
           
            
            String newline = System.getProperty("line.separator");
            System.out.println(report);
            fw.write(report);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Stockticker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
