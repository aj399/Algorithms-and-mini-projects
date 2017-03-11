
import java.io.Serializable;

public class Node implements Serializable{
      int freq,f;
      char charc;
      Node left,right,parent;

     
      public void setFreq(int freq1){
        freq = freq1;
      }

      public void setCharc(char charc1){
        charc = charc1;
      }

      public void setLeft(Node left1){
        left = left1;
      }

      public void setRight(Node right1){
        right = right1;
      }

      public void setParent(Node parent1){
        parent = parent1;
      }

      public void setF(int f1){
        f = f1;
      }

      public int getF(){
        return (f);
      }

      public Node getParent(){
        return (parent);
      }

      public Node getLeft(){
        return (left);
      }

      public Node getRight(){
        return (right);
      }

      public int getFreq(){
        return (freq);
      }

      public char getCharc(){
        return (charc);
      }

    }