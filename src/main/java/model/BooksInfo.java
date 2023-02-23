package model;

import model.thebookdb.VolumeInfo;

public class BooksInfo {
	private String intitle;
	private String inauthor;
	private String inpublisher;
	private String subject;
	private String isbn;
	private String lccn;
	private String oclc;
	
	
	
	public BooksInfo(String intitle, String inauthor, String inpublisher, String subject, String isbn, String lccn,
			String oclc) {
		super();
		this.intitle = intitle;
		this.inauthor = inauthor;
		this.inpublisher = inpublisher;
		this.subject = subject;
		this.isbn = isbn;
		this.lccn = lccn;
		this.oclc = oclc;
	}


	
	
	
	
	

	





	public String getIntitle() {
		return intitle;
	}








	public void setIntitle(String intitle) {
		this.intitle = intitle;
	}








	public String getInauthor() {
		return inauthor;
	}








	public void setInauthor(String inauthor) {
		this.inauthor = inauthor;
	}








	public String getInpublisher() {
		return inpublisher;
	}








	public void setInpublisher(String inpublisher) {
		this.inpublisher = inpublisher;
	}








	public String getSubject() {
		return subject;
	}








	public void setSubject(String subject) {
		this.subject = subject;
	}








	public String getIsbn() {
		return isbn;
	}








	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}








	public String getLccn() {
		return lccn;
	}








	public void setLccn(String lccn) {
		this.lccn = lccn;
	}








	public String getOclc() {
		return oclc;
	}








	public void setOclc(String oclc) {
		this.oclc = oclc;
	}








	@Override
	public String toString() {
		return"BookInfo{" +
	"intitle='" + intitle + "'\n" + 
			", inauthor= '" + inauthor + "'\n" +
	", inpublisher='" + inpublisher + "'\n" +
			", subject='" + subject + "'\n" +
			", isbn ='" + isbn + "'\n" +
			", lccn ='" + lccn + "'\n" +
			", oclc ='" + oclc + "'\n" +
	'}';
	

	
	
	
	
	
	}
	
	
	
}
