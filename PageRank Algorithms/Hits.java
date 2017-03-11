//Yahoo's Hit's pageranking algorithms theoratical implementation 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class Hits{
  
  public static void main(String[] Args) throws IOException{
  
  if(args.length<3){
    System.out.println("Expected Format");
    System.out.println("Argument 1:No: of Iterations");
    System.out.println("Argument 2:Initial Value");
    System.out.println("Argument 3:Input File")
  }
  int iterations =  Integer.parseInt(Args[0]);
	int initialvalue = Integer.parseInt(Args[1]);
	String file = Args[2];
  BufferedReader in = new BufferedReader(new FileReader(file));
    
	String line1 = in.readLine();
	String[] line11 = line1.split(" ");
	int N =  Integer.parseInt(line11[0]);
	int M = Integer.parseInt(line11[1]);
	if(N>10){
		iterations=0;
		initialvalue=-2;
	}
	int[][] L = new int[N][N];
	for(int i=0;i<M;++i){
	  String line = in.readLine();
	  String[] lin = line.split(" ");
	  int v1 = Integer.parseInt(lin[0]);
	  int v2 = Integer.parseInt(lin[1]);
	  L[v1][v2] = 1;
	}
	in.close();
    double[][] LBS = new double[N][2];
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
    	LBS[i][0] = init;
    	LBS[i][1] = init;
    }
    int[][] Lt = new int[N][N];
    for(int i=0;i<N;++i){
    	for(int j=0;j<N;++j){
    		Lt[i][j] = L[j][i];
    	}
    }
    int flag=0;
    int iter=0;
    String space = " ";
    if(N>10){
    	space = "\n";
    }
    if(N<=10){
      System.out.print("Base  :  "+iter+" :"+space);
	  for(int i=0;i<N;++i){
		System.out.print("A[ "+i+"]/H[ "+i+"]="+new DecimalFormat("#0.000000").format(LBS[i][0])+"/"+new DecimalFormat("#0.000000").format(LBS[i][1])+space);
      }
	  System.out.println();
    }
	iter++;
    while(flag==0){
    	double asum = 0;
    	double[][] temp =  new double[N][2];
        for(int i =0;i<N;++i){
        	temp[i][0] = LBS[i][0];
        	temp[i][1] = LBS[i][1];
        }
        
    	for(int i=0;i<N;++i){
    		LBS[i][0] = 0;
    		for(int j=0;j<N;++j){
    		  LBS[i][0] += Lt[i][j]*LBS[j][1];	
    		}
    		asum += LBS[i][0]*LBS[i][0];
    	}
    	asum = Math.sqrt(asum);
    	double hsum =0;
    	for(int i=0;i<N;++i){
    		LBS[i][1] = 0;
    		for(int j=0;j<N;++j){
    		  LBS[i][1] += L[i][j]*LBS[j][0];	
    		}
    		hsum += LBS[i][1]*LBS[i][1];
    	}
    	hsum =  Math.sqrt(hsum);
    	for(int i=0;i<N;++i){
        	LBS[i][0] = (LBS[i][0])/asum;
        	LBS[i][1] = (LBS[i][1])/hsum;
        }
    	flag = 1;
    	if(iterations<1){
    	  for(int i=0;i<N;++i){
    		if(((int)(temp[i][0]*100000)!=(int)(LBS[i][0]*100000))||((int)(temp[i][1]*100000)!=(int)(LBS[i][1]*100000))){
    			flag=0;
    			break;
    		}
    	  }
    	}
    	else{
    		if(iter<iterations){
    			for(int i=0;i<N;++i){
    	    		if(((int)(temp[i][0]*100000)!=(int)(LBS[i][0]*100000))||((int)(temp[i][1]*100000)!=(int)(LBS[i][1]*100000))){
    	    			flag=0;
    	    			break;
    	    		}
    	    	}
    		}
    	}
    	if(N<=10){
    	  System.out.print("Iter  :  "+iter+" :"+space);
    	  for(int i=0;i<N;++i){
        	System.out.print("A[ "+i+"]/H[ "+i+"]="+new DecimalFormat("#0.000000").format(LBS[i][0])+"/"+new DecimalFormat("#0.000000").format(LBS[i][1])+space);
          }
    	  System.out.println();
    	}
    	iter++;
    }
    if(N>10){
    	System.out.print("Iter  :  "+(iter-1)+" :"+space);
    	for(int i=0;i<N;++i){
        	System.out.print("A[ "+i+"]/H[ "+i+"]="+new DecimalFormat("#0.000000").format(LBS[i][0])+"/"+new DecimalFormat("#0.000000").format(LBS[i][1])+space);
        }
    	System.out.println();
    }
    
  }
  
}