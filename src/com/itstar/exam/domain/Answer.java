package com.itstar.exam.domain;

public class Answer {
	 
		@Override
	public String toString() {
		return  "答案:"+answer+" 题号:"+itemId;
	}
		private int id;
		private String answer;
		private int itemId;
		public Answer() {
		super();
		}
		public Answer(int id, String answer, int itemId) {
		super();
		this.id = id;
		this.answer = answer;
		this.itemId = itemId;
		}
		public int getId() {
		return id;
		}
		public void setId(int id) {
		this.id = id;
		}
		public String getAnswer() {
		return answer;
		}
		public void setAnswer(String answer) {
		this.answer = answer;
		}
		public int getItemId() {
		return itemId;
		}
		public void setItemId(int itemId) {
		this.itemId = itemId;
		}
	 
}
