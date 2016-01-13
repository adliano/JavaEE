/*
 * Author    : Adriano Alves (aalves3)
 * Date      : Mar.09.2015
 * Course    : Cs211E Spring of 2015
 * File Name : RMIServerImpl.java
 * File Need : RMIServerInterface.class
 * Objective : HW3 RMI Server implemented to work with
               Rmi client
 * How to run: 
               1) command: rmiregistry [portNumber] &
                  (take note of PID to kill it later)
               2) command: java RMIServerImpl
 * kill rmiregistry : kill -9 PID
 *
 */

import java.rmi.*;
import java.rmi.server.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class RMIServerImpl extends UnicastRemoteObject
                           implements RMIServerInterface
{
   private static final String FILENAME = "US_states";
   private static String arr_states[] = new String[50];
   private static String arr_capitals[] = new String[50];

   /**************** constructor ****************/
   public RMIServerImpl()throws RemoteException
   {
      super();
   }
   /**************** states() *******************/
   public String[] states(String regex)
   {
      ArrayList<String> arl = new ArrayList<>();

      for(String str : arr_states)
      {
         if(str.startsWith(regex.toUpperCase()))
         {
            arl.add(str);
         }
      }
      String arr[] = new String[arl.size()];
      return(arl.toArray(arr));
   }
   /**************** capitals() ******************/
   public String[] capitals(String regex)
   {
      ArrayList<String> arl = new ArrayList<>();

      for(String str : arr_capitals)
      {
         if(str.startsWith(regex.toUpperCase()))
         {
            arl.add(str);
         }
      }
      String arr[] = new String[arl.size()];
      return(arl.toArray(arr));
   }
   /**************** getCapital() *****************/
   public String getCapital(String state)
   {
      String CAPITAL = null;
      for(int i = 0; i< arr_states.length; i++)
      {
         if(arr_states[i].equalsIgnoreCase(state))
         {
             CAPITAL = arr_capitals[i];
         }
      }
      return(CAPITAL);
   }
   /***************** getState() ******************/
   public String getState(String capital)
   {
      String STATE = null;
      for(int i = 0; i< arr_capitals.length; i++)
      {
         if(arr_capitals[i].equalsIgnoreCase(capital))
         {
             STATE = arr_states[i];
         }
      }
      return(STATE);
   }
   /*************************** parseUSStates() ***********************/
   private static void parseUSStates(String states[], String capitals[])
   {
      try
      {
         Scanner sc = new Scanner(new File(FILENAME));
         String line;
         int i = 0;

         sc.nextLine(); sc.nextLine(); 

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
   /***************** main() **********************/
   public static void main(String args[])
   {
      parseUSStates(arr_states, arr_capitals);

      try
      {
         RMIServerImpl server = new RMIServerImpl();
         Naming.rebind("MyServer", server);
         System.out.println("MyServer is ready and running");

      } catch(RemoteException e){}
        catch(MalformedURLException e){}
   }   
}

//END
