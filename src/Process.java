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
		for (int i = 0; i < values.size(); i++) {
			int j = i - 1;
			for (;values.get(i).equals(values.get(j)) || j >= 0; j--) ;
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
		for (String i_str : values) {
			d_temp = 0D;
			for (String i_i_str : i_str.split(",")) {
				d_temp += Double.valueOf(i_i_str);
			}
			str_return += "," + String.valueOf(d_temp / values.size());
		}
		return str_return.substring(1);
	}
}

class Cluster {
	private Integer num_cluster;
	private List<String> centeres;
	private List<ClusterItem> groups;
	public Integer getNumCluster() {
		return num_cluster;
	}
	public void setNumCluster(Integer num_cluster) {
		this.num_cluster = num_cluster;
	}
	public List<String> getCenteres() {
		return centeres;
	}
	public void setCenteres(List<String> index_centeres) {
		this.centeres = index_centeres;
	}
	public List<ClusterItem> getGroups() {
		return groups;
	}
	public void setGroups(List<ClusterItem> groups) {
		this.groups = groups;
	}
	
	private Boolean equalNumberValueDifferentAllGroup() {
		for (int i = 0; i < num_cluster - 1; i++)
			for (int j = 0; j < num_cluster; j++)
				if(Math.abs(groups.get(i).numberItemDifferent() - groups.get(j).numberItemDifferent()) > 1) return false;
			
		return true;
	}
	private Double Euclide(String center, String value) {
		Double d_return = 0D;
		for (int i = 0; i < center.split(",").length; i++)
			d_return += Math.pow(Double.parseDouble(center.split(",")[i]) - Double.parseDouble(value.split(",")[i]), 2D);
		
		return Math.sqrt(d_return);
	}
	private int chooseGroup(List<String> centers, String value) {
		Integer min = 0;
		Double length_min = Euclide(centers.get(0), value);
		for (int i = 1; i < centers.size(); i++) {
			if(length_min > Euclide(centers.get(i), value)) {
				min = i;
				length_min = Euclide(centers.get(i), value);
			}	
		}
		
		return min;
	}
	private void removeAllItemInGroups() {
		for (int i = 0; i < this.groups.size(); i++) {
			this.groups.get(i).removeAll();
		}
	}
	public Cluster(DataFile data_file, Integer num_cluster) {
		this.num_cluster = num_cluster;
		this.centeres = new ArrayList<>();
		this.groups = new ArrayList<>();
		
		for (int i = 0; i < num_cluster; i++) {
			this.centeres.add(data_file.getData().get(i));
			this.groups.add(new ClusterItem());
		}
		do {
			for (int i = 0; i < data_file.getData().size(); i++) {
				this.groups.get(chooseGroup(centeres, data_file.getData().get(i))).addItem(data_file.getData().get(i));;
			}
			if(equalNumberValueDifferentAllGroup()) return;
			else {
				removeAllItemInGroups();
				this.centeres.clear();
				for (int i = 0; i < num_cluster; i++) {
					this.centeres.add(this.groups.get(i).mean());
				}
			}
		} while (true);
	}
	public Double SSE() {
		Double d_re = 0D;
		for (int i = 0; i < num_cluster; i++) // xet tung nhom cluster
		for (int j = 0; j < this.groups.get(i).getValues().size(); j++) // xet tung mau du lieu trong nhom
		for (int j2 = 0; j2 < groups.get(0).getValues().get(0).split(",").length; j2++) // xet tung gia tri trong mau du lieu  
			d_re += Math.pow(Double.valueOf(this.groups.get(i).getValues().get(j).split(",")[j2]) - Double.valueOf(this.groups.get(i).mean().split(",")[j2]), 2D);	
		
		return d_re;
	}
}
public class Process {
	public static Cluster CreateCluster(DataFile data_file, Integer cluster) {
		
		return new Cluster(data_file, cluster);
	}
}
