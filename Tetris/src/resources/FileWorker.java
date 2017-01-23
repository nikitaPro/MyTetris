package resources;

import java.io.*;
import java.util.ArrayList;

public class FileWorker {
	/**
	 * ����������� �����, ������� ������ � ����,
	 * ���� ����� ��� �� ������� 
	 * @param fileName - ���� � �����.
	 * @param text - ����� ������� ����� ������� � ����.
	 */
	public static void write(String fileName, String text)
	{
		//������ � ����

		File myfile = new File(fileName);
		try{
			if(!myfile.exists()) myfile.createNewFile();
			PrintWriter fout = new PrintWriter(myfile.getAbsoluteFile());
			
			try {	fout.print(text); 	}
			finally{
				fout.close();
					}
			
			} catch(IOException e){
		throw new RuntimeException(e);}
		
	}
	/**
	 * ����������� �����, ������� ������ � ����,
	 * ���� ����� ��� �� ������� 
	 * @param fileName - ���� � �����.
	 * @param text - ����� ������� ����� ������� � ����.
	 */
	public static void writeTab(String fileName, ArrayList<String> list)
	{
		//������ � ����
		File myfile = new File(fileName);
		String text=new String();
		int k;
		try{
			if(!myfile.exists()) myfile.createNewFile();
			PrintWriter fout = new PrintWriter(myfile.getAbsoluteFile());
			for(int i=0;i<(k=list.size());i++)
			{
				if(i!=k-1)
					text+=list.get(i)+"\n";
				else 
					text+=list.get(i);
			}
			try {	fout.print(text); 	}
			finally{
				fout.close();
					}
			
			} catch(IOException e){
		throw new RuntimeException(e);}
		
	}
	/**
	 * ������� ���� � ������
	 * @param fileName - ���� � ��������� �����.
	 * @return - String - ���� ����� ��� ��� � �����
	 * @throws FileNotFoundException
	 */
	public static String read(String fileName) throws FileNotFoundException {

		StringBuilder sb = new StringBuilder();
		File myfile = new File(fileName);
		exists(fileName);
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(myfile.getAbsoluteFile()));
	        try {
	            //� ����� ��������� ��������� ����
	            String s;
	            while ((s = in.readLine()) != null) {
	            	
	                sb.append(s);
	                sb.append("\r\n");
	                
	            }
	        } finally {
	            //������� ����
	            in.close();
	        }
	        
		}catch(IOException e) {
	        throw new RuntimeException(e);
	    }
		return sb.toString();
	}
	/**
	 * ��������� ���� �� ������� � ������ �����.
	 * @param fileName - ���� � ��������� �����.
	 * @return -  ArrayList<String> - ������ ������ �����
	 * @throws FileNotFoundException
	 */
	public static ArrayList<String>  readTab(String fileName) throws FileNotFoundException {
		System.out.println();
		//������ ���� � ���� ������ �����
		 ArrayList<String> list = new ArrayList<String>();
		File myfile = new File(fileName);
		exists(fileName);
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(myfile.getAbsoluteFile()));
	        try {
	            //� ����� ��������� ��������� ����
	            String s;
	            int i=0;
	            while ((s = in.readLine()) != null) {
	            	
	                list.add(i, s);
	                i++;
	                //sb.append("\r\n");
	            }
	        } finally {
	            //������� ����
	            in.close();
	        }
	        
		}catch(IOException e) {
	        throw new RuntimeException(e);
	    }
		return list;
	}
	/** ���������� ����� ������
	 * ������ ���������� ���� ����� ���.
	 * @param fileName - ���� � ��������� �����.
	 * @throws FileNotFoundException
	 */
	private static void exists(String fileName) throws FileNotFoundException {
		//�������� �� ���-��� �����
	    File file = new File(fileName);
	    if (!file.exists()){
	        throw new FileNotFoundException(file.getAbsolutePath());
	    }
 	}
	/**
	 * ��������� ���������� �� ����.
	 * @param fileName
	 * @return int = -1 - ���� ����� �� ���������� � 0 ���� ����������
	 */
	public static int existsOrNot(String fileName)
	{
		File file = new File(fileName);
	    if (!file.exists()){
	        return -1;
	    }
	    else return 0;
	}
	public static void update(String fileName, String newText)throws FileNotFoundException
	{
		//����������� � ����� �����
		exists(fileName);
		StringBuffer sb = new StringBuffer();
		String str;
		str = read(fileName);
		sb.append(str);
		sb.append(newText);
		write(fileName, sb.toString());
		
	}
	public static void delete(String nameFile) throws FileNotFoundException {
	    exists(nameFile);
	    new File(nameFile).delete();
	}
	public static String read(String fileName, int lineNumber) throws FileNotFoundException {
		//������ ������ � ������� lineNumber
		StringBuilder sb = new StringBuilder();
		File myfile = new File(fileName);
		exists(fileName);
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(myfile.getAbsoluteFile()));
	        try {
	            //� ����� ��������� ��������� ����
	            String s;int i = 0;
	            while ((s = in.readLine()) != null) {
	            	if(i==lineNumber){
	                sb.append(s);
	                sb.append("\r\n");
	                break;}
	                i++;
	            }
	        } finally {
	            //������� ����
	            in.close();
	        }
	        
		}catch(IOException e) {
	        throw new RuntimeException(e);
	    }
		return sb.toString();
	}
}

