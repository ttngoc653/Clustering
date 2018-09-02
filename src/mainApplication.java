import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class mainApplication {
	private static void outputClusterer(FileWriter fw, DataFile _data_file, Cluster _cluster) {
	     try {
			System.out.println("Writing to file with " + _cluster.getNumCluster() + " clusters...");
			fw.write("\nNumber of iterations: " + _cluster.getNumCluster() + "\n");
			fw.write("Within cluster sum of squared errors: " + _cluster.SSE() + "\n");
			fw.write("Cluster centroids:\n");
			System.out.println("Writing to "+_cluster.getNumCluster()+"-clustered information table file.");
			fw.write("\t\t\tCluster#\n");
			fw.write(String.format("%-16s", "Attribute"));
			
			for (int i = 0; i < _cluster.getNumCluster(); i++)
				fw.write(String.format("%-6s", "(" + i + ")"));
			fw.write("\n"+String.format("%-13s", ""));
			for (int i = 0; i < _cluster.getGroups().size(); i++) {
				fw.write(String.format("%6s", _cluster.getGroups().get(i).getValues().size()));
			}
			
			fw.write("\n==========================================================================");
			
			for (int i = 0; i < _data_file.getColumnName().size(); i++) {
				fw.write("\n"+String.format("%-15s", _data_file.getColumnName().get(i)));
				for (int j = 0; j < _cluster.getNumCluster(); j++) {
					fw.write(String.format("%-6s", String.format("%.2f", Double.parseDouble(_cluster.getGroups().get(j).mean().split(",")[i]))));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void exportOnlyModelFile(String file_name_in) {
		try {
			FileWriter fw = new FileWriter(new File("model.txt"));
			DataFile _data_file = new DataFile(file_name_in);

			for (int i = 1; i <= 10; i++) {
				Cluster _cluster = new Cluster(_data_file, i); 
				outputClusterer(fw, _data_file, _cluster);
				fw.write("\n-------------------------------------------------------------------------");
				System.out.println("Completed write data with "+_cluster.getNumCluster()+"-means");
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void outputAssignments(FileWriter fw, DataFile _data_file, Cluster _cluster) throws IOException {
		for (int i = 0; i < _data_file.getColumnName().size(); i++)
			fw.write(_data_file.getColumnName().get(i)+",");
		fw.write("Cluster\n");
		
		for (int i = 0; i < _cluster.getNumCluster(); i++) {
			for (int j = 0; j < _cluster.getGroups().get(i).getValues().size(); j++) {
				fw.write(_cluster.getGroups().get(i).getValues().get(j)+","+i+"\n");
			}
		}
	}
	@SuppressWarnings("unused")
	private static void exportOnlyAssignmentsFile(String file_name_in) {
		try {
			FileWriter fw = new FileWriter(new File("assignments.csv"));
			DataFile _data_file = new DataFile(file_name_in);

			for (int i = 1; i <= 10; i++) {
				Cluster _cluster = new Cluster(_data_file, i);
				System.out.println("Assign (" + i + "-means) clusters to each data sample and output the file.");
				fw.write("Data set after splitting " + i + " clusters");
				outputAssignments(fw, _data_file, _cluster);
				System.out.println("Completed write data with "+_cluster.getNumCluster()+"-means");
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	private static void exportModelAndAssignmentsFile(String file_name_in) {
		try {
			FileWriter fw = new FileWriter(new File("model.txt"));
			FileWriter fw2 = new FileWriter(new File("assignments.csv"));
			DataFile _data_file = new DataFile(file_name_in);

			for (int i = 1; i <= 10; i++) {
				Cluster _cluster = new Cluster(_data_file, i); 
				outputClusterer(fw, _data_file, _cluster);
				fw.write("\n-------------------------------------------------------------------------");
				

				System.out.println("Assign (" + i + "-means) clusters to each data sample and output the file.");
				fw.write("Data set after splitting " + i + " clusters");
				outputAssignments(fw2, _data_file, _cluster);
				System.out.println("Completed write data with "+_cluster.getNumCluster()+"-means");
			}
			fw.close();
			fw2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void exportFileByParameters(String[] args) {
		try {
			FileWriter fw2 = new FileWriter(new File(args[2]));
			FileWriter fw = new FileWriter(new File(args[1]));
			DataFile _data_file = new DataFile(args[0]);

			Cluster _cluster = new Cluster(_data_file, Integer.valueOf(args[3])); 
			outputClusterer(fw, _data_file, _cluster);
			fw.write("\n-------------------------------------------------------------------------");
				
			System.out.println("Assign (" + args[3] + "-means) clusters to each data sample and output the file.");
			fw.write("Data set after splitting " + args[3] + " clusters");
			outputAssignments(fw2, _data_file, _cluster);
			System.out.println("Completed write data with "+_cluster.getNumCluster()+"-means");
				
			fw.close();
			fw2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isNumCluster(String x) {
		try {
			if(Integer.parseInt(x)>0) return true;	
		} catch (Exception e) {	}
		return false;
	}
	public static final void main(String[] args) {
		// exportOnlyModelFile("sessions.csv");
		// exportOnlyAssignmentsFile("sessions.csv");
		// exportModelAndAssignmentsFile("sessions.csv");
		
		if(args.length < 4 || !isNumCluster(args[3])) {
			System.out.println("Wrong parameter or parameter is not enough!");
		}else {
			exportFileByParameters(args);
		}
	}
}
