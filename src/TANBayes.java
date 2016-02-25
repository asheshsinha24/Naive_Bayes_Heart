import java.io.*;
import java.util.*;

public class TANBayes
{

    public static double[][] cond_prob;

	public static int class1;
    public static int class2;


    public static Hashtable<String, Hash_Tag> Bayes_Hash = new Hashtable<String, Hash_Tag>();

    public TANBayes(int att_num)
    {
	cond_prob = new double[att_num][att_num];
    }

public class Hash_Tag
{
    int count_f;
};

    public void createBayes(Bayes d_tree1, Bayes d_tree2, Node root, Node root1, int []real_val)
    {
	CondProb(root, d_tree1, real_val);

	int MST[][] = Prims(d_tree1);

	//System.out.println("");
	
	System.out.println(d_tree1.att_name[0] + " class");
	for (int i=1; i<d_tree1.att_num-1;i++)
	    {
		for (int j=0; j<MST.length;j++)
		    {
			if(MST[j][1]==i)
			    System.out.println(d_tree1.att_name[MST[j][1]] + " " + d_tree1.att_name[MST[j][0]] + " class");
		    }
	       
	    }

	System.out.println("");
	
	BayesTest( d_tree1, root1, real_val, MST);
	
	
    }

    public void CondProb(Node root, Bayes dt1, int[] real_val)
    {
	class1=0;
	class2=0;

	for (int k=0;k<root.data.size(); k++)
	    {
		DataPoint point = (DataPoint)root.data.elementAt(k);

		if(point.attributes[dt1.att_num-1]==0)
		    class1+=1;
		else
		    class2+=1;

		for (int i=0; i<dt1.att_num-1; i++)
		    {
			String key = dt1.att_name[i] + (String)dt1.domain[i].elementAt(point.attributes[i]) + (String)dt1.domain[dt1.att_num-1].elementAt(point.attributes[dt1.att_num-1]);
			if(Bayes_Hash.containsKey(key))
			    {
				Hash_Tag h1= Bayes_Hash.get(key);
				h1.count_f= h1.count_f+1;
				Bayes_Hash.put(key,h1);
			    }
			else
			    {
			     
				Hash_Tag h1= new Hash_Tag();
				h1.count_f=1;
				Bayes_Hash.put(key,h1);
			    }

			for (int j=0; j<dt1.att_num-1; j++)
			    {
				String key_com = dt1.att_name[i] + (String)dt1.domain[i].elementAt(point.attributes[i]) + dt1.att_name[j] + (String)dt1.domain[j].elementAt(point.attributes[j]) + (String)dt1.domain[dt1.att_num-1].elementAt(point.attributes[dt1.att_num-1]);
				if(Bayes_Hash.containsKey(key_com))
				    {
					Hash_Tag h1= Bayes_Hash.get(key_com);
					h1.count_f+=1;
					Bayes_Hash.put(key_com,h1);
				    }
				else
				    {
					Hash_Tag h1= new Hash_Tag();
					h1.count_f=1;
					Bayes_Hash.put(key_com,h1);
				    }
			    }
		    }
	    }

	
	for (int i1=0;i1<dt1.att_num-1; i1++)
	    {
		for (int j1=0; j1< dt1.att_num-1; j1++)
		    {
			if(i1==j1)
			    {
				cond_prob[i1][j1]=-1;
				continue;
			    }
			
			double sum_prob=0;

			for (int i2=0; i2<dt1.domain[i1].size();i2++)
			    {
				for (int j2=0; j2<dt1.domain[j1].size(); j2++)
				    {
					int val1=0, val2=0, val3=0, val4=0,val11=0,val12=0;
					String key1 = dt1.att_name[i1] + (String)dt1.domain[i1].elementAt(i2) + (String)dt1.domain[dt1.att_num-1].elementAt(0);
					String key2 = dt1.att_name[i1] + (String)dt1.domain[i1].elementAt(i2) + (String)dt1.domain[dt1.att_num-1].elementAt(1);
					String key3 = dt1.att_name[j1] + (String)dt1.domain[j1].elementAt(j2) + (String)dt1.domain[dt1.att_num-1].elementAt(0);
					String key4 = dt1.att_name[j1] + (String)dt1.domain[j1].elementAt(j2) + (String)dt1.domain[dt1.att_num-1].elementAt(1);
					String key11 = dt1.att_name[i1] + (String)dt1.domain[i1].elementAt(i2)+ dt1.att_name[j1] + (String)dt1.domain[j1].elementAt(j2) + (String)dt1.domain[dt1.att_num-1].elementAt(0);
					String key12 = dt1.att_name[i1] + (String)dt1.domain[i1].elementAt(i2) + dt1.att_name[j1] + (String)dt1.domain[j1].elementAt(j2) + (String)dt1.domain[dt1.att_num-1].elementAt(1);
					
					if(Bayes_Hash.containsKey(key1))
					    {
						Hash_Tag h1= Bayes_Hash.get(key1);
						val1=h1.count_f;
					    }
			
					if(Bayes_Hash.containsKey(key2))
					    {
						Hash_Tag h1= Bayes_Hash.get(key2);
						val2=h1.count_f;
					    }
					if(Bayes_Hash.containsKey(key3))
					    {
						Hash_Tag h1= Bayes_Hash.get(key3);
						val3=h1.count_f;
					    }
					if(Bayes_Hash.containsKey(key4))
					    {
						Hash_Tag h1= Bayes_Hash.get(key4);
						val4=h1.count_f;
					    }
					if(Bayes_Hash.containsKey(key11))
					    {
						Hash_Tag h1= Bayes_Hash.get(key11);
						val11=h1.count_f;
					    }
					if(Bayes_Hash.containsKey(key12))
					    {
						Hash_Tag h1= Bayes_Hash.get(key12);
						val12=h1.count_f;
					    }
					
					double c1 = val12;
					double c2 =  val12;
			                double c3 = val2;
					double c4 =   val4;
					
					double prob1=0;
					double prob2=0;
					
					
					prob1= (1.0*val11+1)/(class1+class2+dt1.domain[i1].size()*dt1.domain[j1].size()*2)*Math.log(((1.0*val11+1)/(class1+dt1.domain[i1].size()*dt1.domain[j1].size()))/((1.0*val1+1)/(class1 + dt1.domain[i1].size())*(1.0*val3+1)/(class1+dt1.domain[j1].size())))/Math.log(2);
			       

				       
					prob2= (1.0*val12+1)/(class1+class2+dt1.domain[i1].size()*dt1.domain[j1].size()*2)*Math.log(((1.0*val12+1)/(class2 + dt1.domain[i1].size()*dt1.domain[j1].size()))/((1.0*val2+1)/(class2 + dt1.domain[i1].size())*(1.0*val4+1)/(class2 +dt1.domain[j1].size())))/Math.log(2); 

					sum_prob = sum_prob + prob1+prob2;
				    }
			    }
			cond_prob[i1][j1]=sum_prob;
			//System.out.print(cond_prob[i1][j1] + " ");
		    }
		//System.out.println("");
	    }
					

	
    }

