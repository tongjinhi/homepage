public class BoardData {
	String boardId;
	String userId;
	String name;
	String title;
	String content;
	String date;
	
	// Constructor
	public BoardData() {}
	public BoardData(String p_bId, 
			         String p_uId, 
			         String p_name, 
			         String p_title, 
			         String p_content, 
			         String p_date) {
		
		this.boardId = p_bId;
		this.userId  = p_uId;
		this.name    = p_name;
		this.title   = p_title;
		this.content = p_content;
		this.date    = p_date;
	}
	
	// Method
	public String getBoardId() {
		return boardId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getDate() {
		return date;
	}
}
