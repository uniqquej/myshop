## 프로젝트 개요
### ✔ 프로젝트 소개
#### 스프링 부트 쇼핑몰 프로젝트 with JPA 참조
---
## 2. 서비스
### ✔ 1. home
  -** / (GET)**
    - security를 이용하여 로그인 여부 확인 후 권한에 맞는 네비바 호출
    - 등록된 상품 목록은 누구나 조회 가능

### ✔ 2. user
  - **/user/signup (GET/POST)**
    - 이메일, 비밀번호를 입력받아 회원가입 진행
    
  - **/user/login (GET)**
    - 로그인 페이지 반환
    - security의 formLogin 사용하여 로그인 진행
       
  - **/user/logout (GET)**
    - security logout 사용
      
### ✔ 3. item
  - **/items (GET)**
    - ItemResponseDto 배열 반환

  - **/item/{itemId} (GET)**
    - itemId에 해당하는 ItemResponseDto  반환
    - 상품 디테일 페이지 반환

  - **/admin/item/new (GET, POST)**
    - 관리자 상품 등록
    - 상품의 이미지는 따로 배열로 입력받아 상품과 이미지 따로 저장
  
  - **/admin/item/{itemId} (POST)**
    - 관리자 상품 수정
      
  - **/admin/items, /admin/items/{page} (GET)**
    - 관리자 상품 관리 페이지 반환
    - 상품 목록을 확인하고, 수정 페이지로 이동 가능

### ✔ 4. cart
  -** /cart (GET,POST)**
    - 장바구니 페이지 반환
    - 장바구니 담기 성공 시 cartItemId 반환
      
  - **/cartItem/{cartItemId} (PATCH, DELETE)**
    - 장바구니 상품의 개수 수정
    - 장바구니 상품 삭제
      
  - **/cart/orders (POST)**
    - 장바구니 상품 주문

### ✔ 5. order
  - **/order (POST)**
    - 상품 주문
      
  - **/orders, /orders/{page} (GET)**
    - 주문 목록 페이지 반환
    
  - **/order/{orderId}/cancel (POST)**
    - 주문자 이메일 확인 후 주문 취소 

### ✔ 6. review
  - **/review (GET,POST)**
    - 리뷰 작성 페이지 반환
    - 파라미터로 orderItemId를 받아 상품에 해당하는 리뷰 작성
      
  - **/reviews/{itemId} (GET)**
    - itemId에 해당하는 상품의 리뷰 목록 조회

---
## 구성
### ERD
![myshoperd](https://github.com/uniqquej/myshop/assets/109218139/9357ea71-ea4d-43e6-9f83-f9b0390c6716)
