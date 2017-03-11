//JOSEPH MARATTUKALAM ANAND cs610 PP 6551

import java.io.*;

public class Decoder{

   static int heapsize;

   public static void main(String[] args) throws Exception {
     if(args.length < 1){
          System.out.println("Required Format\nArgument 1: File to Decode");
          exit();
        }
     int charsize,c,bytesize,k,ipsize,tempint,j; 
     char tempchar;
     byte[] bytebuffer = new byte[1];
     byte[] bytebuffer1 = new byte[1];
     byte[] intbytebuffer = new byte[4];
     String decodedfile ="";
     Node q;
     for(int g=0;g<args[0].length()-4;++g){
       decodedfile = decodedfile + args[0].charAt(g);
     } 
     OutputStream out = new FileOutputStream(decodedfile);
     FileInputStream is = new FileInputStream(args[0]);
     is.read(intbytebuffer,0,4);
     charsize = ((intbytebuffer[0] & 0xFF) << 24) | ((intbytebuffer[1] & 0xFF) << 16)
        | ((intbytebuffer[2] & 0xFF) << 8) | (intbytebuffer[3] & 0xFF);
     Symbol[] C = new Symbol[256];
     for(j=0;j<C.length;++j){
       C[j] = new Symbol();
       C[j].setFreq(0);
     }
     k = 0;
     while(k<charsize){
       c = is.read(bytebuffer,0,1);
       tempchar = (char)(bytebuffer[0] & 0xFF);
       c = is.read(intbytebuffer,0,4);
       tempint = ((intbytebuffer[0] & 0xFF) << 24) | ((intbytebuffer[1] & 0xFF) << 16)
        | ((intbytebuffer[2] & 0xFF) << 8) | (intbytebuffer[3] & 0xFF);
       C[tempchar].setFreq(tempint);
       k = k+1;
     }
     q = Huffman(C);
     Node temp = q;
     is.read(intbytebuffer,0,4);
     ipsize = ((intbytebuffer[0] & 0xFF) << 24) | ((intbytebuffer[1] & 0xFF) << 16)
        | ((intbytebuffer[2] & 0xFF) << 8) | (intbytebuffer[3] & 0xFF);
     k=0;
     int flag=0,t;
     while(k<ipsize){
       c = is.read(bytebuffer1,0,1);
       for(int g=7;g>=0;--g){
         k = k+1;
         t = (bytebuffer1[0] >> g) & 1;
         if(t==0){
           q = q.getLeft();
         }
         else {
           q = q.getRight();
         }
         if(q.getF()==0){
           bytebuffer[0] = (byte)q.getCharc();
           out.write(bytebuffer,0,1);
           out.flush();
           q = temp;
         }
         if(k==ipsize){
           flag =1;
           break;
         }
       }
       if(flag==1){
         break;
       }
     }    
     is.close();
     out.close();
   }
   static Node Huffman(Symbol[] C) throws IOException{
      int c,count=0,No=0;
      byte[] bytebuffer = new byte[1];
      for(c=0;c<C.length;++c){
        if(C[c].getFreq()>0){
          count +=1;
        }
      }
      Node[] Q = new Node[count];
      for(c=0;c<C.length;++c){
        if(C[c].getFreq()>0){
          Q[No] = new Node();
          bytebuffer[0] = (byte)((char)c);                                             
          Q[No].setCharc((char)(bytebuffer[0] & 0xFF));
          Q[No].setFreq(C[c].getFreq());
          No +=1;
        }
      }
      for(c=0;c<count;++c){
        Q[c].setF(0);
      }
      Build_MinHeap(Q);
      Node x,y;
      while(heapsize>1){
        Node z = new Node();
        x = EXTRACT_MIN(Q);
        z.setLeft(x);
        y = EXTRACT_MIN(Q);
        z.setRight(y);
        z.setFreq(x.getFreq() + y.getFreq());
        z.setF(1);
        InsertMinHeap(Q,z);
      }                                            
      return(EXTRACT_MIN(Q));
    }
 
    static void InsertMinHeap(Node[] Q,Node key){
      heapsize = heapsize + 1;
      int i = heapsize - 1;
      Node temp;
      Q[i] = key;
      while((i>0) && (Q[Parent(i)].getFreq() > Q[i].getFreq())){
        temp = Q[i];
        Q[i] = Q[Parent(i)];
        Q[Parent(i)] = temp;
        i = Parent(i);
      }
    
    }

    public static int Parent(int i){
      if(i%2==1){
        i +=1;
      }
      i /=2;
      i -=1;
      return(i);
    }

    static Node EXTRACT_MIN(Node[] Q){
      Node min  = Q[0];
      Q[0] = Q[heapsize-1];
      heapsize = heapsize-1;
      MinDownHeap(Q,0);
      return(min);
    }

    static void Build_MinHeap(Node[] Q){
      heapsize = Q.length;
      for(int i = (heapsize-1)/2;i>=0;i--){
        MinDownHeap(Q,i);
      }
    }
  
    static void MinDownHeap(Node[] Q,int i){
      Node temp,small;
      int f;
      f = 0;
      small = Q[i];
      if((2*i+1<heapsize)&&(Q[2*i+1].getFreq()<small.getFreq())){
        small = Q[2*i+1];
        f=1;
      }
      if((2*i+2<heapsize)&&(Q[2*i+2].getFreq()<small.getFreq())){
        small = Q[2*i+2];
        f=2;
      }
      if(f==1){
        temp = Q[i];
        Q[i] = Q[2*i+1];
        Q[2*i+1] = temp;
        MinDownHeap(Q,2*i+1);
      }
      else if(f==2){
        temp = Q[i];
        Q[i] = Q[2*i+2];
        Q[2*i+2] = temp;
        MinDownHeap(Q,2*i+2);
      }
      
    }
}