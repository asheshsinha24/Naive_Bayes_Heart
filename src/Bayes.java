import java.io.*;
import java.util.*;

public class Bayes
{
    int att_num;
    String [] att_name;
    Vector [] domain;
    Vector [] domainReal;
    public static int class1;
    public static int class2;

    public static Hashtable<String, Hash_Tag> Bayes_Hash = new Hashtable<String, Hash_Tag>();

    public Bayes()
    {
	att_name=new String[20];
	domain = new Vector[20];
	domainReal = new Vector[20];
	for (int i=0; i<20;i++)
	    {
	     domain[i] = new Vector();
	     domainReal[i] = new Vector();
	     
	     
	    }
    }

public class Hash_Tag
{
    int count_f;
};

    public void createBayes(Bayes d_tree2, Node root, Node root1, int []real_val)
    {
	BayesLearn(root,real_val);

	for (int i=0; i<att_num-1; i++)
	    System.out.println(att_name[i] + " " + "class");
	System.out.println("");
	
	BayesTest(root1,real_val);
	
	
    }

    public void BayesLearn(Node root, int[] real_val)
    {
	
	class1=0;
	class2=0;

	for(int i=0; i<root.data.size(); i++)
	    {
		DataPoint point = (DataPoint)root.data.get(i);
		
		if(point.attributes[att_num-1]==0)
		    class1+=1;
		else
		    class2+=1;

		for (int j=0; j<point.attributes.length-1; j++)
		    {			
			String key = att_name[j] + (String)domain[j].elementAt(point.attributes[j]) + (String)domain[att_num-1].elementAt(point.attributes[att_num-1]);
			
			if(Bayes_Hash.containsKey(key))
			   {
			       
			       Hash_Tag h1 = Bayes_Hash.get(key);
			       
			       h1.count_f = h1.count_f + 1;
			       
			       Bayes_Hash.put(key,h1); 
			   }
			 else
			   {
			       
				   Hash_Tag h1 = new Hash_Tag();
       
				   h1.count_f =1;
			  
				   Bayes_Hash.put(key, h1);
				   
			   }
		    }
		
	    }

    }

	public void BayesTest(Node root1, int[] real_val)
	{
       
	    int analyze=0;

	    for(int i=0; i<root1.data.size(); i++)
	    {
	      
		double prob1=(1.0*class1+1.0)/(class1+class2+2);
		double prob2=(1.0*class2+1.0)/(class1+class2+2);


		DataPoint point = (DataPoint)root1.data.get(i);

		for (int j=0; j<point.attributes.length-1; j++)
		    {
			
			String key1 = att_name[j] + (String)domain[j].elementAt(point.attributes[j]) + (String)domain[att_num-1].elementAt(0);
		        String key2 = att_name[j] + (String)domain[j].elementAt(point.attributes[j]) + (String)domain[att_num-1].elementAt(1);;
			
			int total_count1=0;
			int total_count2=0;
			if(Bayes_Hash.containsKey(key1))
			   {
			       
			       Hash_Tag h1 = new Hash_Tag();
				   h1 = Bayes_Hash.get(key1);

				   total_count1 = h1.count_f;
				  
			    }

			   if(Bayes_Hash.containsKey(key2))
			   {
			       Hash_Tag h1 = new Hash_Tag();
			       h1= Bayes_Hash.get(key2);
			       
				   total_count2 = h1.count_f;
				 
				
			   }
			   
			   int sum1=0;
			   int sum2=0;
			  
			   for (int k=0; k<domain[j].size();k++)
			       {
				
				   String key3 = att_name[j] + (String)domain[j].elementAt(k) + (String)domain[att_num-1].elementAt(0);
				   String key4 = att_name[j] + (String)domain[j].elementAt(k) + (String)domain[att_num-1].elementAt(1);;
			
				   if(Bayes_Hash.containsKey(key3))
				       {
			       
					   Hash_Tag h1 = new Hash_Tag();
					   h1 = Bayes_Hash.get(key3);
					   sum1=sum1+h1.count_f;

			       
				       }

				   if(Bayes_Hash.containsKey(key4))
				       {
					   Hash_Tag h1 = new Hash_Tag();
					   h1= Bayes_Hash.get(key4);
					   sum2=sum2+h1.count_f;
				 
				       }
			       }
			   
			   prob1 = prob1*(1.0*total_count1+1.0)/(sum1+domain[j].size());

			   prob2 = prob2*(1.0*total_count2+1.0)/(sum2+domain[j].size());
			   

		     }

			if(prob1>=prob2)
			    {
				double p1=prob1/(prob1+prob2);
				System.out.println(domain[att_num-1].elementAt(0) + " " + domain[att_num-1].elementAt(point.attributes[att_num-1]) + " " + p1);
				if((String)domain[att_num-1].elementAt(0) == (String)domain[att_num-1].elementAt(point.attributes[att_num-1]))
				    analyze++;
			    }
			else
			    {
				double p2=prob2/(prob1+prob2);
				System.out.println(domain[att_num-1].elementAt(1) + " " + domain[att_num-1].elementAt(point.attributes[att_num-1]) + " " + p2);
				if((String)domain[att_num-1].elementAt(1) == (String)domain[att_num-1].elementAt(point.attributes[att_num-1]))
				    analyze++;
			    }
	      }


	    System.out.println("");
	    System.out.println(analyze);
	}
    		
    
}
