/*
********************************************************************* 
Author    : Adriano Alves
Date      :
File Name : DemoDomXML.java
Objective : Demo Program about xml
********************************************************************* 
*/
import java.io.File;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
 
class DemoDomXML
{
    //************************ main() **********************************//
   public static void main(String[] args) 
   {
      DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder icBuilder;

      try 
      {
         icBuilder = icFactory.newDocumentBuilder();
         Document doc = icBuilder.newDocument();
         Element mainRootElement = doc.createElementNS("http://crunchify.com/CrunchifyCreateXMLDOM", "Companies");
         doc.appendChild(mainRootElement);
 
         // append child elements to root element
         mainRootElement.appendChild(getCompany(doc, "1", "Paypal", "Payment", "1000"));
         mainRootElement.appendChild(getCompany(doc, "2", "eBay", "Shopping", "2000"));
         mainRootElement.appendChild(getCompany(doc, "3", "Google", "Search", "3000"));

         // safe in a file 
//         DocumentBuilder builder = factory.newDocumentBuilder();
//         Document doc = builder.parse(new File("testxmlfile.xml"));
         icBuilder.parse(new File("testxmlfile.xml"));

         // output DOM XML to console 
         Transformer transformer = TransformerFactory.newInstance().newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
         DOMSource source = new DOMSource(doc);
         StreamResult console = new StreamResult(System.out);
         transformer.transform(source, console);
 
         System.out.println("\nXML DOM Created Successfully..");
 
      }
      catch (Exception e) 
      {
            e.printStackTrace();
      }
   }
   //***************************** getCompany() **************************************//
   private static Node getCompany(Document doc, String id, String name, String age, String role) 
   {
      Element company = doc.createElement("Company");
      company.setAttribute("id", id);
      company.appendChild(getCompanyElements(doc, company, "Name", name));
      company.appendChild(getCompanyElements(doc, company, "Type", age));
      company.appendChild(getCompanyElements(doc, company, "Employees", role));
      return company;
   }
   // utility method to create text node
   //******************************* getCompanyElements() ******************************//
   private static Node getCompanyElements(Document doc, Element element, String name, String value) 
   {
      Element node = doc.createElement(name);
      node.appendChild(doc.createTextNode(value));
      return node;
   }
   //****************************** parsXML() ***********************************//
   public static void parseXML(String fileName)
   {
      try
      {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document doc = builder.parse(new File(fileName));
         builder.parse(new File(fileName));
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
}
