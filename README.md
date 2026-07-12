# 스터디카페 관리 시스템

Java Swing으로 구현한 스터디카페 좌석·이용권 관리 시스템입니다.

## 기술 스택
![Java](https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Swing](https://img.shields.io/badge/SWING-orange?style=for-the-badge&logo=java&logoColor=white)

## 동작 프로세스

**메인 화면**
사용 가능 좌석(0/전체), 스터디룸(0/전체) 현황을 표시합니다.

**로그인**
전화번호, 비밀번호(숫자 4자리) 입력 후 로그인 버튼을 누르면 로그인이 완료되고 
이용권 선택 화면으로 이동합니다.

**이용권 선택**
- 이용권이 없는 경우: 요금제 선택 창에서 분류별로 표시된 모든 요금제 중 하나를 
  버튼으로 선택 → 이용권 선택 창으로 이동
- 이용권이 있는 경우: 보유한 이용권을 버튼으로 표시, 선택 시 좌석 선택 화면으로 이동

**좌석 선택**
좌석을 사용중 / 사용가능 / 내좌석(미구현) 세 가지로 분류해 색깔로 표시합니다. 
모든 좌석은 버튼으로 표시되며, 원하는 좌석을 선택할 수 있습니다.

**입실/퇴실 관리**
- 입실: 입실 버튼 + 본인 전화번호 + 비밀번호 입력 → 입실 처리
- 퇴실: 퇴실 버튼 + 본인 전화번호 + 비밀번호 입력 → 퇴실 처리, 이용 시간 초과 시 
  추가 요금 정산 화면으로 연결

## 화면 미리보기
| 회원가입 | 로그인 | 이용권 확인 |
|---|---|---|
| ![회원가입](https://github.com/user-attachments/assets/696a7238-0baf-4d5e-813b-fef7c5492864) | ![로그인](https://github.com/user-attachments/assets/0b4bc82f-c2d8-4560-a0f6-239441734f83) | ![이용권구매](https://github.com/user-attachments/assets/dabe79d4-5a9b-4708-84b2-6ed34ddbaa9d) |
| 입/퇴실 관리 | 좌석 선택 | 입실 완료 |
| ![입퇴실화면](https://github.com/user-attachments/assets/22879e3a-2f0b-42fa-9264-0602e65a8fc9) | ![좌석선택화면](https://github.com/user-attachments/assets/9ff36167-e774-4c5c-81f2-cbf1ab93c974) | ![좌석선택입실](https://github.com/user-attachments/assets/fe2741b9-16ff-48cb-8627-3d8f5a59b861) |
| 중복 좌석 선택 안내 | 퇴실 완료 | 초과 이용 정산 상세 |
| ![좌석이미있음](https://github.com/user-attachments/assets/77384d0f-0a2e-4028-9d31-95d21a207d44) | ![퇴실](https://github.com/user-attachments/assets/3f7e229c-300c-4332-a469-bec417bac576) | ![퇴실추가요금](https://github.com/user-attachments/assets/0e0b13d2-01c5-4c5d-896b-eb7ad52f85c8) |
| 추가 요금 결제 | | |
| ![추가요금결제](https://github.com/user-attachments/assets/2d61a2b6-d464-4584-892f-05185cbc7955) | | |

## 데이터 모델링
| 회원가입 | 로그인 |
|---|---|
| **DB 설계** | **ERD** |
| ![db설계](https://github.com/user-attachments/assets/e45e89af-95d3-4f24-a58a-8338735fde82) | ![ERD](https://github.com/user-attachments/assets/4e84a43b-1066-4be1-9081-cd1b5ec17402) |
