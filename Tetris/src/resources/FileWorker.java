package resources;

import java.io.*;
import java.util.ArrayList;

public class FileWorker {
	/**
	 * Статический метод, запишит строку в файл,
	 * если файла нет то создаст 
	 * @param fileName - путь к файлу.
	 * @param text - текст который будет записан в файл.
	 */
	public static void write(String fileName, String text)
	{
		//запись в файл

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
	 * Статический метод, запишит строку в файл,
	 * если файла нет то создаст 
	 * @param fileName - путь к файлу.
	 * @param text - текст который будет записан в файл.
	 */
	public static void writeTab(String fileName, ArrayList<String> list)
	{
		//запись в файл
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
	 * Прочтет файл в строку
	 * @param fileName - путь к читаемому файлу.
	 * @return - String - весь текст что был в файле
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
	            //В цикле построчно считываем файл
	            String s;
	            while ((s = in.readLine()) != null) {
	            	
	                sb.append(s);
	                sb.append("\r\n");
	                
	            }
	        } finally {
	            //закрыть файл
	            in.close();
	        }
	        
		}catch(IOException e) {
	        throw new RuntimeException(e);
	    }
		return sb.toString();
	}
	/**
	 * Прочитает файл по строчно в массив строк.
	 * @param fileName - путь к читаемому файлу.
	 * @return -  ArrayList<String> - объект массив строк
	 * @throws FileNotFoundException
	 */
	public static ArrayList<String>  readTab(String fileName) throws FileNotFoundException {
		System.out.println();
		//вернет файл в виде масива строк
		 ArrayList<String> list = new ArrayList<String>();
		File myfile = new File(fileName);
		exists(fileName);
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(myfile.getAbsoluteFile()));
	        try {
	            //В цикле построчно считываем файл
	            String s;
	            int i=0;
	            while ((s = in.readLine()) != null) {
	            	
	                list.add(i, s);
	                i++;
	                //sb.append("\r\n");
	            }
	        } finally {
	            //закрыть файл
	            in.close();
	        }
	        
		}catch(IOException e) {
	        throw new RuntimeException(e);
	    }
		return list;
	}
	/** Внутренний метод класса
	 * Бросит исключение если файла нет.
	 * @param fileName - путь к читаемому файлу.
	 * @throws FileNotFoundException
	 */
	private static void exists(String fileName) throws FileNotFoundException {
		//проверка на сущ-ние файла
	    File file = new File(fileName);
	    if (!file.exists()){
	        throw new FileNotFoundException(file.getAbsolutePath());
	    }
 	}
	/**
	 * Проверяет существует ли файл.
	 * @param fileName
	 * @return int = -1 - если файла не существует и 0 если существует
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
		//дописывание в конец файла
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
		//вернет строку с номером lineNumber
		StringBuilder sb = new StringBuilder();
		File myfile = new File(fileName);
		exists(fileName);
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(myfile.getAbsoluteFile()));
	        try {
	            //В цикле построчно считываем файл
	            String s;int i = 0;
	            while ((s = in.readLine()) != null) {
	            	if(i==lineNumber){
	                sb.append(s);
	                sb.append("\r\n");
	                break;}
	                i++;
	            }
	        } finally {
	            //закрыть файл
	            in.close();
	        }
	        
		}catch(IOException e) {
	        throw new RuntimeException(e);
	    }
		return sb.toString();
	}
}

