//HUFFMAN ENCODER

import java.io.*;

public class henc {

    static int heapsize;

    public static void main(String[] args) throws IOException {
           
        String encodedfile = args[0]+".huf";
        File file=new File(args[0]);
        byte[] bytebuffer = new byte[1];
        byte[] intbytebuffer = new byte[4];
        InputStream is = new FileInputStream(file);
        InputStream is1 = new FileInputStream(file);
        int c = 0,j,flag;
        Node q;
        Symbol[] C = new Symbol[256];
        for(j=0;j<C.length;++j){
          C[j] = new Symbol();
          C[j].setFreq(0);
        }
        while ((c = is.read(bytebuffer,0,1)) > 0) {
            char c1 = (char)(bytebuffer[0] & 0xFF);
            C[(int)c1].setFreq(C[(int)c1].getFreq() + 1); 
        }
        is.close();
        q = Huffman(C,encodedfile);
        FileOutputStream out = new FileOutputStream(encodedfile,true);
        flag = 0;
        Node tempnode=q;
        traverse(q,C,"");
        q = tempnode;
        int bitcount =0;
        for(int g=0;g<C.length;++g){
          if(C[g].getFreq()>0){
            bitcount+= C[g].getSymbol().length()*C[g].getFreq();
          }
        }
        int counter =0;
        char ch;
        intbytebuffer[0] = (byte) (bitcount >> 24);
        intbytebuffer[1] = (byte) (bitcount >> 16);
        intbytebuffer[2] = (byte) (bitcount >> 8);
        intbytebuffer[3] = (byte) (bitcount);
        out.write(intbytebuffer,0,4);
        int bytecount = (bitcount+bitcount%8)/8;
        byte[] nBuff = new byte[bytecount+1];
        int bitcounter =8;
        int bytecounter =0;
        while(is1.available()>0){
          is1.read(bytebuffer,0,1);
          ch = (char)(bytebuffer[0] & 0xFF);
          for(int g=0;g<C[(int)ch].getSymbol().length();++g){
            if(bitcounter>0){
              bitcounter -=1;
            }
            else{
              bytecounter +=1;
              bitcounter = 7;
            }
            if(C[(int)ch].getSymbol().charAt(g)=='1'){
              nBuff[bytecounter] |= (1 << bitcounter);
            }
            else{
              nBuff[bytecounter] &= ~(1 << bitcounter);
            }
          }
        }
        out.write(nBuff,0,bytecount);
        out.close();
    }

   
    static void traverse(Node Q,Symbol[] C,String s){
      
        if(Q.getLeft()==null){
          C[(int)Q.getCharc()].setSymbol(s);
        }
        else{
          traverse(Q.getLeft(),C,s+"0");
        }
        if(Q.getRight()==null){
          C[(int)Q.getCharc()].setSymbol(s);
        }
        else{
          traverse(Q.getRight(),C,s+"1");
        }
    }
    
    
    static Node Huffman(Symbol[] C,String encodedfile) throws IOException{
      int No=0,count=0,c;
      byte[] bytebuffer = new byte[1];
      byte[] intbytebuffer = new byte[4];
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
      FileOutputStream out = new FileOutputStream(encodedfile);
      intbytebuffer[0] = (byte) (count >> 24);
      intbytebuffer[1] = (byte) (count >> 16);
      intbytebuffer[2] = (byte) (count >> 8);
      intbytebuffer[3] = (byte) (count);
      out.write(intbytebuffer,0,4);
      for(c=0;c<count;++c){
        bytebuffer[0] = (byte)Q[c].getCharc();
        out.write(bytebuffer,0,1);
        intbytebuffer[0] = (byte) (Q[c].getFreq() >> 24);
        intbytebuffer[1] = (byte) (Q[c].getFreq() >> 16);
        intbytebuffer[2] = (byte) (Q[c].getFreq() >> 8);
        intbytebuffer[3] = (byte) (Q[c].getFreq());
        out.write(intbytebuffer,0,4);
      }
      out.close();
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