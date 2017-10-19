package routines;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * user specification: the function's comment should contain keys as follows: 1. write about the function's comment.but
 * it must be before the "{talendTypes}" key.
 * 
 * 2. {talendTypes} 's value must be talend Type, it is required . its value should be one of: String, char | Character,
 * long | Long, int | Integer, boolean | Boolean, byte | Byte, Date, double | Double, float | Float, Object, short |
 * Short
 * 
 * 3. {Category} define a category for the Function. it is required. its value is user-defined .
 * 
 * 4. {param} 's format is: {param} <type>[(<default value or closed list values>)] <name>[ : <comment>]
 * 
 * <type> 's value should be one of: string, int, list, double, object, boolean, long, char, date. <name>'s value is the
 * Function's parameter name. the {param} is optional. so if you the Function without the parameters. the {param} don't
 * added. you can have many parameters for the Function.
 * 
 * 5. {example} gives a example for the Function. it is optional.
 */
public class CompareData {

    /**
     * helloExample: not return value, only print "hello" + message.
     * 
     * 
     * {talendTypes} String
     * 
     * {Category} User Defined
     * 
     * {param} string("world") input: The string need to be printed.
     * 
     * {example} helloExemple("world") # hello world !.
     */
    public static void helloExample(String message) {
        if (message == null) {
            message = "World"; //$NON-NLS-1$
        }
        System.out.println("Hello " + message + " !"); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public static List getDBData(){
        List<String> dbData = new ArrayList<>();
        dbData.add("Name, Surname,Age");
        dbData.add("Gabage, test, 278");
        dbData.add("tests, kkk,12");
        dbData.add("file, jjj, 2");
        dbData.add("java, datas,30");
        return dbData;
    }
    
    public static List getFileData(String filename) throws IOException{
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        List<String> list = new ArrayList<>();

        try {
            fileReader = new FileReader(filename);
            bufferedReader = new BufferedReader(fileReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                list.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
            fileReader.close();
        }
        return list;
    }

}
