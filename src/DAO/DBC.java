package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBC {
	public static Connection DBConnect() {
		
		// DB 접속에 필요한 정보를 저장하기 위한 Connection 타입 객체 [con] 선언
		Connection con = null;
		
		// 변수를 먼저 선언하고 con 객체에 값을 대입해주면 자동으로 url, user, password가 대입됨
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "JAY";
		String password = "1111";
		
		// [1] Class 파일 자체에 이름을 입력
		// [2] con 객체에 드라이버 매니저로 url, user, password 값 받기
		try {
			// 오라클 데이터베이스 드라이버
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 오라클 데이터베이스 접속정보
			con = DriverManager.getConnection(url, user, password);
			
		} catch (ClassNotFoundException e) {
			// ClassNotFoundException : 드라이버 오류 시
			
			// 잘못 되었다는 문구 출력 후 오류 경로 출력
			System.out.println("DB접속 실패 : 드라이버 오류");
			e.printStackTrace();
		} catch (SQLException e) {
			// SQLException : 접속 정보 오류 시
			
			// 잘못 되었다는 문구 출력 후 오류 경로 출력
			System.out.println("DB접속 실패 : 접속정보 오류");
			e.printStackTrace();
		}
		// void 타입이 아니기 때문에 Connection 타입의 return값 필요
		return con;
	}
}
