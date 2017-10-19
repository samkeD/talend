package routines;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
public class CompareRoutine {

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
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	public static void helloExample(String message) {
		if (message == null) {
			message = "World"; //$NON-NLS-1$
		}
		System.out.println("Hello " + message + " !"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String [] getColumnNames (String header){
		String row = header.substring(header.indexOf('[') + 1, header.lastIndexOf(']'));
		//to be used to temporarily store row string while extracting column names from the original row string
		String tempRow;
		//array to be used to store all column names
		String columns[] = new String[row.split(",").length];
		for (int i = 0; i < columns.length; i++) {
			//gets single column name
			columns[i] = row.substring(0, row.indexOf('='));
			tempRow = row.substring(row.indexOf(',') + 1);
			row = tempRow;
		}
		return columns;

	}
	public static StringBuilder writeFileHeading(String header){
		String [] columnNames = getColumnNames(header);
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("RECORD NO"+COMMA_DELIMITER);
		for(int i=1;i<columnNames.length;i++){
			if(!columnNames[i].equals(columnNames[columnNames.length-1]))
			stringBuilder.append("S_"+columnNames[i]+COMMA_DELIMITER+ "T_"+columnNames[i]+COMMA_DELIMITER);
			else
				stringBuilder.append("S_"+columnNames[i]+COMMA_DELIMITER+ "T_"+columnNames[i]);

		}
		stringBuilder.append(NEW_LINE_SEPARATOR); //new data must always be on a new line
		return stringBuilder;
	}

	public static List createLists(String header){
		List<List<String>> columnsNameList = new ArrayList<>();

		//gets a single row of data with (column names)keys and values (column data)
		String row = header.substring(header.indexOf('[') + 1, header.lastIndexOf(']'));
		//array to be used to store all column names
		String columns[] = new String[row.split(",").length];
		for (int i = 0; i < columns.length; i++) {
			//gets single column name
			columnsNameList.add(new ArrayList<String>());

		}
		// System.out.println(columnsNameList.size());
		//System.out.println(columnsNameList.get(0));
		return columnsNameList;
	}

	public static void populateLists(String header, List<List<String>>  list){
		//gets a single row of data with (column names)keys and values (column data)
		//List<List<String>>  list = new ArrayList<>();
		String row = header.substring(header.indexOf('[') + 1, header.lastIndexOf(']'));
		row+=",";
		//to be used to temporarily store row string while extracting column names from the original row string
		String tempRow;

		for (int i = 0; i < list.size(); i++) {
			list.get(i).add(row.substring(row.indexOf('=') +1,row.indexOf(',')));
			tempRow = row.substring(row.indexOf(',') + 1);
			row = tempRow;
		}

	}

	public static StringBuilder getDataDifferences(List<List<String>>  list) {
		int count = 0;
		StringBuilder differencesStringBuilder = new StringBuilder("");

		//adds record numbers to the file
		List<String> fieldDifferences = new ArrayList<>();
		for (int k = 0; k < list.get(0).size() / 2; k++)
				fieldDifferences.add(list.get(0).get(k)+COMMA_DELIMITER);

		//gets the rest of the lists differences and add them to the existing difference list
		for (int i = 1; i < list.size(); i++) {
			List<String> data = new ArrayList<>(list.get(i));
			for (int k = 0, j = data.size() / 2; k < data.size() / 2 && j < data.size(); k++, j++) {
				/* - the if statements will append the two differing information from the two sources to differences
               - the else statements will just append the empty string if no differences are found,
                  this is done to ensure each difference is listed under the correct column name
				 */
				
				String tempFieldDifference = fieldDifferences.get(k);
				if(i != list.size()-1){
					if (!data.get(k).equals(data.get(j))) {
						fieldDifferences.set(k,tempFieldDifference+ data.get(j)+COMMA_DELIMITER+ data.get(k)+COMMA_DELIMITER);
						count++;
					}
					else
						fieldDifferences.set(k,tempFieldDifference+COMMA_DELIMITER+COMMA_DELIMITER);
				}
				
				else{
					if (!data.get(k).equals(data.get(j))) {
						fieldDifferences.set(k,tempFieldDifference+ data.get(j)+COMMA_DELIMITER+ data.get(k));
						count++;
					}
					else
						fieldDifferences.set(k,tempFieldDifference+COMMA_DELIMITER);
				}

			}
		}
		//add all differences to differencesStringBuilder
		for (String difference : fieldDifferences) {
			differencesStringBuilder.append(difference);
			differencesStringBuilder.append(NEW_LINE_SEPARATOR);
		}
		differencesStringBuilder.append("count "+count);

		return differencesStringBuilder;
	}

	public static void writeDifferencesFile(String filename,StringBuilder differencesStringBuilder){
		StringBuilder stringBuilder = new StringBuilder(differencesStringBuilder.substring(differencesStringBuilder.indexOf("count")));
		stringBuilder.delete(0,6);
		Integer count = new Integer(Integer.parseInt(stringBuilder.toString()));
		 differencesStringBuilder.delete(differencesStringBuilder.indexOf("count"),differencesStringBuilder.length());
		if(count>0){
			differencesStringBuilder.append("------------------------------------------------------");
			differencesStringBuilder.append(NEW_LINE_SEPARATOR);
			differencesStringBuilder.append("END OF FILE");
			differencesStringBuilder.append(NEW_LINE_SEPARATOR);
			differencesStringBuilder.append(count+" DIFFERENCES WERE FOUND BETWEEN THE TWO SOURCES");

			try (FileWriter fileWriter = new FileWriter(filename);
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

				bufferedWriter.write(differencesStringBuilder.toString());

				System.out.println("Done writing to file "+count+" DIFFERENCES WERE FOUND BETWEEN THE TWO SOURCES");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println(count +" DIFFERENCES FOUND BETWEEN THE TWO SOURCES");
	}

}
