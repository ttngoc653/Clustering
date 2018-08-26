import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataFile {
	private List<String> column_name;
	private List<String> data;
	public List<String> getColumnName() {
		return column_name;
	}
	public void setColumnName(List<String> column_name) {
		this.column_name = column_name;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	@SuppressWarnings({ "resource", "unused" })
	public DataFile(String file_in_name) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file_in_name));
			String _data = reader.readLine();
			
			column_name = new ArrayList<>();
			for (String i_str : _data.split(","))
				column_name.add(i_str);
			
			data = new ArrayList<>();
			while((_data = reader.readLine()) != null)
				data.add(_data);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public DataFile() {
		column_name = new ArrayList<>();
		data = new ArrayList<>();
	}
}
