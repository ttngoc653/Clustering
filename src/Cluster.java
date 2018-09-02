import java.util.ArrayList;
import java.util.List;

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
		System.out.println("Start running with " + num_cluster + " clusters");
		this.num_cluster = num_cluster;
		this.centeres = new ArrayList<>();
		this.groups = new ArrayList<>();
		Integer num_run = 1;
		
		for (int i = 0; i < num_cluster; i++) {
			this.centeres.add(data_file.getData().get(i));
			this.groups.add(new ClusterItem());
			this.groups.get(i).addItem(data_file.getData().get(i));
		}
		do {
			System.out.println("\tStart counting " + num_run + " times");
			for (int i = (num_run == 1 ? num_cluster : 0); i < data_file.getData().size(); i++) {
				this.groups.get(chooseGroup(centeres, data_file.getData().get(i))).addItem(data_file.getData().get(i));;
			}
			if(equalNumberValueDifferentAllGroup()) break;
			else if(equalMeanGroupsAfterBefore()) break;
			else {
				this.centeres.clear();
				for (int i = 0; i < num_cluster; i++) {
					this.centeres.add(this.groups.get(i).mean());
				}
				removeAllItemInGroups();
				num_run++;
			}
		} while (true);
		
		System.out.println("Done with 10 subgroups!");
	}
	private boolean equalMeanGroupsAfterBefore() {
		for (int i = 0; i < num_cluster; i++) {
			if(!this.groups.get(i).mean().equals(this.centeres.get(i))) return false;
		}
		return true;
	}
	public Double SSE() {
		System.out.println("\tCounting SSE...");
		Double d_re = 0D;
		for (int i = 0; i < num_cluster; i++) // xet tung nhom cluster
		for (int j = 0; j < this.groups.get(i).getValues().size(); j++) // xet tung mau du lieu trong nhom
		for (int j2 = 0; j2 < groups.get(0).getValues().get(0).split(",").length; j2++) // xet tung gia tri trong mau du lieu  
			d_re += Math.pow(Double.valueOf(this.groups.get(i).getValues().get(j).split(",")[j2]) - Double.valueOf(this.groups.get(i).mean().split(",")[j2]), 2D);	
		
		return d_re;
	}
}
