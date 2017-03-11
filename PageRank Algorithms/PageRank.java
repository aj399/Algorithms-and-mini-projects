//Google's pageranking algorithms theoratical implementation 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class PageRank {

	public static void main(String[] args) throws IOException {
		
    if(args.length<3){
      System.out.println("Expected Format");
      System.out.println("Argument 1:No: of Iterations");
      System.out.println("Argument 2:Initial Value");
      System.out.println("Argument 3:Input File")
    }
    double d = .85;
		int iterations =  Integer.parseInt(args[0]);
		int initialvalue = Integer.parseInt(args[1]);
		String file = args[2];
	  BufferedReader in = new BufferedReader(new FileReader(file));    
		String line1 = in.readLine();
		String[] line11 = line1.split(" ");
		int N =  Integer.parseInt(line11[0]);
		int M = Integer.parseInt(line11[1]);
		if(N>10){
			iterations=0;
			initialvalue=-2;
		}
		List[] L = new List[N];
		for(int i=0;i<N;++i){
			List node1 = new List(i);
			L[i] = node1;
		}
		for(int i=0;i<M;++i){
		  String line = in.readLine();
		  String[] lin = line.split(" ");
		  int v1 = Integer.parseInt(lin[0]);
		  int v2 = Integer.parseInt(lin[1]);
		  List node2 = new List(v2);
		  L[v1].setM(L[v1].getM()+1);
		  List temp = L[v1];
		  while(temp.getNext()!=null){
			  temp = temp.getNext();
		  }
		  temp.setNext(node2);
		}
		in.close();
        double[] P = new double[N];
        double init;
        if(initialvalue==-1){
        	init = 1/(double)N;
        }
        else if(initialvalue==-2){
        	init = 1/Math.sqrt(N);
        }
        else{
        	init = 1;
        }
        for(int i=0;i<N;++i){
        	P[i] = init;
        }
        double[] src = new double[N]; 
        for(int i=0;i<N;++i){
          src[i] = 1/N;
        }
        String space = " ";
        if(N>10){
        	space = "\n";
        }
        int flag = 0;
        int iter=0;
        if(N<=10){
          System.out.print("Base  :  "+iter+" :"+space);
    	  for(int i=0;i<N;++i){
        	System.out.print("P[ "+i+"]="+new DecimalFormat("#0.000000").format(P[i])+space);
          }
    	  System.out.println();
        }
    	iter++;
		while(flag==0){
	    	double[] temp =  new double[N];
	        for(int i =0;i<N;++i){
	        	temp[i] = P[i];
	        }
	        double[] D = new double[N];
	        for(int i =0;i<N;++i){
	        	D[i] = 0;
	        }
	    	for(int i=0;i<N;++i){
	    		List ntemp = L[i];              
	    		while(ntemp.getNext()!=null){
	    			ntemp = ntemp.getNext();
	    			D[ntemp.getIndex()] += temp[i]/L[i].getM();
	    		}
	    	}
	    	for(int i=0;i<N;++i){
	    		P[i] = (1-d)/N + d*D[i];
	    	}
	    	flag = 1;
	    	if(iterations<1){
	    	  for(int i=0;i<N;++i){
	    		if((int)(temp[i]*100000)!=(int)(P[i]*100000)){
	    			flag=0;
	    			break;
	    		}
	    	  }
	    	}
	    	else{
	    		if(iter<iterations){
	    			for(int i=0;i<N;++i){
			    		if((int)(temp[i]*100000)!=(int)(P[i]*100000)){
			    			flag=0;
			    			break;
			    		}
			    	}
	    		}
	    		  
	    	}
                if(N<=10){
	    	  System.out.print("Iter  :  "+iter+" :"+space);
	     	  for(int i=0;i<N;++i){
	         	System.out.print("P[ "+i+"]="+new DecimalFormat("#0.000000").format(P[i])+space);
	          }
	     	  System.out.println();
                }
	     	iter++;
	    }
		if(N>10){
			System.out.print("Iter  :  "+(iter-1)+" :"+space);
	     	for(int i=0;i<N;++i){
	         	System.out.print("P[ "+i+"]="+new DecimalFormat("#0.000000").format(P[i])+space);
	         }
	     	System.out.println();
	    }
	}

}
