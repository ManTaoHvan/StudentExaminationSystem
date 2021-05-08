package ExamMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.itstar.exam.dao.AnswerDao;
import com.itstar.exam.dao.ItemDao;
import com.itstar.exam.domain.Answer;
import com.itstar.exam.domain.Item;

public class ExamMain {
	//用户读取用户输入的流，不必关掉，当程序终止时自动收回
//	所以把关闭流的操作换成更对象有关，思路是：随着对象的消亡而消亡。
	//对象执行完就会消亡，所以流就自动关闭
	private   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private ItemDao itemDao = new ItemDao();
	private AnswerDao answerDao = new AnswerDao();
	
	public static void main(String[] args){
	new ExamMain().testExam();
	}
	//当前系统时间
	void time(){
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat SDF=new  SimpleDateFormat("当前时间:"+"yyyy-MM-dd HH:mm:ss");
		String str=SDF.format(calendar.getTime());
		System.out.println(str);
		
	}
	public void testExam() {
		while (true) {
		// 打印主菜单. 当用户键入1, 进入考试
		System.out.println("------考试系统主菜单------");
		System.out.println("1 进入考试");
		System.out.println("2 显示上次成绩和答案");
		System.out.println("3 退出考试系统");
		time();
		// 获取用户输入的字符
		char c = getUserAction();
		if (c == '1') {
			startExam();
		} else if (c == '2') {
		System.out.println("------历史成绩及答案------");
		calcScore();	
		} else if(c == '3'){
			System.out.println("已退出系统!");
			break;
		}else {
			System.out.println("输入有误!");
		}
		}
	}
	//开始考试
	private void startExam() {
		// TODO Auto-generated method stub
 		System.out.println("------欢迎进入考试系统------");
		System.out.println("按键定义如下:");
		System.out.println("N键下一题 P键上一题 ABCD键做出选择");
		while (true) {
			System.out.print("键入N开始考试 ");
			char op = getUserAction();
			if (op == 'N') {
			// 如果用户键入N，执行while循环后续的代码
				break;
				}
			}
		//开始考试之前删除数据库的数据
		answerDao.delete();
		//当前做的第几题
		int currentNo = 1;
		//显示指定题编号
		displayItem(currentNo);
		
		//开始答题
		while(true){
			//判断用户当前输入的字符
		char ch =getUserAction();//如果这里输入出错，就会显示输入输入有误
		if(ch == 'N'){ //进入下一题
			//如果不到最后一题，那么就继续答题
			if (currentNo<itemDao.getCount()) {
				currentNo++;
				displayItem(currentNo);
			}else {
				System.out.println("当前已经是最后一题");
			}
		}else if(ch == 'P'){ //进入上一题
			if (currentNo>1) {
				currentNo--;
				displayItem(currentNo);
			}else {
				System.out.println("当前已经是第一题");
			}
		}else if(ch == 'A' || ch == 'B' || ch =='C' || ch == 'D'){
			//当用户输入ABCD时候，把用户输入的作为答案，保存到数据库中
			Answer answer =new Answer();
			answer.setAnswer(String.valueOf(ch));
			answer.setItemId(currentNo);
			answerDao.saveOrUpdate(answer);
			//每做完一题向下跳一题
			//如果是最后一题就不跳
			if (currentNo == itemDao.getCount()) {
				System.out.println("已经是最后一题 可输入F进行退出");
			}else {//如果不是最后一题，就跳下一题
				currentNo++;
				displayItem(currentNo);	
			}
		}else if (ch == 'F') {
			//结束之前判断一下是否有未做完的题目
			//查询题目的数量
			int itemCount=itemDao.getCount();
			//查询答案的数量
			int answersCount=answerDao.getCount();
			//题目数量和答案数量是否一致
			if (itemCount == answersCount) {
				System.out.println("已退出考试!");
					break;
			}
				System.out.println("题目未答完请继续答  N/P键可进行上下题目切换");	
		}
		}
	 
		
	}
	//显示正确答案和用户答案，并计算分数。
	private void calcScore() {
		// TODO Auto-generated method stub
		//查询题目正确答案
		int IAOrder=1;
		int AOrder=1;
		List<Item> items=itemDao.select(); 
		 
		//打印题目正确答案
		System.out.print("原题答案:");
		for (Item item : items) {
			System.out.print(IAOrder+"."+item.getAnswer()+" ");
			IAOrder++;
		}
		System.out.print("\n你的答案:");//调整显示美观
		 
		List<Answer> answers=answerDao.select();
		for (Answer answer : answers) {
			System.out.print(AOrder+"."+answer.getAnswer()+" ");
			AOrder++;
		}
		float score=0;
		/*
		 * 注意在这里使用answers.size()为大小，不要使用items.size();因为比如在有些情况下，
		虽然设置了强制要要求学生答题，但是有时候会特殊情况强制退出了考试也有可能，所以要保证遍历的时候不要超过答题次数。
		*/
		for (int i = 0; i < answers.size(); i++) {
			Item item =items.get(i);
			Answer answer=answers.get(i);
			
			if (  item.getAnswer().equals(answer.getAnswer())  ) {
				score=score+item.getScore();
			}
		}
		System.out.println("\n考试成绩:"+score+"分");
		
	}

	//根据指定的题号查询题目内容
	private void displayItem(int currentNo) {
		// TODO Auto-generated method stub
		// 从数据库查询出Item对象
		Item item = itemDao.get(currentNo);
		// 打印题目内容
		System.out.println(item);
		//如果用户答过次题，则就返回答案
		Answer answer=answerDao.getByItemId(currentNo);
		if (answer.getAnswer() != null ) {
//			getAnswer()；返回的字符串，由于Strign类重写了toString方法，所以不打印地址
			System.out.println("你的答案:"+answer.getAnswer());
		}
	}
 
//	 获取用户输入的字符
	private char getUserAction() {
		try {
			/*
		  readLine() 方法，读一整行，这个方法会去掉键入的回车符
		  但read()
			还要注意：
			  回车 \r  本义是光标重新回到本行开头，换行 \n  本义是光标往下一行
			  这个跟操作系统有关，在Unix内核的系统中换行就是\n，但是windows系统中换行需要\r\n都用，
			  */
		System.out.print("请输入:");
		String str = br.readLine();
		// 取第一个字符
		char c = str.charAt(0);
		while (c != -1) {
		// 转成大写字符，便于处理，关了就会都开始的一次，后面就会出现异常。
		c = Character.toUpperCase(c);
		/*
		system.in是一个字节流。所以可以转化成字符流，
		由于这里不使用Scanner的原因是因为scanner不能读取字符，
		只能读取字节。N表示下一题，P表示上一题，F为结束，
		*/
		if (c == 'A' || c == 'B' || c == 'C' || c == 'D' || c == 'N'
		|| c == 'P' || c == 'F' || c == '1'
		|| c == '2'|| c == '3') {
			//在这里，如果下面使用了final语句块，这先会执行final，在执行return。
			//还有注意为什么这里不关流，
			
		return c;
		} else {
		System.out.println("输入有误 只能输入字符A/B/C/D/N/P/F/Y/1/2中的单字符");
		}
		//如果没有读到正确的字符，继续读取输入
		System.out.print("请重新正确输入:");
		str = br.readLine();//按行读取，在取出第1个字符。
		c = str.charAt(0);
	//	这里读了，不能在这里关闭流，这不好的，所以把关闭流的操作换成更对象有关，思路是：随着对象的消亡而消亡。
		}
		} catch (IOException e) {
		e.printStackTrace();
		}
		return 0;//这里返回其实没有什么作用
	}
	 
	
}
