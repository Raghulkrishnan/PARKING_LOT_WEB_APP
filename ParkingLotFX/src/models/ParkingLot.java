package models;

public class ParkingLot {
	int bid;
	String lot;
	String level;
  
	public int getBookingid() {
		return bid;
	}
	public void setBookingid(int bid) {
		this.bid = bid;
	}
	public String getSlot() {
		return lot;
	}
	public void setSlot(String lot) {
		this.lot = lot;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}