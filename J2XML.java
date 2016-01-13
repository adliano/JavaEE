/*
***********************************************************************
  Author : Adriano Alves
  Date   : Apr.25.2015
  File Name : J2XML.java
  Course : CS211E Spring of 2015
  Objective : HW6 , Java program to get all java attributes (java.) and
              convert it to a valid XML file

***********************************************************************
*/

import java.io.*;
import java.util.*;
import java.net.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;


class J2XML 
{
   //************************ main() *************************//
   public static void main(String args[])
   {
      if (args.length != 1) 
      {
         System.err.println("Usage: java J2XML filename");
         System.exit (1);
      }
      else systemInfoToXMLFile(args[0]);
   }
   //****************** systemInfoToXMLFile()******************//
   public static void systemInfoToXMLFile(String outputFile)
   {
      Properties prop = System.getProperties();
      Enumeration keys = prop.keys();
      Element rootElement, childElement, valueElement;

      try
      {
         DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
         Document doc = docBuilder.newDocument();

         // create a root element 
         rootElement = doc.createElement("javaproperties");
         // set attributes
         rootElement.setAttribute("computer", getCoputerName());
         rootElement.setAttribute("createBy", System.getProperty("user.name"));
         rootElement.setAttribute("date", getTodaysDate());
         doc.appendChild(rootElement);

         while(keys.hasMoreElements())
         {
            String strKey = (String)keys.nextElement();
            String strValue = (String)prop.getProperty(strKey);

            if(strKey.startsWith("java."))
            {
               String strNode = strKey.substring(strKey.lastIndexOf(".")+1);

               childElement = doc.createElement(strNode);
               childElement.appendChild(doc.createTextNode(strValue));
               rootElement.appendChild(childElement);
            }
         }
         // output DOM XML to file 
         Transformer transformer = TransformerFactory.newInstance().newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
         DOMSource source = new DOMSource(doc);
         //StreamResult console = new StreamResult(System.out);
         StreamResult result = new StreamResult(new File(outputFile));
         transformer.transform(source, result);
      }
      catch(Exception e){ e.printStackTrace(); }  
   }
   //**************************** getComputerName() *************may/08/2015**//
   public static String getCoputerName()
   {
      String pcName = null;

      try
      {
         InetAddress localMachine = java.net.InetAddress.getLocalHost();
         pcName = localMachine.getHostName().toString();
      }
      catch(UnknownHostException e)
      {
         e.printStackTrace();
      }
      return(pcName);
   }
   //***************************** getTodaysDate() ************may/09/2015**//
   public static String getTodaysDate()
   {
      Calendar c = Calendar.getInstance();
      String str[] = c.getTime().toString().split(" ");

      return(str[1]+"/"+str[2]+"/"+str[str.length-1]);
   }
}
//******* END ******
