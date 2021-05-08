package com.itstar.exam.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itstar.exam.domain.Answer;
import com.itstar.exam.util.DbUtil;
import com.itstar.exam.util.ResultSetHandler;

public class AnswerDao {
	
	public static void main(String[] args) {
		AnswerDao a=new AnswerDao();
		//查看自己做的答案
//		for (Answer i : a.select()) {
//			System.out.println(i);
//		}
		 
	}
	
	
//查询t_answers表中的所有答案
	public List<Answer> select() {
		List<Answer> answers = new ArrayList<Answer>();
		DbUtil.executeQuery("select * from t_answers order by i_id asc", new Object[]{},new ResultSetHandler() {
		@Override
		public void handle(ResultSet res) throws SQLException {
			// TODO Auto-generated method stub
			while (res.next()) {
				int id = res.getInt(1);
				String an = res.getString(2);
				int itemId = res.getInt(3);
				Answer answer = new Answer(id, an, itemId);
				answers.add(answer);
				}
		}
		});
		return answers;
		}
//	保存考生提交的答案
	public void save(Answer answer) {
		DbUtil.executeUpdate(
		"insert into t_answers (answer, i_id) values(?,?)",new Object[] { answer.getAnswer(), answer.getItemId() });
		}
//	保存考生提交的答案或修改
	public void saveOrUpdate(Answer answer){
		//判断是否是头一次做答，还是修改、
		//从题库里面拿出空数据做出一个对象，这个对象的谢谢是有默认值的，int类型默认为0，string默认为null
//		answerInDb答案是否在数据库
		Answer answerInDb =getByItemId(answer.getItemId());
//		getAnswer()返回的是字符串类型的答案
		if(answerInDb.getAnswer() ==null){
			//保存答案
			save(answer);
		}else {
			//修改答案
			update(answer);
		}
	}
//	更新考生提交的答案
	public void update(Answer answer) {
		DbUtil.executeUpdate("update t_answers set answer = ? where i_id = ?",
		new Object[] { answer.getAnswer(), answer.getItemId() });
		}
//	删除所有的答案
	public void delete() {
		DbUtil.executeUpdate("delete from t_answers", new Object[] {});
		}
//	通过题号查询考生提交的答案
	public Answer getByItemId(int itemId) {
		Answer answer = new Answer();
		DbUtil.executeQuery("select * from t_answers where i_id = ?",
		new Object[] { itemId }, new ResultSetHandler() {
		@Override
		public void handle(ResultSet res) throws SQLException {
		if (res.next()) {
		int id = res.getInt(1);
		String an = res.getString(2);
		int no = res.getInt(3);
		answer.setId(id);
		answer.setAnswer(an);
		answer.setItemId(no);
		}
		}
		});
		return answer;
		}
	//查询数据库中已的答题目的数量
		public int getCount(){
			//注意：这里不可以使用变量来接受一个返回值，因为下面是匿名内部类，是不可以直接访问的，所以使用一个数组
			int[] count = new int[1];
			DbUtil.executeQuery("select count(*) from t_answers",new Object[]{},new ResultSetHandler(){
				@Override
				public void handle(ResultSet res) throws SQLException {
					// TODO Auto-generated method stub
					if(res.next()){
						count[0] = res.getInt(1);
						}
				}
			});
			return count[0];
			}
		 
 
	
}
