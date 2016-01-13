/*

 Author         : Adriano Alves (aalves3)
 Date           : 02.04.2015
 Course         : CS211E Spring of 2015
 File Name      : DieHard.java
 Files needed   : 
 Obijective     : HW1 , Make a server multi client to load a file 
                  US_States , thisfile have name key value state 
                  and capital, the server have to return 
                  the answer to client program

*/

import java.net.*;
import java.io.*;
import java.util.*;

class DieHard
{
   String stateName, capitalName;
   String str[];
   String st[] = new String[50];
   String cpt[] = new String[50];

   /****************** main() **********************/
   public static void main(String args[]) throws IOException
   {
      try(
          ServerSocket svr = new ServerSocket(10001);
          Socket clnt = svr.accept();
          PrintWriter out = )
      {
          
      }
     //************* testing USStates2Arrays function
     USStates2Arrays(st, cpt);
     for(int i=0; i< 50; i++)
     {
        System.out.println(cpt[i]);
     }
     //****************************** end of test
   }
   /****************** USStates2Arrays() ******************/
   public static void USStates2Arrays(String states[], String capitals[])
   {
      try
      {
         Scanner sc = new Scanner(new File("US_states"));
         String line;
         int i=0;

         sc.nextLine(); sc.nextLine();
         // US_states file have 2 lines of heather file 
         while(sc.hasNext())
         {
            line = sc.nextLine();
            String str[]= line.split("\\s\\s+");
            if(str.length >=2)
            {
               if(str.length == 2)
               {
                  states[i] = str[0];
                  capitals[i++] = str[1];  
               }
               else
               {
                  states[i] = str[0]+" "+ str[1];
                  capitals[i++] = str[2];
               }
            }
         }
      }catch(FileNotFoundException e){}
   }
} 
