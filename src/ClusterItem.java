import java.util.ArrayList;
import java.util.List;

class ClusterItem {
	private List<String> values;

	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	public void addItem(String item) {
		this.values.add(item);
	}
	public Integer numberItemDifferent() {
		Integer i_number = 0;
		if(values.size() == 0) return 0;
		if(values.size() == 1) return 1;
		for (int i = 1; i < values.size(); i++) {
			int j = i - 1;
			for (; j >= 0 && !values.get(i<0?0:i).equals(values.get(j<0?0:j)); j--) ;
			if(j<0) i_number++;
		}
		return i_number;
	}
	public void removeAll() {
		this.values.clear();
	}
	public ClusterItem() {
		this.values = new ArrayList<>();
	}
	public String mean() {
		Double d_temp = 0D;
		String str_return = "";
		
		for (int i = 0; i < values.get(0).split(",").length; i++) {
			d_temp = 0D;
			for (int j = 0; j <values.size(); j++) {
				d_temp += Double.valueOf(values.get(j).split(",")[i]);
			}
			str_return += "," + String.valueOf(d_temp / values.size());
		}
		return str_return.substring(1);
	}
}