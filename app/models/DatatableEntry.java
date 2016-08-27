package models;

import java.util.List;

public class DatatableEntry {
	
	private List<Column> columns;
	private List<List<String>> dataset;
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List<List<String>> getDataset() {
		return dataset;
	}
	public void setDataset(List<List<String>> dataset) {
		this.dataset = dataset;
	}

	

	

}
