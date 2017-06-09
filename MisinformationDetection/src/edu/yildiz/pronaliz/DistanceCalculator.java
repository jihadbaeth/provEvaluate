package edu.yildiz.pronaliz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections15.list.SetUniqueList;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class DistanceCalculator {

	   public static void main(String[] args) throws FileNotFoundException {

			File folder = new File("/home/jihad/Desktop/dataset/100/provn/");
			File[] listOfFiles = folder.listFiles();
	        PrintWriter pw = new PrintWriter(new File("/home/jihad/Desktop/dataset/100/provn/distances.csv"));
	        StringBuilder sb = new StringBuilder();
			sb.append("FileName");
			sb.append(',');
			sb.append("Distance");
			sb.append(',');
			sb.append("Total Number of Tweeps");
			sb.append(',');
			sb.append("Number of Tweeps with positive Feedback");
			sb.append(',');
			sb.append("Number of Tweeps with Ngeative Feedback");
			sb.append('\n');
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			        System.out.println(file.getAbsolutePath());
			        String[] results = distanceCalculator(file.getAbsolutePath());


					sb.append(file.getName());
					sb.append(',');
					sb.append(results[0]);
					sb.append(',');
					sb.append(results[1]);
					sb.append(',');
					sb.append(results[2]);
					sb.append(',');
					sb.append(results[3]);
					sb.append('\n');


					System.out.println("done!");
			    }
			}
			pw.write(sb.toString());
			pw.close();
	   }
	   public static String[] distanceCalculator(String path) {
	        double distance =0;
	        String[] resutls = new String[4];
		      try {
		         File inputFile = new File(path);
		         SAXReader reader = new SAXReader();
		         Document document = reader.read( inputFile );
		         List<Tuple> tupleList = new ArrayList<>();
		         List<Node> nodes = document.selectNodes("/document/prov:wasAssociatedWith");
		         System.out.println("----------------------------");
//		         System.out.println(nodes.size());
		     	 HashMap<String, Agent> ahm = new HashMap<String, Agent>();
		     	 ArrayList<String> positive = new ArrayList<String>();
		     	 ArrayList<String> negative = new ArrayList<String>();
		    	 int i =1;
		         for (Node node : nodes) {

//		            System.out.println("\nCurrent Element :"
//		               + node.getName());
		            String activityID =node.valueOf("@prov:id");
//		            System.out.println("Student roll no : "
//		               + activityID );


		            if(activityID.length()>45)
		            {
		            	if(i==3)
		            		{i=1;
		            		}else{i++;}
		            	String[] contents = activityID.split("(?=activity)");
//		                System.out.println(contents[i]);
		            	Tuple t = new Tuple();
		            	t.setActivityID(contents[i]);
		            	t.setAgentID(node.selectSingleNode("prov:agent").valueOf("@prov:ref"));
		            	tupleList.add(t);
		                String [] activitySplits = t.getActivityID().split("__");
//		            	System.out.println("Agent : " + node.selectSingleNode("prov:agent").valueOf("@prov:ref"));
		            	String activityType = activitySplits[4].substring(0, 1);
		            	double cred =AgentMetricsRetriver.getAgentMetrics(t.getAgentID(), path).getCredibility();
		            	double avail = AgentMetricsRetriver.getAgentMetrics(t.getAgentID(), path).getAvailability();
		            	double veri = AgentMetricsRetriver.getAgentMetrics(t.getAgentID(), path).getVerifiability();

		            	Agent tweep = new Agent();
		            	tweep.setId(t.getAgentID());
		            	tweep.setCredibility(cred);
		            	tweep.setAvailability(avail);
		            	tweep.setVerifiability(veri);
		            	ahm.put(t.getAgentID(), tweep);
		            	if(activityType.equals("1"))
		            	{
		        			if(!positive.contains(tweep.getId()))
		            		positive.add(tweep.getId());
		            		if(negative.contains(tweep.getId()))
		            		{
		            			negative.remove(tweep.getId());
		            		}
		            	}
		            	else if(activityType.equals("2")||activityType.equals("3"))
		            	{
		            		if(!positive.contains(tweep.getId()))
		            		{
		            			if(!negative.contains(tweep.getId()))
		            			negative.add(tweep.getId());
		            		}


		            	}

		            }
//		            System.out.println("First Name : " + node.selectSingleNode("prov:activity").valueOf("@prov:ref"));

		         }

		      	System.out.println("===============");
		      	System.out.println("List of Tweeps");
		      	resutls[1]=ahm.size()+"";
		     	for(String temp:ahm.keySet())
		     	{
		     		System.out.print(temp+"  ");
		     		System.out.println(ahm.get(temp).getCredibility());
		     	}
		      	System.out.println("===============");
		      	System.out.println("List of Tweeps with Positive Feedback");
		      	for(String temp:positive)
		      	{
		      		System.out.println(temp);
		      	}
		      	resutls[2]=positive.size()+"";
		      	System.out.println("===============");
		      	System.out.println("List of Tweeps with Negative Feedback");
		      	for(String temp:negative)
		      	{
		      		System.out.println(temp);
		      	}
		      	System.out.println("===============");
		     	System.out.println();
		     	System.out.println();
		     	System.out.println();
		     	System.out.println();
		      	System.out.println("Calculating Distance from Positivity");
		        double sumOfCred =0;
		        for(Agent temp:ahm.values())
		        {
		        	sumOfCred = sumOfCred+temp.getCredibility();
		        }
		        System.out.println("Summation of tweeps credibility: "+ sumOfCred);
		        for(String temp:negative)
		        {
		        	distance = distance + (ahm.get(temp).getCredibility()/sumOfCred);
		        }
		        resutls[3]=negative.size()+"";
		        System.out.println("Distance from positivity= "+(1-distance));
		     	System.out.println();
		      } catch (DocumentException e) {
		         e.printStackTrace();
		      }
		      resutls[0]=distance+"";

		      return resutls;


		   }

}