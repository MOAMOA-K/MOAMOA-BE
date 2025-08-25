# 📌 모아모아 뀽 Backend

> **대학 상권을 살리는 AI 기반 소통 플랫폼**  
학생들은 솔직한 피드백을 통해 포인트와 쿠폰을 얻고, 사장님은 학생들의 피드백을 바탕으로 매장을 개선할 수 있는 상생형 서비스입니다.

- 배포 주소: http://moamoa-k.s3-website.ap-northeast-2.amazonaws.com/

---

## 🚀 서비스 개요

### 🎯 문제 정의
- 기존 공개 리뷰 시스템(네이버/카카오 지도 등)은 평판 부담 때문에 **광고성 리뷰**만 양산됨
- **학생들의 솔직한 피드백 기회 상실** → 사장님은 개선 방향을 파악하지 못함
- 결과적으로 **대학 상권 침체와 학생들의 상권 이탈이 가속화됨**

### 🛠 해결 방식
- **영수증 기반 인증 + OCR**: 진짜 방문자만 피드백 가능
- **익명 피드백**: 평판 부담 없이 솔직한 의견 가능
- **AI 필터링**: 비방/욕설 제거 + 개선점 요약 전달
- **리워드 시스템**: 학생은 포인트/쿠폰 보상, 사장님은 "개선 공지" 홍보

---

## 🛠 기술 스택

### Backend
- **Language**: Java / Kotlin (Spring Boot)
- **Framework**: Spring Boot 3.x
- **Database**: MySQL
- **ORM**: JPA (Hibernate)
- **Auth**: Spring Security + JWT

### Infra & DevOps
- **Deployment**: AWS EC2 + Docker + Nginx
- **Storage**: AWS S3 (영수증 이미지 저장)
- **AI API**: Naver Cloud OCR, Gemini API

—
