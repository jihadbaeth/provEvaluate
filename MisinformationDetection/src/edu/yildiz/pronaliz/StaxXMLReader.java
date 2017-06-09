package edu.yildiz.pronaliz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxXMLReader {

    public static void main(String[] args) {
        String fileName = "/home/jihad/Desktop/100/dax_1_100_.dax.xml";
        List<Tuple> empList = parseXML(fileName);
        for(Tuple emp : empList){
            System.out.println(emp.toString());
        }
    }

    private static List<Tuple> parseXML(String fileName) {
        List<Tuple> empList = new ArrayList<>();
        Tuple emp = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
               if (xmlEvent.isStartElement()){
                   StartElement startElement = xmlEvent.asStartElement();
                   if(startElement.getName().getLocalPart().equals("wasAssociatedWith")){
                       emp = new Tuple();
                       //Get the 'id' attribute from Employee element
                       Attribute idAttr = startElement.getAttributeByName(new QName("id"));
                       if(idAttr != null){
//                       emp.setId(Integer.parseInt(idAttr.getValue()));
                    	   System.out.println(idAttr.getValue());
                       }
                       xmlEvent = xmlEventReader.nextEvent();
                       Characters activityElement = xmlEvent.asCharacters();
                       xmlEvent = xmlEventReader.nextEvent();
                       StartElement activityElement2 = xmlEvent.asStartElement();
                       System.out.println(activityElement2.getAttributes());

                       if(activityElement2.getName().getLocalPart().equals("activity")){
                           xmlEvent = xmlEventReader.nextEvent();
                           Attribute idAttr2 = activityElement2.getAttributeByName(new QName("id"));
                           if(idAttr != null){
//                             emp.setId(Integer.parseInt(idAttr.getValue()));
                          	   System.out.println(idAttr2.getValue());
                             }
                           emp.setActivityID(xmlEvent.asCharacters().getData());
                       }else if(startElement.getName().getLocalPart().equals("agent")){
                           xmlEvent = xmlEventReader.nextEvent();
                           emp.setAgentID(xmlEvent.asCharacters().getData());
                       }
                   }
                   //set the other varibles from xml elements

               }
               //if Employee end element is reached, add employee object to list
               if(xmlEvent.isEndElement()){
                   EndElement endElement = xmlEvent.asEndElement();
                   if(endElement.getName().getLocalPart().equals("wasAssociatedWith")){
                       empList.add(emp);
                   }
               }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return empList;
    }

}