    public int[][] Prims(Bayes dt1)
    {
        ArrayList<Integer> V = new ArrayList<Integer>();
        int [][] E = new int [dt1.att_num-2][2];
	boolean []visited = new boolean[dt1.att_num-1];
	
	for (int i=0; i<dt1.att_num-1; i++)
	    {
		visited[i]=false;
	    }

	int index=0;
	int index1=0;
	int index2=0;
	
	visited[index]=true;
	V.add(index);
	int count=1;
	
	while(V.size()<dt1.att_num-1)
	    { 
		double max_val=-10000;
		for(int j=0; j<V.size();j++)
		    {

			for (int i=0; i<dt1.att_num-1; i++)
			    {
				if(visited[i]==false)
				    {
					double n_val = cond_prob[V.get(j)][i];
					
					if(n_val>max_val)
					    {
						max_val=n_val;
						index1=i;
						index2=V.get(j);
					    }
				    }
		
		
			    }
		    }
		visited[index1]=true;
		V.add(index1);
		E[count-1][0]=index2;
		E[count-1][1]=index1;
		count++;
	    }

	
	return E;
    }


    

    public void BayesTest(Bayes dt1, Node root1, int[] real_val, int [][] MST)
	{

	    int att_num = dt1.att_num;
	    int analyze=0;

	    // Finding parent
	    int [] parent=new int[dt1.att_num-1];
	    parent[0]=-1;
	    for (int j=0; j<att_num-1; j++)
		{
		 

		    for (int k=0; k<MST.length;k++)
			{
			    if(MST[k][1]==j)
				parent[j]=MST[k][0];
				    
			}
		}

	    for(int i=0; i<root1.data.size(); i++)
	    {
	       
		double prob1=(1.0*class1+1.0)/(class1+class2+2);
		double prob2=(1.0*class2+1.0)/(class1+class2+2);

		DataPoint point = (DataPoint)root1.data.get(i);

		for (int j=0; j<point.attributes.length-1; j++)
		    {
			
			
			String key1 = dt1.att_name[j] + (String)dt1.domain[j].elementAt(point.attributes[j]) + (String)dt1.domain[dt1.att_num-1].elementAt(0);
			String key2 = dt1.att_name[j] + (String)dt1.domain[j].elementAt(point.attributes[j]) + (String)dt1.domain[dt1.att_num-1].elementAt(1);

			int total_count1=0;
			int total_count2=0;
			int total_count3=0;
			int total_count4=0;
			int total_count11=0;
			int total_count12=0;

			if(Bayes_Hash.containsKey(key1))
			   {
			       
			       Hash_Tag h1 = Bayes_Hash.get(key1);
			       total_count1 = h1.count_f;
				  
			    }

			   if(Bayes_Hash.containsKey(key2))
			   {
			       Hash_Tag h1= Bayes_Hash.get(key2);   
			       total_count2 = h1.count_f;	 
				
			   }
			   
			   String key11 = " ";
			   String key12 = " ";

			 
			if(j>0)
			    {
			
				  String key3 = dt1.att_name[parent[j]] + (String)dt1.domain[parent[j]].elementAt(point.attributes[parent[j]]) + (String)dt1.domain[dt1.att_num-1].elementAt(0);
				String key4 = dt1.att_name[parent[j]] + (String)dt1.domain[parent[j]].elementAt(point.attributes[parent[j]]) + (String)dt1.domain[dt1.att_num-1].elementAt(1);
	
				key11 = dt1.att_name[j] + (String)dt1.domain[j].elementAt(point.attributes[j])+ dt1.att_name[parent[j]] + (String)dt1.domain[parent[j]].elementAt(point.attributes[parent[j]]) + (String)dt1.domain[dt1.att_num-1].elementAt(0);
				key12 = dt1.att_name[j] + (String)dt1.domain[j].elementAt(point.attributes[j])+ dt1.att_name[parent[j]] + (String)dt1.domain[parent[j]].elementAt(point.attributes[parent[j]]) + (String)dt1.domain[dt1.att_num-1].elementAt(1);

			
				if(Bayes_Hash.containsKey(key3))
				    {
			       
					Hash_Tag h1 = Bayes_Hash.get(key3);
					total_count3 = h1.count_f;
				  
				    }

				if(Bayes_Hash.containsKey(key4))
				    {
					Hash_Tag h1= Bayes_Hash.get(key4);   
					total_count4 = h1.count_f;	 
				
				    }
				if(Bayes_Hash.containsKey(key11))
				    {
			       
					Hash_Tag h1 = Bayes_Hash.get(key11);
					total_count11 = h1.count_f;
				  
				    }

				if(Bayes_Hash.containsKey(key12))
				    {
					Hash_Tag h1= Bayes_Hash.get(key12);   
					total_count12 = h1.count_f;	 
				
				    }
			    }
			   
			

			   if(j==0)
			       {
				   prob1 = prob1*(1.0*total_count1+1.0)/(class1+dt1.domain[j].size());
				   prob2 = prob2*(1.0*total_count2+1.0)/(class2+dt1.domain[j].size());
			       }
			   else
			       {

				   prob1 = prob1*(1.0*total_count11+1.0)/(total_count3+ dt1.domain[j].size());
				   prob2 = prob2*(1.0*total_count12+1.0)/(total_count4+ dt1.domain[j].size());
			       }

			   


		     }

			if(prob1>=prob2)
			    {
				double p1=prob1/(prob1+prob2);
				System.out.println(dt1.domain[att_num-1].elementAt(0) + " " + dt1.domain[att_num-1].elementAt(point.attributes[att_num-1]) + " " + p1);
				if((String)dt1.domain[att_num-1].elementAt(0) == (String)dt1.domain[att_num-1].elementAt(point.attributes[att_num-1]))
				    analyze++;
			    }
			else
			    {
				double p2=prob2/(prob1+prob2);
				System.out.println(dt1.domain[att_num-1].elementAt(1) + " " + dt1.domain[att_num-1].elementAt(point.attributes[att_num-1]) + " " + p2);
				if((String)dt1.domain[att_num-1].elementAt(1) == (String)dt1.domain[att_num-1].elementAt(point.attributes[att_num-1]))
				    analyze++;
			    }
	      }


	    System.out.println("");
	    System.out.println(analyze);
	}
   	
    
}
