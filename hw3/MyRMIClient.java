/*
 Author : Adriano Alves (aalves3)
 Date : Mar.12.2015
 Course : CS211E Spring of 2015
 File Name : MyRMIClient.java
 Files Needed : RMIServerInterface.class
                RMIServerImpl.class
 Objective : HW3 Client Program belong to a server
             RMIServerImpl.class
*/

import java.util.*;
import java.rmi.*;
import java.rmi.Naming;
import java.io.*;

public class MyRMIClient 
{
   //************************ die() ***************************
   public static void die(String msg)
   {
      System.err.println(msg);
      System.exit(1);
   }
   //**************************** println() ***********************
   public static void println(String str)
   {
      System.out.println(str);
   }
   //**************************** print() ***********************
   public static void print(String str)
   {
      System.out.print(str);
   }
   /************************ printArray() ************************/
   public static void printArrayAnswer(String array[])
   {
      if(array.length==0) 
      {
         println("NO RESULTS");
         println("----------------------------------");
      }
      else
      {
         println("Answer: ");
         for(String s : array) println("- "+s);
         println("----------------------------------");
         println("");
      }
   }
   /**************************** main() **************************/
   public static void main(String args[])
   {
      if (args.length == 1)
      {
         try
         {
            String url = "rmi://"+args[0]+"/MyServer"; 
            /** create a object of the interface **/
            RMIServerInterface r = (RMIServerInterface) Naming.lookup(url);
            Scanner question = new Scanner(System.in);

            for(;;)
            {
               println("1- Look for a Capital");
               println("2- Look for a State");
               println("3- Look for states start with letter");
               println("4- Look for capitals start with letter");
               println("5- Exit");
               print("Option: ");

               String option = question.nextLine();
               String input = null;
               println("");

               switch(option)
               {
                  case "1":
                         print("Capital of :");
                         input = question.nextLine();
                         println("Answer :"+r.getCapital(input));
                         println("----------------------------------------");
                         println("");
                  break;
 
                  case "2":
                         print("State of Capital:");
                         input = question.nextLine();
                         println("Answer :"+r.getState(input));
                         println("----------------------------------------");
                         println("");
                  break;

                  case "3":
                         print("States start with: ");
                         input = question.nextLine();
                         printArrayAnswer(r.states(input));
                  break;

                  case "4":
                         print("Capitals start with: ");
                         input = question.nextLine();
                         printArrayAnswer(r.capitals(input));
                  break;

                  case "5":
                         println(" * * * * * * Bye Bye * * * * * *");
                         System.exit(0);
                  default:
                         println("************ Invalid Option ************");
                         println("----------------------------------------");
                         println("");
                  break;
               }
                   
            }
         }  catch(Exception e)
            {
               println("error....");
               e.printStackTrace();
            }
      }
      else die("Usage:java MyRMIClient [hostName]");
   }
}      
//END       
