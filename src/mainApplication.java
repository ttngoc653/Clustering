import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mainApplication {
	private static void outputClusterer(Cluster _cluster, String _file_in_name) {
	     try {
			FileWriter fw = new FileWriter(new File(_file_in_name));
			fw.write("Number of iterations: " + _cluster.getNumCluster() + "\n");
			fw.write("Within cluster sum of squared errors: " + _cluster.SSE() + "\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
