package model;

public class Coffee {
      String cofName;
      int supId;
      int total;
      int sales;
      double price;
    public Coffee() {}
	public Coffee(String cofName, int supId, int total, int sales, double price) {
		
		this.cofName = cofName;
		this.supId = supId;
		this.total = total;
		this.sales = sales;
		this.price = price;
	}
	public String getCofName() {
		return cofName;
	}
	public void setCofName(String cofName) {
		this.cofName = cofName;
	}
	public int getSupId() {
		return supId;
	}
	public void setSupId(int supId) {
		this.supId = supId;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
