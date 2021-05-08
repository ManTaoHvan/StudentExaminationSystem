package com.itstar.exam.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.itstar.exam.domain.Item;//要导入自己的包
import com.itstar.exam.util.DbUtil;//要导入自己的包
import com.itstar.exam.util.ResultSetHandler;
//itemDao(item Data Access Object )题目对象
public class ItemDao {
	public static void main(String[] args) {
		ItemDao it=new ItemDao();
		//测试查看所有题目
//		List<Item> res= it.select();
//		for (Item i : res) {
//			System.out.println(i);
//		}
		//测试查看指定题目
//		System.out.println(it.get(8)); 
		//测试查看题目
//	    System.out.println(it.getCount());
		 
	}
	
//查询t_items表中的所有题目
	public List<Item> select() {
		List<Item> items = new ArrayList<Item>();
		DbUtil.executeQuery("select * from t_items",new Object[]{}, new ResultSetHandler() {
			@Override
			public void handle(ResultSet res) throws SQLException {
				// TODO Auto-generated method stub
				 
					while (res.next()) {
						int id = res.getInt(1);
						String question = res.getString(2);
						String optionA = res.getString(3);
						String optionB = res.getString(4);
						String optionC = res.getString(5);
						String optionD = res.getString(6);
						String answer = res.getString(7);
						int  score=res.getInt(8);
						Item item = new Item(id, question, optionA,optionB, optionC, optionD, answer,  score);
						items.add(item);
						}
			}
		});
		return items;
	}
//根据编号查询指定的题目
	public Item get(int id) {
		Item item = new Item();
		DbUtil.executeQuery("select * from t_items where id = ?",new Object[]{id},new ResultSetHandler(){
			@Override
			public void handle(ResultSet res) throws SQLException {
				// TODO Auto-generated method stub
				if (res.next()) {
					int no = res.getInt(1);
					String question = res.getString(2);
					String optionA = res.getString(3);
					String optionB = res.getString(4);
					String optionC = res.getString(5);
					String optionD = res.getString(6);
					String answer = res.getString(7);
					int  score=res.getInt(8);
					item.setId(no);
					item.setQuestion(question);
					item.setOptionA(optionA);
					item.setOptionB(optionB);
					item.setOptionC(optionC);
					item.setOptionD(optionD);
					item.setAnswer(answer);
					item.setScore(score);
					}
			}
		});
	return item;
	}
//根据编号修改指定的题目,选项不可改。
	public void set(String str,int id) {
		DbUtil.executeUpdate("update  t_items set question=? where id=?",new Object[]{str,id} );
	}
	
//查询数据库中题目的数量
	public int getCount(){
		//注意：这里不可以使用变量来接受一个返回值，因为下面是匿名内部类，是不可以直接访问的，所以使用一个数组
		int[] count = new int[1];
		DbUtil.executeQuery("select count(*) from t_items",new Object[]{},new ResultSetHandler(){
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
	/*查询所有题目的方法二(这种方法比较麻烦，不过适合初学者看，一般不用这个方法)
	 * 所有题目(t_items表的一行数据，作为一个对象)放在在list集合里面，也就是所有对象的集合
	 * public List<Item> select(){
		 List<Item> itemes=new ArrayList<Item>();
		 Connection con=null;
		 Statement sta=null;
		 ResultSet res=null;
		 con = DbUtil.getCon();
		 try {
			sta=con.createStatement();
			res=sta.executeQuery("select * from t_items");
			while(res.next()){
				int id = res.getInt(1);
				String question = res.getString(2);
				String optionA = res.getString(3);
				String optionB = res.getString(4);
				String optionC = res.getString(5);
				String optionD = res.getString(6);
				String answer = res.getString(7);
				int  score=res.getInt(8);
				Item item =new Item(id, question, optionA, optionB, optionC, optionD, answer, score );
				itemes.add(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		 finally{
			 DbUtil.closeResultSet(res);
			 DbUtil.closeSta(sta);
			 DbUtil.closeCon(con);
		 }
		 return itemes;
	}
	*/
	
	/*通过编号程序指定题目方法二(这种方法比较麻烦，不过适合初学者看，一般不用这个方法)
	 * public Item get(int id){
		Item item =new Item();
		 Connection con=null;
		 Statement sta=null;
		 ResultSet res=null;
		 con = DbUtil.getCon();
		 try {
			sta=con.createStatement();
			res=sta.executeQuery("select * from t_items where id="+id);
			if(res.next()){
				int no = res.getInt(1);
				String question = res.getString(2);
				String optionA = res.getString(3);
				String optionB = res.getString(4);
				String optionC = res.getString(5);
				String optionD = res.getString(6);
				String answer = res.getString(7);
				int  score=res.getInt(8);
				item.setId(no);
				item.setQuestion(question);
				item.setOptionA(optionA);
				item.setOptionB(optionB);
				item.setOptionC(optionC);
				item.setOptionD(optionD);	
				item.setScore(score);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		 finally{
			 DbUtil.closeResultSet(res);
			 DbUtil.closeSta(sta);
			 DbUtil.closeCon(con);
		 } 
		return item;
	}
	*/
	
