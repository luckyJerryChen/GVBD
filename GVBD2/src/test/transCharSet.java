package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class transCharSet {
	public static void main(String[] args) throws IOException {
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Administrator\\Desktop\\数据\\needData\\11111.txt"),"GBK"));
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter (new FileOutputStream("C:\\Users\\Administrator\\Desktop\\数据\\needData\\33333.txt"),"UTF-8"));
			String line =br.readLine();
			while(line!=null){
				bw.write(line);
				System.out.println(line);
				bw.newLine();
				line=br.readLine();
			}
			br.close();
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
