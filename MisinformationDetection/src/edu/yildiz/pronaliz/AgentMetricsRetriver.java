package edu.yildiz.pronaliz;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class AgentMetricsRetriver {
   public static void main(String[] args) {
	   Agent agent =new Agent();

      try {
         File inputFile = new File("/home/jihad/Desktop/10/dax_13_10_.dax.xml");
         DocumentBuilderFactory dbFactory
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder;

         dBuilder = dbFactory.newDocumentBuilder();

         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();

         XPath xPath =  XPathFactory.newInstance().newXPath();

         String expression = "/document/agent[@id='pronaliz:__40GeorgeHartman']/label";
         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
         System.out.println("Number of found results " +nodeList.getLength());
         for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            System.out.println("\nCurrent Element :"
               + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               System.out.println(eElement.getTextContent());
               String[] contents = eElement.getTextContent().split(",");
//               agent.setId(id);
               agent.setCredibility(Double.parseDouble(contents[0]));
               agent.setAvailability(Double.parseDouble(contents[1]));
               agent.setVerifiability(Double.parseDouble(contents[2]));
               System.out.println(agent.toString());
            }
         }
      } catch (ParserConfigurationException e) {
         e.printStackTrace();
      } catch (SAXException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (XPathExpressionException e) {
         e.printStackTrace();
      }
   }
   public static Agent getAgentMetrics(String id, String path) {
	   Agent agent =new Agent();
	      try {
	         File inputFile = new File(path);
	         DocumentBuilderFactory dbFactory
	            = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder;

	         dBuilder = dbFactory.newDocumentBuilder();

	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();

	         XPath xPath =  XPathFactory.newInstance().newXPath();

	         String expression = "/document/agent[@id='"+ id+"']/label";
	         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
//	         System.out.println("Number of found results " +nodeList.getLength());
	         for (int i = 0; i < nodeList.getLength(); i++) {
	            Node nNode = nodeList.item(i);
//	            System.out.println("\nCurrent Element :"
//	               + nNode.getNodeName());
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
//	               System.out.println(eElement.getTextContent());
	               String[] contents = eElement.getTextContent().split(",");
	               agent.setId(id);
	               agent.setCredibility(Double.parseDouble(contents[0]));
	               agent.setAvailability(Double.parseDouble(contents[1]));
	               agent.setVerifiability(Double.parseDouble(contents[2]));
//	               System.out.println(agent.toString());

	            }
	         }
	      } catch (ParserConfigurationException e) {
	         e.printStackTrace();
	      } catch (SAXException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (XPathExpressionException e) {
	         e.printStackTrace();
	      }
	      return agent;
	   }
}