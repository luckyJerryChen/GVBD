package test;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Random;

public class CopyOfgenerateData
{
  public static int VERTEX_COUNT = 110;
  public static int EDGE_COUNT_BASE = 10;
  public static int EDGE_COUNT_RANDOM = 8;
  public static String DEFAULT_VERTEX_VALUE = "10.0";
  public static String DEFAULT_EDGE_WEIGHT = "0";
  public static String VERTEX_ID_VALUE_SPLIT = ":";
  public static String EDGE_ID_WEIGHT_SPLIT = ":";
  public static String VERTEX_EDGE_SPLIT = "\t";
  public static String EDGE_EDGE_SPLIT = " ";
  public static String OUTPUT_PATH = "/home/pagerank.txt";
  public static int STATIC_VERTEX = 0;
  public static int STATIC_EDGE = 0;
  
  public static void main(String[] args)
  {
    VERTEX_COUNT = Integer.parseInt(args[0]);
    OUTPUT_PATH =  "./WebRoot/dataSimple/random.txt";
    
    if (args.length != 2)
    {
      System.out.println("Please input like this type:");
      System.out.println("java -jar nodenum outpupath");
      return;
    }
    Random rVertex = new Random();
    Random rVertexCount = new Random();
    File root = new File(OUTPUT_PATH);
    if(root.exists()){
    	root.delete();
    }
    try
    {
      System.out.println("Begin to generate the dataset for PageRank, please wait......");
      
      FileWriter fw = new FileWriter(root);
      BufferedWriter bw = new BufferedWriter(fw);
      


      int count = rVertexCount.nextInt(EDGE_COUNT_RANDOM) + EDGE_COUNT_BASE;
      StringBuffer sb = new StringBuffer(rVertex.nextInt(VERTEX_COUNT) + 
        EDGE_ID_WEIGHT_SPLIT + DEFAULT_EDGE_WEIGHT);
      for (int j = 1; j < count; j++) {
        sb.append(EDGE_EDGE_SPLIT + rVertex.nextInt(VERTEX_COUNT) + 
          EDGE_ID_WEIGHT_SPLIT + DEFAULT_EDGE_WEIGHT);
      }
      bw.write(0 + VERTEX_ID_VALUE_SPLIT + DEFAULT_VERTEX_VALUE + 
        VERTEX_EDGE_SPLIT + sb.toString());
      
      STATIC_VERTEX += 1;
      STATIC_EDGE += count;
      for (int i = 1; i < VERTEX_COUNT; i++)
      {
        count = rVertexCount.nextInt(EDGE_COUNT_RANDOM) + EDGE_COUNT_BASE;
        sb = new StringBuffer(rVertex.nextInt(VERTEX_COUNT) + 
          EDGE_ID_WEIGHT_SPLIT + DEFAULT_EDGE_WEIGHT);
        for (int j = 1; j < count; j++) {
          sb.append(EDGE_EDGE_SPLIT + rVertex.nextInt(VERTEX_COUNT) + 
            EDGE_ID_WEIGHT_SPLIT + DEFAULT_EDGE_WEIGHT);
        }
        bw.newLine();
        bw.write(i + VERTEX_ID_VALUE_SPLIT + DEFAULT_VERTEX_VALUE + 
          VERTEX_EDGE_SPLIT + sb.toString());
        
        STATIC_VERTEX += 1;
        STATIC_EDGE += count;
      }
      bw.close();
      fw.close();
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
    System.out.println("Generate data successfully and the statics as follws:");
    System.out.println("Size of DataSet : " + (float)root.length() / 1048576.0F + " MB");
    System.out.println("VertexCount : " + STATIC_VERTEX);
    System.out.println("EdgeCount : " + STATIC_EDGE);
    System.out.println("Average outgoing degree : " + STATIC_EDGE / STATIC_VERTEX);
  }
}
