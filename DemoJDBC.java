/*
  02.10.2015
  chapter 4 JDBC

  compile using javac DemoJDBC.java
  to runn use java -cp .:mysql_connector DemoJDBC
  mysql_connector is teh name you want to give to your 
  simbolic link

  to set the path
  exort CLASSPATH=.:your_driver_jar_file:$CLASSPATH

*/

import java.sql.*;

class DemoJDBC
{
   /********************** main() *********************/
   public static void main(String args[]) throws SQLException
   {
      // factory method                                                        //your database name
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs211e",
                                                    "amoghtan", "blackRose");
      System.out.println("connected to the database");
      //if using java 7 and above use try and catch
      
      Statement stmt = conn.createStatement();

      //now lets create a table
      stmt.execute( "create table greetings(name varchar(20))" );
      // use execute if you are use non quary
      // table have only 1 filed called name with value size in this case 20

      // now lets put data 
      stmt.execute( "inset into greetings values('Hello Word!')" );
      // string in MSQL is inside ' '
      // you can inset more line just using same command
      
      // lets create querry to get data from database
      ResultSet res = stmt.executeQuery("select * from greetins");
      // myql will create a output and return to java
      // res set the pointer at 1 line above your fist line
      // now we need to set the pointer for right lines of data

      res.next();
      /* 
       for several recordes use 
       while(res.next())
       {
         ........
       }
       */
       System.out.println(res.getString(1));
       // getString is overload to get field by number or by name 
       // you can use getInteger(); || getChar();....

       // how to delete(drop) the table
       stmt.execute("drop table greetings");
       // if inside try and catch it will be automatic close
       // if not you need to close
       res.close();
       stmt.close();
       conn.close();
       /* you can use method isClosed()
          if( !res.isClosed() ) res.close();
       */

   }
}
//end
