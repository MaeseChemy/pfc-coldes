package es.uc3m.coldes.model;

public class Design {

	private String username;
	private byte[] designcontent;
	private String designname;
	
	public Design() {
	}

	public Design(String username, byte[] designcontent, String designname) {
		super();
		this.username = username;
		this.designcontent = designcontent;
		this.designname = designname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getDesigncontent() {
		return designcontent;
	}

	public void setDesigncontent(byte[] designcontent) {
		this.designcontent = designcontent;
	}

	public String getDesignname() {
		return designname;
	}

	public void setDesignname(String designname) {
		this.designname = designname;
	}

}
