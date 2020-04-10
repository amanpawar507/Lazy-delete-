import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HashC {
    public static void main(String[] args) throws Exception {
        BufferedReader br= new BufferedReader(new FileReader(new File("C:\\Users\\7aman\\Desktop\\ads2.txt")));
        String line;
        Scanner sc = new Scanner(System.in);
        int choice=1;
        ArrayList<String> tokens= new ArrayList<>();
        ArrayList<Boolean> lazdel = new ArrayList<Boolean>(21);
        String arr[];
        int count=0;
        Function<String,Stream<String>> lineSplitter= l-> Pattern.compile("[^a-zA-Z]").splitAsStream(l);
       // System.out.println(" Reading File & Tokenizing ");
        while((line = br.readLine())!=null){
            arr= line.split("[^a-zA-Z]");
            for(String a:arr){
                if(!tokens.contains(a)){
                    lazdel.add(false);
                    tokens.add(a);
                    count++;
                }
            }          
//            Stream.of(line).flatMap(lineSplitter).
        }
        HashString s = new HashString(count+3);
        //System.out.println(" Inserting C file in the HashTable");
        for(String l:tokens){
         s.insert(l);
        }
        System.out.println("Choose from the following:-");
        while(choice!=0){
            System.out.println("1.Insert \t 2. Display \t 3.Search");
            System.out.println("4.Lazy Delete \t ");
            choice = sc.nextInt();
            switch(choice){
                case 1: System.out.println("Enter Element to insert");
                        String tmp = sc.next();
                        if(s.isFull()){
                            System.out.println("Storage Full!!!!");
                        }
                        else{
                        s.insert(tmp);
                        }
                        break;
                case 2: s.Display();
                        break;
                case 3: System.out.println("Enter Element to Search");
                        String search = sc.next();
                        s.Search(search);
                        break;
                case 4: System.out.println("Enter element to lazy delete:");
                        String lazzdel = sc.next();
                        s.LazyDelete(lazzdel);
                        break;
                case 5: System.out.println("Enter Element to Delete");
                        String del = sc.next();
                        s.Delete(del);
                        break;
               
            }
            System.out.println("Do you Want to Continue 1. YES \t 0. NO");
            choice= sc.nextInt();
        }
        
    }
}
class HashString{
    int size,currentSize;
    Boolean[] arr1= new Boolean[100];
    String[] arr;
    int collision[];
    int keys[];
    public HashString(int size) {
        Arrays.fill(arr1, Boolean.FALSE);
        this.size = size;
        currentSize=0;
        arr= new String[size];
        collision = new int[size];
        keys= new int[size];
    }
    int Hash(String s){
        int hashed=0;
        for(char c:s.toCharArray()){
            hashed+=(int)c;
        }
        return hashed;
    }
    void incSize(){
        size++;
    }
    boolean isFull(){
        return currentSize==size-1;
    }
    boolean isEmpty(){
        return currentSize==0;
    }
    void insert(String s){
       
        int key = Hash(s);
        int loc = key%size;
        int sq=0;
        int i=0;
        ArrayList<String> visited = new ArrayList<>();
        visited.add(Integer.toString(loc));
        
                while( arr[loc]!=null && (!(Hash(arr[loc])==key))){
                   i++;
                   sq++;
                   loc= (loc+(sq*sq))%size;
                   visited.add(Integer.toString(loc));
                }
                if(arr[loc]!=null){
                    System.out.println("Key already inserted.");
                }
                else{
                    if(arr1[loc]=true){
                    Delete(arr[loc]);
                    }
                    
                        while(arr[loc]!=null){
                        i++;
                        sq++;
                        loc= (loc+(sq*sq))%size;
                        }
                         collision[loc]=i+1;
                        arr[loc]=s;      
                        keys[loc]=key;
                        currentSize++;
                    
                }
               
//       double tmp1=currentSize,tmp2=size;
//      
//       double t= (tmp1/tmp2);
        //System.out.println("Load Factor "+ (t*100) +"for ->"+s);
    }

    void Display(){
        System.out.println("HashTable is:-");
        for(int i=1;i<size;i++){
            if(!(arr[i]==null)){
                System.out.println("Index -"+(i-1)+"\t"+"Collisions - "+(collision[i]-1)+ "\t   value - "+arr[i]);
            }
        }
    }
    int Search(String s1){
        int key = Hash(s1);
        int loc = key%size;
        int sq=0;
        int i=0;
        ArrayList<String> visited = new ArrayList<>();
        visited.add(Integer.toString(loc));
        while( arr[loc]!=null && (!(Hash(arr[loc])==key))){
            i++;
           sq++;
           loc= (loc+(sq*sq))%size;
           visited.add(Integer.toString(loc));
        }
        if(arr[loc]!=null){
            System.out.println("Key Found at "+loc+" , collision "+i+" Value-> "+arr[loc]);
            return loc;
        }
        else{
            System.out.println("Key not found , Collisions -> "+ (i+1));
            return -1;
        }
    }
    void Delete(String s){
        int loc = Search(s);
        if(loc==-1){
            System.out.println("Unable to Delete");
        }
        else{
            System.out.println("Deleted Successfully "+ arr[loc]);
            arr[loc]=null;
            collision[loc]=-1;
            keys[0]=-1;
            currentSize--;
        }
       
    }
    
    void LazyDelete(String s){
        
        int loc = Search(s);
        if(loc==-1){
            System.out.println("Unable to Delete");
        }
        else{
            System.out.println("Deleted Successfully "+ arr[loc]);
            	arr1[loc]= true;
                collision[loc]=-1;         
            }
        System.out.println("Deleted:"+ arr[loc] +"\t value:" + arr1[loc]);  
        }
    }



