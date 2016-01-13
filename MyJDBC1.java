/*
 *
 *  Author       : Adriano Alves aalves3@mail.ccsf.edu 
 *  Date         : 02/11/15
 *  Course       : CS211E Spring of 2015 HW2
 *  Files need   : US_states, MyJDBC1.prop, MyJDBC1_Queries.prop
 *  Program Name : MyJDBC1.java
 *  Objective    : JDBC Database that will load a file with 
                 data and display info about this fie
                 contents of this file is name of US states 
                 and capitals.
 *
 */
 
import java.util.*;
import java.io.*;
import java.sql.*;

class MyJDBC1
{
   private static final String FILENAME = "US_states";
   private static final String PROPTFILE = "MyJDBC1.prop";
   private static final String PROPTQUERY = "MyJDBC1_Queries.prop";
   private static String URL, userName, passCode;
   private static String arr_states[] = new String[50];
   private static String arr_capitals[] = new String[50];

   /*************************** parseUSStates() ***********************/
   private static void parseUSStates(String states[], String capitals[])
   {
      try
      {
         Scanner sc = new Scanner(new File(FILENAME));
         String line;
         int i = 0;

         sc.nextLine(); sc.nextLine(); // skip over couple of headers

         while(sc.hasNext())
         {
            line = sc.nextLine();
            String temp[] = line.split("\\s\\s+");
            if(temp.length >= 2)
            {
               if(temp.length == 2)
               {
                  states[i]     = temp[0];
                  capitals[i++] = temp[1];
               }
               else
               {
                  states[i]     = temp[0] + " " + temp[1];
                  capitals[i++] = temp[2];
               }
            }
         }

      }catch(FileNotFoundException e){System.err.println(e);}
   }
   /*********************** loadProp() ******************************/
   public static void loadProp(String fileName)
   {
      Properties p = new Properties();

      try( FileReader f = new FileReader(PROPTFILE) )
      {
         p.load(f);
         URL      = p.getProperty("prop.URL");
         userName = p.getProperty("prop.USERID");
         passCode = p.getProperty("prop.PSWD");

      } catch( Exception e )
        {
            System.out.println("UNABLE to open file "+PROPTFILE);
            e.printStackTrace();
        }
   }
   /******************************** mkconn() *****************************/
   public static Connection mkconn()
   {
      loadProp(PROPTFILE);

      Connection conn = null;

      try
      {
         conn = DriverManager.getConnection(URL, userName, passCode);
         System.out.println("Connected to "+URL);

      } catch(SQLException e)
        {
          System.out.println(" CONNECTION FAILED!!!! ");
          e.printStackTrace();
        }

      return(conn);
   }
   /***************************** dataLoader() *****************************/
   public static void dataLoader(Connection c, String st[], String cpt[])
   {
      parseUSStates(arr_states, arr_capitals);

      Statement stmt = null;
      int tableSize = 0;

      try
      {
         stmt = c.createStatement();
         stmt.execute("create table if not exists usa("  +
                      " id INT not null auto_increment," +
                      " states varchar(32)not null,"     +
                      " capitals varchar(32) not null,"  +
                      " primary key(id))"
                     );

         ResultSet res = stmt.executeQuery("select count(*) from usa");
         while( res.next() )
         {
           tableSize = res.getInt(1);
         }

         if(tableSize==0)
         {
            for(int i=0; i<(st.length); i++)
            {
                stmt.execute("INSERT INTO usa (states,capitals) VALUES('"+
                              st[i]+"','"+cpt[i]+"')" );
            }
          }

         if( stmt != null ) stmt.close();

      } catch(SQLException e)
        {
          e.printStackTrace();
        }
   }
   /*********************** queryPrinter() **********************/
   public static void queryPrinter(Connection c, String query)
   {
      Statement stmt = null;

      try
      {
         stmt = c.createStatement();
         ResultSet res = stmt.executeQuery(query);

         while( res.next() )
         {
            System.out.println(res.getString(1));
         }

         if( stmt != null ) stmt.close();

      } catch(SQLException e)
        {
          System.out.println("Unable to read from database");
          e.printStackTrace();
        }
   }
   /******************** displayQueries() *******************/
   public static void displayQueries(Connection c)
   {
      String qs[] = new String[4]; 
                
      Properties p = new Properties();

      try( FileReader f = new FileReader(PROPTQUERY); )
      {
         p.load(f);
         qs[0] = p.getProperty("propt.QS1");
         qs[1] = p.getProperty("propt.QS2");
         qs[2] = p.getProperty("propt.QS3");
         qs[3] = p.getProperty("propt.QS4");

      } catch( Exception e )
        {
            System.out.println("UNABLE to open file "+ PROPTQUERY);
            e.printStackTrace();
        }

      for(String str: qs)
      {
         queryPrinter(c, str);
      }
   }
   /************************ tableDroper() ******************/
   public static void tableDroper(Connection c)
   {
      Statement stmt = null;

      try
      {
         stmt = c.createStatement();
         stmt.execute("drop table usa");

         if( stmt != null ) stmt.close();
      
         System.out.println("Table Droped Successful.");

      } catch(SQLException e)
        {
          System.out.println("Unable to drop table");
          e.printStackTrace();
        }
   }
   /********************** cmdOption ************************/
   public static void cmdOption(Connection conn, String option[])
   {
      int c;

      GetOpt g = new GetOpt(option, "hpd");
      
      while( (c=g.getopt())!=-1 )
      {
         switch(c)
         {
            case 'p': displayQueries(conn);
            break;

            case 'd': tableDroper(conn);
            break;

            case 'h': helpOpt();
            break;

            case '?': System.exit(0); 
            break; 
         }
      }
   }
   /********************* helpOpt() *************************/
   public static void helpOpt()
   {
      System.out.println("java -cp [connector] MyJDBC1 <options> ");
      System.out.println("option -p: print queries");
      System.out.println("option -d: drop table");
   }
   /************************* main **************************/
   public static void main(String args[])
   {
      Connection conn = mkconn();
      dataLoader(conn, arr_states, arr_capitals);
      cmdOption(conn, args);
   }
}

// End Fev/15/2015 
