package mvc.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconn {
	
	private Connection conn;
	private String url = "jdbc:mysql://127.0.0.1/chan?serverTimezone=UTC";
	private String user = "root";
	private String password = "1234";
	
	
	public Connection getConnetcion() {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);
		}catch(Exception e) {
			e.printStackTrace();
		}
	 //System.out.println("객체생성확인==>"+conn);	
    return conn;  //연결객체가 생겨났을때의 객체정보를 담고있는 객체참조변수
                  //null값이면 연결이 되지 않았다라는 뜻
	
	}

}
