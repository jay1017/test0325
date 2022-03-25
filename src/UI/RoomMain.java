package UI;

import java.util.Scanner;

import DAO.RoomSQL;
import DTO.*;

public class RoomMain {

	public static void main(String[] args) {
		// 객체 생성
		Scanner sc = new Scanner(System.in);
		RoomSQL sql = new RoomSQL();
		Manager manager = new Manager();
		Customer customer = new Customer();
		Room room = new Room();
		Book book = new Book();

		// 변수 생성
		String cId;
		String cPw;
		String cName;
		String cPhone;

		String branch;
		String mId;
		String mPw;
		String mName;
		String mPhone;

		String bDate;
		String theme;

		boolean run1 = true; // while문 1 : 메뉴 1
		boolean run2 = true; // while문 2 : 메뉴 2
		boolean run3 = true; // 속메뉴
		int menu1 = 0; // 메뉴 1 : 회원가입, 로그인, 종료
		int menu2 = 0; // 메뉴 2 : 예약, 확인, 취소 등
		boolean check1; // 아이디, 비밀번호 확인(로그인, 회원탈퇴)
		boolean check2; // 비밀번호 확인(회원정보수정)

		// DB 접속
		sql.connect();
		System.out.println();
		System.out.println("[이스케이프룸에 오신 것을 환영합니다.]");

		// 메뉴 띄우기
		while (run1) {
			System.out.println("=================================");
			System.out.println(" 1.회원가입      2.로그인      3.종료");
			System.out.println("=================================");
			System.out.print("메뉴 선택 : ");
			menu1 = sc.nextInt();
			System.out.println();

			switch (menu1) {
			case 1:
				System.out.println("[회원 분류를 선택 해 주세요.]");
				System.out.println("---------------------------------");
				System.out.println("       1.고객          2.점주       ");
				System.out.println("---------------------------------");
				System.out.print("선택 : ");
				int jcode = sc.nextInt();
				System.out.println();

				if (jcode == 1) {
					System.out.println("===================== 회원 가입 =====================");
					System.out.println("사용하실 아이디를 입력 해 주세요. (영,숫자 혼용 가능 / 10자 이내)");
					System.out.print("아이디 : ");
					cId = sc.next();

					System.out.println("사용하실 비밀번호를 입력 해 주세요. (영,숫자 혼용 가능 / 10자 이내)");
					System.out.print("비밀번호 : ");
					cPw = sc.next();

					System.out.println("고객님의 성함을 입력 해 주세요.");
					System.out.print("성함 : ");
					cName = sc.next();

					System.out.println("연락처를 입력 해 주세요. (예 : 010-1234-5678)");
					System.out.print("연락처 : ");
					cPhone = sc.next();
					System.out.println("====================================================");
					System.out.println();

					customer.setcId(cId);
					customer.setcPw(cPw);
					customer.setcName(cName);
					customer.setcPhone(cPhone);

					sql.insertCustomer(customer);
				} else if (jcode == 2) {
					System.out.println("===================== 회원 가입 =====================");
					System.out.println("운영 중이신 지점명을 입력 해 주세요. (예 : 구월점)");
					System.out.print("지점명 : ");
					branch = sc.next();

					System.out.println("사용하실 아이디를 입력 해 주세요. (영, 숫자 혼용 가능 / 10자 이내)");
					System.out.print("아이디 : ");
					mId = sc.next();

					System.out.println("사용하실 비밀번호를 입력 해 주세요. (영, 숫자 혼용 가능 / 10자 이내)");
					System.out.print("비밀번호 : ");
					mPw = sc.next();

					System.out.println("점장님의 성함을 입력 해 주세요.");
					System.out.print("성함 : ");
					mName = sc.next();

					System.out.println("지점 대표 전화번호를 입력 해 주세요. (예 : 010-1234-5678)");
					System.out.print("전화번호 : ");
					mPhone = sc.next();

					System.out.println("====================================================");
					System.out.println();

					manager.setBranch(branch);
					manager.setmId(mId);
					manager.setmPw(mPw);
					manager.setmName(mName);
					manager.setmPhone(mPhone);

					sql.insertManager(manager);
				}
				break;
			case 2:
				run2 = true; // 회원마다 보여주는 메뉴가 false로 되었기 때문에 초기화 시켜주기.

				System.out.println("[회원 분류를 선택 해 주세요.]");
				System.out.println("---------------------------------");
				System.out.println("       1.고객          2.점주       ");
				System.out.println("---------------------------------");
				System.out.print("선택 : ");
				jcode = sc.nextInt();
				System.out.println();

				if (jcode == 1) {
					System.out.println("============= 로그인 =============");
					System.out.print("아이디 : ");
					cId = sc.next();
					System.out.print("비밀번호 : ");
					cPw = sc.next();
					System.out.println("================================");
					System.out.println();

					check1 = sql.idCheck(cId, cPw);

					if (check1) {
						System.out.println("[고객님, 환영합니다.]");
						while (run2) {
							System.out.println("[메뉴를 선택 해 주세요.]");
							System.out.println("=================================");
							System.out.println(" 1.예약하기    2.예약확인    3.예약취소");
							System.out.println(" 4.회원수정    5.회원탈퇴    6.로그아웃");
							System.out.println("=================================");
							System.out.print("메뉴 선택 : ");
							menu2 = sc.nextInt();
							System.out.println();

							switch (menu2) {
							case 1:
								System.out.println("지점을 입력 해 주세요.");
								sql.selectAllBranch();
								System.out.print("지점명 : ");
								branch = sc.next();
								System.out.println();
								
								boolean checkBranch = sql.checkBranch(branch);		// 지점명을 제대로 입력 했으면

								if(checkBranch) {
									System.out.println("날짜를 입력 해 주세요. (예 : 2022-03-23)");
									System.out.print("날짜 : ");
									bDate = sc.next();
									System.out.println();

									System.out.println("원하시는 테마를 입력 해 주세요.");
									sql.selectTheme(branch);
									System.out.print("테마명 : ");
									theme = sc.next();
									System.out.println();
									
									boolean checkTheme = sql.checkRoom(theme);		// 테마명을 제대로 입력 했으면
									
									if(checkTheme) {
										boolean selectBook = sql.selectBook(branch, bDate, theme);		// 지점, 날짜, 테마명 일치하는 게 없으면(false)
										if(selectBook == false) {
											System.out.println("예약이 가능합니다. 예약 하시겠습니까? (번호를 입력 해 주세요.)");
											System.out.println("=======================");
											System.out.println("     1.Yes   2.No      ");
											System.out.println("=======================");
											System.out.print("선택 : ");
											int menu4 = sc.nextInt();
											System.out.println();
											
											if(menu4 == 1) {
												book.setbDate(bDate);
												book.setbBranch(branch);
												book.setbTheme(theme);
												book.setbId(cId);
												sql.insertBook(book);
											} else if(menu4 == 2) {
												System.out.println("예약을 하지 않고 메인 화면으로 돌아갑니다.");
											} else {
												System.out.println("잘못 입력하셨습니다.");
											}
										} else {
											System.out.println("이미 예약된 테마입니다.");
										}
									} else {
										System.out.println("테마명을 잘못 입력하셨습니다.");
										System.out.println();
									}
								} else {
									System.out.println("값이 잘못 입력 되었습니다.");
									System.out.println();
								}
								break;
							case 2:
								sql.book(cId);
								break;
							case 3:
								System.out.println("========= 예약 취소 =========");
								System.out.println("취소 하시려는 지점의 이름을 입력 해 주세요. (예 : 구월점)");
								System.out.print("지점명 : ");
								branch = sc.next();
								
								System.out.println("취소 하시려는 날짜를 입력 해 주세요. (예 : 2022-03-03)");
								System.out.print("날짜 : ");
								bDate = sc.next();
								
								System.out.println("취소 하시려는 테마의 이름을 입력 해 주세요.");
								System.out.print("테마명 : ");
								theme = sc.next();
								System.out.println("===========================");
								System.out.println();
								
								book.setbBranch(branch);
								book.setbDate(bDate);
								book.setbTheme(theme);
								book.setbId(cId);
								
								boolean check3 = sql.checkBook(book);
								if(check3) {
									sql.deleteBook(book);
								} else {
									System.out.println("일치하는 예약 내역이 없습니다. 확인 해 주세요.");
									System.out.println();
								}
								break;
							case 4:
								System.out.println("비밀번호를 입력 해 주세요.");
								System.out.print("비밀번호 : ");
								cPw = sc.next();

								check2 = sql.pwCheck(cId, cPw);

								if (check2) {
									System.out.println("비밀번호가 일치합니다.");
									System.out.println();
									System.out.println("[수정 할 정보를 선택 해 주세요.]");
									System.out.println("================================");
									System.out.println("1.비밀번호  2.이름  3.연락처  4.뒤로가기");
									System.out.println("================================");
									System.out.print("선택 : ");
									menu2 = sc.nextInt();

									switch (menu2) {
									case 1:
										System.out.print("변경할 비밀번호 : ");
										cPw = sc.next();
										sql.updatePw(cPw, cId);
										break;
									case 2:
										System.out.print("변경할 이름 : ");
										cName = sc.next();
										sql.updateName(cName, cId);
										break;
									case 3:
										System.out.print("변경할 연락처 : ");
										cPhone = sc.next();
										sql.updatePhone(cPhone, cId);
										break;
									case 4:
										System.out.println("메인으로 돌아갑니다.");
										check1 = false;
										break;
									default:
										break;
									}
								} else {
									System.out.println("비밀번호가 틀립니다. 확인 해 주세요.");
									System.out.println();
								}
								break;
							case 5:
								System.out.println("정말 탈퇴하시겠습니까?");
								System.out.println("(네 / 아니오 둘 중 하나 입력)");
								System.out.print("답 : ");
								String memberOut = sc.next();

								if (memberOut.equals("네")) {
									System.out.println("아이디를 입력 해 주세요.");
									System.out.print("아이디 : ");
									cId = sc.next();

									System.out.println("비밀번호를 입력 해 주세요.");
									System.out.print("비밀번호 : ");
									cPw = sc.next();

									check1 = sql.idCheck(cId, cPw);

									if (check1) {
										sql.deleteCustomer(cId);
										run2 = false;
									} else {
										System.out.println("아이디와 비밀번호가 일치하지 않습니다. 확인 후 입력 해 주세요.");
										System.out.println();
									}
								} else if (memberOut.equals("아니오")) {
									System.out.println();
									System.out.println("메인으로 돌아갑니다.");
									System.out.println();
								} else {
									System.out.println("잘못 입력하셨습니다. 다시 입력 해 주세요.");
								}
								break;
							case 6:
								run2 = false;
								System.out.println("로그아웃 합니다.");
								break;
							default:
								System.out.println("잘못 입력하셨습니다. 다시 입력 해 주세요.");
								break;
							}
						}
					} else {
						System.out.println("아이디와 비밀번호가 일치하지 않습니다. 다시 입력 해 주세요.");
					}
				} else if (jcode == 2) {
					System.out.println("============= 로그인 =============");
					System.out.print("아이디 : ");
					mId = sc.next();
					System.out.print("비밀번호 : ");
					mPw = sc.next();
					System.out.println("================================");
					System.out.println();

					check1 = sql.idCheck2(mId, mPw);

					if (check1) {
						System.out.println("[점장님, 환영합니다.]");
						while (run2) {
							run3 = true;
							System.out.println("[메뉴를 선택 해 주세요.]");
							System.out.println("=================================");
							System.out.println(" 1.테마관리    2.예약관리    3.회원목록");
							System.out.println(" 4.정보수정    5.회원탈퇴    6.로그아웃");
							System.out.println("=================================");
							System.out.print("메뉴 선택 : ");
							menu2 = sc.nextInt();
							System.out.println();

							switch (menu2) {
							case 1:
								while (run3) {
									System.out.println("========== 테마 관리 ==========");
									System.out.println("1.추가  2.조회  3.삭제  4.뒤로가기");
									System.out.println("============================");
									System.out.print("메뉴 선택 : ");
									int menu3 = sc.nextInt();
									System.out.println();

									switch (menu3) {
									case 1:
										branch = sql.selectBranch(mId);
										System.out.println("테마를 추가 합니다. (띄어쓰기 없이 입력 해 주세요.)");
										System.out.print("테마 입력 : ");
										theme = sc.next();
										room.setRoom(theme);

										sql.insertRoom(room, branch);
										break;
									case 2:
										branch = sql.selectBranch(mId);
										sql.selectRoom(room, branch);
										break;
									case 3:
										System.out.println("삭제 할 테마를 입력 해 주세요.");
										System.out.print("테마 입력 : ");
										theme = sc.next();

										boolean checkTheme = sql.checkTheme(theme);

										if (checkTheme) {
											sql.deleteRoom(theme);
										} else {
											System.out.println("테마 이름을 잘못 입력하셨습니다. 확인 후 다시 작성 해 주세요.");
										}
										break;
									case 4:
										run3 = false;
										System.out.println("메인 페이지로 돌아갑니다.");
										break;
									default:
										System.out.println("잘못 입력하셨습니다. 다시 입력 해 주세요.");
										break;
									}
								}
								break;
							case 2:
								while (run3) {
									System.out.println("======= 예약 관리 =======");
									System.out.println("1.조회  2.삭제  3.뒤로가기");
									System.out.println("======================");
									System.out.print("메뉴 선택 : ");
									int menu3 = sc.nextInt();
									System.out.println();
									
									switch (menu3) {
									case 1:
										sql.selectBooks(mId);
										break;
									case 2:
										System.out.println("========= 예약 취소 =========");
										System.out.println("취소 하시려는 날짜를 입력 해 주세요. (예 : 2022-03-03)");
										System.out.print("날짜 : ");
										bDate = sc.next();
										
										System.out.println("취소 하시려는 예약자 분의 아이디를 입력 해 주세요.");
										System.out.print("아이디 : ");
										cId = sc.next();
										
										System.out.println("취소 하시려는 테마의 이름을 입력 해 주세요.");
										System.out.print("테마명 : ");
										theme = sc.next();
										System.out.println("===========================");
										System.out.println();
										
										book.setbDate(bDate);
										book.setbId(cId);
										book.setbTheme(theme);
										
										boolean check3 = sql.checkBook2(book);
										if(check3) {
											sql.deleteBook2(book);
										} else {
											System.out.println("일치하는 예약 내역이 없습니다. 확인 해 주세요.");
											System.out.println();
										}
										break;
									case 3:
										run3 = false;
										System.out.println("메인 페이지로 돌아갑니다.");
										break;
									default:
										System.out.println("잘못 입력하셨습니다. 다시 입력 해 주세요.");
										break;
									}
								}
								break;
							case 3:
								sql.selectMember(customer);
								break;
							case 4:
								System.out.println("비밀번호를 입력 해 주세요.");
								System.out.print("비밀번호 : ");
								mPw = sc.next();

								check2 = sql.pwCheck(mId, mPw);

								if (check2) {
									System.out.println("비밀번호가 일치합니다.");
									System.out.println();
									System.out.println("[수정 할 정보를 선택 해 주세요.]");
									System.out.println("=====================================");
									System.out.println(" 1.비밀번호  2.점주명  3.대표전화  4.뒤로가기");
									System.out.println("=====================================");
									System.out.print("선택 : ");
									menu2 = sc.nextInt();

									switch (menu2) {
									case 1:
										System.out.print("변경할 비밀번호 : ");
										mPw = sc.next();
										sql.updatePw2(mPw, mId);
										break;
									case 2:
										System.out.print("변경할 성함 : ");
										mName = sc.next();
										sql.updateName2(mName, mId);
										break;
									case 3:
										System.out.print("변경할 전화번호 : ");
										mPhone = sc.next();
										sql.updatePhone2(mPhone, mId);
										break;
									case 4:
										System.out.println("메인으로 돌아갑니다.");
										check1 = false;
										break;
									default:
										break;
									}
								}
								break;
							case 5:
								System.out.println("정말 탈퇴하시겠습니까?");
								System.out.println("(네 / 아니오 둘 중 하나 입력)");
								System.out.print("답 : ");
								String memberOut = sc.next();

								if (memberOut.equals("네")) {
									System.out.println("아이디를 입력 해 주세요.");
									System.out.print("아이디 : ");
									mId = sc.next();

									System.out.println("비밀번호를 입력 해 주세요.");
									System.out.print("비밀번호 : ");
									mPw = sc.next();

									check1 = sql.idCheck(mId, mPw);

									if (check1) {
										sql.deleteManager(mId);
										run2 = false;
									} else {
										System.out.println("아이디와 비밀번호가 일치하지 않습니다. 확인 후 입력 해 주세요.");
										System.out.println();
									}
								} else if (memberOut.equals("아니오")) {
									System.out.println();
									System.out.println("메인으로 돌아갑니다.");
									System.out.println();
								} else {
									System.out.println("잘못 입력하셨습니다. 다시 입력 해 주세요.");
								}
								break;
							case 6:
								run2 = false;
								System.out.println("로그아웃 합니다.");
								break;
							default:
								System.out.println("잘못 입력하셨습니다. 다시 입력 해 주세요.");
								break;
							}
						}
					} else {
						System.out.println("아이디와 비밀번호가 일치하지 않습니다. 다시 입력 해 주세요.");
					}
				} else {
					System.out.println("회원 구분 번호를 잘못 입력하셨습니다.");
				}
				break;
			case 3:
				run1 = false;
				System.out.println("프로그램을 종료합니다.");
				break;
			default:
				System.out.println("잘못 입력하셨습니다. 다시 입력하세요.");
				break;
			}
		}

		sql.conClose();
		sc.close();
	}

}
