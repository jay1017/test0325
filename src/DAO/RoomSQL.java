package DAO;

import java.sql.*;

import DTO.*;

public class RoomSQL {
	// 1. 객체 생성
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 2. 메소드 생성
	// 1) DB접속
	public void connect() {
		con = DBC.DBConnect();
	}

	// 2) DB접속 해제
	public void conClose() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 3-1) 회원 가입 : 고객
	public void insertCustomer(Customer customer) {
		String sql = "INSERT INTO CUSTOMER VALUES(?, ?, ?, ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customer.getcId());
			pstmt.setString(2, customer.getcPw());
			pstmt.setString(3, customer.getcName());
			pstmt.setString(4, customer.getcPhone());

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("회원가입에 성공했습니다.");
			} else {
				System.out.println("회원가입에 실패했습니다. 다시 시도 해 주세요.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3-2) 회원 가입 : 점주
	public void insertManager(Manager manager) {
		String sql = "INSERT INTO MANAGER VALUES(?, ?, ?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, manager.getBranch());
			pstmt.setString(2, manager.getmId());
			pstmt.setString(3, manager.getmPw());
			pstmt.setString(4, manager.getmName());
			pstmt.setString(5, manager.getmPhone());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("회원가입에 성공하였습니다.");
			} else {
				System.out.println("회원가입에 실패했습니다. 다시 시도 해 주세요.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 4-1) 아이디, 비밀번호 확인 : 고객
	public boolean idCheck(String cId, String cPw) {
		boolean check = false;

		String sql = "SELECT CID FROM CUSTOMER WHERE CID = ? AND CPW = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cId);
			pstmt.setString(2, cPw);

			rs = pstmt.executeQuery();

			check = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	// 4-2) 아이디 비밀번호 확인 : 점주
	public boolean idCheck2(String mId, String mPw) {
		boolean check = false;

		String sql = "SELECT MID FROM MANAGER WHERE MID = ? AND MPW = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mId);
			pstmt.setString(2, mPw);

			rs = pstmt.executeQuery();

			check = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	// 5-1) 테마 등록 전 로그인 한 사람의 지점 갖고오기
	public String selectBranch(String mId) {
		String branch = null;
		String sql = "SELECT BRANCH FROM MANAGER WHERE MID = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				branch = rs.getString(1);
			}
			
		} catch(SQLException e) {
			
		}
		
		return branch;
	}
	
	// 5-2) 테마 등록
	public void insertRoom(Room room, String branch) {
		String sql = "INSERT INTO ROOM VALUES(?, ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, room.getRoom());
			pstmt.setString(2, branch);

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("테마 등록이 완료 되었습니다!");
			} else {
				System.out.println("테마 등록이 실패 하였습니다. 다시 입력 해 주세요.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 6) 테마 조회
	public void selectRoom(Room room, String branch) {
		String sql = "SELECT * FROM ROOM WHERE BRANCH = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, branch);

			rs = pstmt.executeQuery();
			System.out.println("=============================");
			while (rs.next()) {
				System.out.println("테마 이름 : " + rs.getString(1));
			}
			System.out.println("=============================");
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 7-1) 테마 명 일치/불일치 확인
	public boolean checkTheme(String theme) {
		boolean checkTheme = false;
		String sql = "SELECT * FROM ROOM WHERE RNAME = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, theme);

			rs = pstmt.executeQuery();

			checkTheme = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return checkTheme;
	}

	// 7-2) 일치하면 실행하는 테마 삭제 메소드
	public void deleteRoom(String theme) {
		String sql = "DELETE FROM ROOM WHERE RNAME = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, theme);

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("해당 테마가 삭제되었습니다.");
			} else {
				System.out.println("해당 테마가 삭제가 실패하였습니다. 확인 해 주세요.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 8-1) 회원 삭제 메소드 : 고객
	public String deleteCustomer(String cId) {
		String deleteCustomer = null;
		String sql = "DELETE FROM CUSTOMER WHERE CID = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cId);

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("회원 탈퇴가 완료 되었습니다. 이용 해 주셔서 감사합니다.");
			} else {
				System.out.println("회원 탈퇴가 실패하였습니다. 다시 확인 후 이용 해 주세요.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return deleteCustomer;
	}
	
	// 8-2) 회원 삭제 메소드 : 점주
		public String deleteManager(String mId) {
			String deleteManager = null;
			String sql = "DELETE FROM MANAGER WHERE MID = ?";

			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mId);

				int result = pstmt.executeUpdate();

				if (result > 0) {
					System.out.println("회원 탈퇴가 완료 되었습니다. 이용 해 주셔서 감사합니다.");
				} else {
					System.out.println("회원 탈퇴가 실패하였습니다. 다시 확인 후 이용 해 주세요.");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return deleteManager;
		}

	// 9-1) 회원 정보 수정 : 고객
	// - 비밀번호만 체크하는 메소드
	public boolean pwCheck(String cId, String cPw) {
		boolean pwCheck = false;

		String sql = "SELECT * FROM CUSTOMER WHERE CID = ? AND CPW = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cId);
			pstmt.setString(2, cPw);

			rs = pstmt.executeQuery();

			pwCheck = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pwCheck;
	}

	// - 정보 수정을 하는 메소드 : 비밀번호
	public void updatePw(String cPw, String cId) {
		String sql = "UPDATE CUSTOMER SET CPW = ? WHERE CID = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cPw);
			pstmt.setString(2, cId);
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("수정이 완료 되었습니다.");
			} else {
				System.out.println("수정을 실패 하였습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// - 정보 수정을 하는 메소드 : 이름
	public void updateName(String cName, String cId) {
		String sql = "UPDATE CUSTOMER SET CNAME = ? WHERE CID = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cName);
			pstmt.setString(2, cId);
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("수정이 완료 되었습니다.");
			} else {
				System.out.println("수정을 실패 하였습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// - 정보 수정을 하는 메소드 : 연락처
	public void updatePhone(String cPhone, String cId) {
		String sql = "UPDATE CUSTOMER SET CPHONE = ? WHERE CID = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cPhone);
			pstmt.setString(2, cId);
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("수정이 완료 되었습니다.");
			} else {
				System.out.println("수정을 실패 하였습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 9-2) 회원 정보 수정 : 점주
		// - 비밀번호만 체크하는 메소드
		public boolean pwCheck2(String mId, String mPw) {
			boolean pwCheck = false;

			String sql = "SELECT * FROM MANAGER WHERE MID = ? AND MPW = ?";

			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mId);
				pstmt.setString(2, mPw);

				rs = pstmt.executeQuery();

				pwCheck = rs.next();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return pwCheck;
		}

		// - 정보 수정을 하는 메소드 : 비밀번호
		public void updatePw2(String mPw, String mId) {
			String sql = "UPDATE MANAGER SET MPW = ? WHERE MID = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mPw);
				pstmt.setString(2, mId);
				
				int result = pstmt.executeUpdate();
				
				if(result > 0) {
					System.out.println("수정이 완료 되었습니다.");
				} else {
					System.out.println("수정을 실패 하였습니다.");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// - 정보 수정을 하는 메소드 : 이름
		public void updateName2(String mName, String mId) {
			String sql = "UPDATE MANAGER SET MNAME = ? WHERE MID = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mName);
				pstmt.setString(2, mId);
				
				int result = pstmt.executeUpdate();
				
				if(result > 0) {
					System.out.println("수정이 완료 되었습니다.");
				} else {
					System.out.println("수정을 실패 하였습니다.");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// - 정보 수정을 하는 메소드 : 연락처
		public void updatePhone2(String mPhone, String mId) {
			String sql = "UPDATE MANAGER SET MPHONE = ? WHERE MID = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mPhone);
				pstmt.setString(2, mId);
				
				int result = pstmt.executeUpdate();
				
				if(result > 0) {
					System.out.println("수정이 완료 되었습니다.");
				} else {
					System.out.println("수정을 실패 하였습니다.");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	// 10) 회원 목록 가져오기
	public void selectMember(Customer customer) {
		String sql = "SELECT * FROM CUSTOMER";
		try {
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			System.out.println("======== 회원 목록 ========");
			System.out.println();
			while(rs.next()) {
				System.out.println("아이디 : " + rs.getString(1));
				System.out.println("이름 : " + rs.getString(3));
				System.out.println("연락처 : " + rs.getString(4));
				System.out.println();
			}
			System.out.println("========================");
			System.out.println();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 11) 지점 전체 조회 메소드
	public void selectAllBranch() {
		String sql = "SELECT BRANCH FROM MANAGER";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			System.out.println("===================");
			while(rs.next()) {
				System.out.println("[지점명 : " + rs.getString(1) + "]");
			}
			System.out.println("===================");
		} catch(SQLException e) {
			
		}
	}
	
	// 12) 테마 조회
	public boolean selectTheme(String branch) {
		boolean theme = false;
		String sql = "SELECT RNAME FROM ROOM WHERE BRANCH = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, branch);
			rs = pstmt.executeQuery();
			System.out.println("===================");
			while(rs.next()) {
				System.out.println("[" + rs.getString(1) +"]");
			}
			System.out.println("===================");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return theme;
	}
	
	// 13) 지점 이름 제대로 입력 했는 지 체크
	public boolean checkBranch(String branch) {
		boolean cb = false;
		String sql = "SELECT BRANCH FROM MANAGER WHERE BRANCH = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, branch);
			rs = pstmt.executeQuery();
			cb = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cb;
	}
	// 14) 테마 이름 제대로 입력 했는 지 체크
	public boolean checkRoom(String theme) {
		boolean cr = false;
		String sql = "SELECT * FROM ROOM WHERE RNAME = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, theme);
			rs = pstmt.executeQuery();
			cr = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cr;
	}
	// 15) 예약 조회 : 고객
	public boolean selectBook(String branch, String bDate, String theme) {
		boolean sb = false;
		String sql = "SELECT * FROM BOOK WHERE BRANCH = ? AND BDATE = ? AND RNAME = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, branch);
			pstmt.setString(2, bDate);
			pstmt.setString(3, theme);
			rs = pstmt.executeQuery();
			sb = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb;
	}
	// 16) 예약 하기 : 고객
	public void insertBook(Book book) {
		String sql = "INSERT INTO BOOK VALUES(?, ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, book.getbDate());
			pstmt.setString(2, book.getbId());
			pstmt.setString(3, book.getbTheme());
			pstmt.setString(4, book.getbBranch());
			int result = pstmt.executeUpdate();
			if(result > 0) {
				System.out.println("예약이 완료 되었습니다.");
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// 17) 예약 내역 조회 : 고객
	public void book(String cId) {
		String sql = "SELECT * FROM BOOK WHERE CID = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cId);
			rs = pstmt.executeQuery();
			System.out.println("============================");
			System.out.println();
			while(rs.next()) {
				System.out.println(" 날짜 : " + rs.getString(1));
				System.out.println(" 방이름 : " + rs.getString(3));
				System.out.println(" 지점명 : " + rs.getString(4));
				System.out.println();
			}
			System.out.println("============================");
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 18) 예약 테이블에 해당 내용이 있는 지 확인 : 고객
	public boolean checkBook(Book book) {
		boolean checkBook = false;
		String sql = "SELECT * FROM BOOK WHERE BDATE = ? AND CID = ? AND RNAME = ? AND BRANCH = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, book.getbDate());
			pstmt.setString(2, book.getbId());
			pstmt.setString(3, book.getbTheme());
			pstmt.setString(4, book.getbBranch());
			rs = pstmt.executeQuery();
			checkBook = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkBook;
	}
	// 19) 예약 테이블에서 해당 내용 삭제 : 고객
	public void deleteBook(Book book) {
		String sql = "DELETE FROM BOOK WHERE BDATE = ? AND CID = ? AND RNAME = ? AND BRANCH = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, book.getbDate());
			pstmt.setString(2, book.getbId());
			pstmt.setString(3, book.getbTheme());
			pstmt.setString(4, book.getbBranch());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("예약이 취소되었습니다.");
			} else {
				System.out.println("예약을 취소할 수 없습니다. 다시 시도 해 주세요.");
			}
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// 20) 예약된 내역 조회 : 점주
	public void selectBooks(String mId) {
		String sql = "SELECT BOOK.CID, BOOK.RNAME, BOOK.BDATE FROM MANAGER, BOOK WHERE MANAGER.BRANCH = BOOK.BRANCH AND MID = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mId);
			rs = pstmt.executeQuery();
			System.out.println("======= 예약 내역 =======");
			System.out.println();
			while(rs.next()) {
				System.out.println("예약 날짜 : " + rs.getString(3));
				System.out.println("예약자 아이디 : " + rs.getString(1));
				System.out.println("방 이름 : " + rs.getString(2));
				System.out.println();
			}
			System.out.println("=======================");
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// 21) 입력한 내용과 예약 테이블 비교
	public boolean checkBook2(Book book) {
		boolean checkBook2 = false;
		String sql = "SELECT * FROM BOOK WHERE BDATE = ? AND CID = ? AND RNAME = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, book.getbDate());
			pstmt.setString(2, book.getbId());
			pstmt.setString(3, book.getbTheme());
			rs = pstmt.executeQuery();
			checkBook2 = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkBook2;
	}
	// 22) 예약 테이블에서 해당 내용 삭제 : 점주
	public void deleteBook2(Book book) {
		String sql = "DELETE FROM BOOK WHERE BDATE = ? AND CID = ? AND RNAME = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, book.getbDate());
			pstmt.setString(2, book.getbId());
			pstmt.setString(3, book.getbTheme());
			int result = pstmt.executeUpdate();
			if(result > 0) {
				System.out.println("예약이 취소 되었습니다.");
			} else {
				System.out.println("예약 취소가 되지 않았습니다. 확인 해 주세요.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
