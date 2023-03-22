# #Penpal
팀으로 진행한 프로젝트를 리팩토링중입니다.

[AWS] https://bit.ly/3LHhU9f

(배포는 상황에 따라 중지될 수 있습니다.)

### Branch
- main 
- dev
- original : 팀 프로젝트 원본

### 목차
1. [기획의도](#기획의도)
2. [기술스택](#기술스택)
3. [주요기능](#주요기능)
4. [기능소개](#기능소개)
5. [ERD](#erd)
6. [commit](#commit)

## 기획의도
 >팀원이 사용중인 penpal 사이트가 너무 노후화되고 email 교환 이후로는 버려지는 것을 개선하고자 시작한 프로젝트입니다.<br>
 기존 사이트의 기능을 일부 가져오고 외부 서비스인 email을 이용하기 보다는 사이트 내에서 해결하기 위해 친구, 채팅, 태그 기능을 추가하였습니다.
 
## 기술스택
- html5, css3, javaScript, jQuery 3.6.1, thymeleaf
- java 17, spring boot 2.7.4(gradle 7.5.1), spring security, json-simple 1.1.1
- spring data JPA, H2
- webSocket

## 주요기능
- 로그인, 회원가입, 회원정보수정
- 태그기능 게시판
- 프로필
- 게시판, 프로필 검색
- 친구 기능, 온라인 친구 표시
- 실시간 채팅
- 새 메세지, 친구요청 알림

## 기능소개
- 메인 페이지

![1](https://user-images.githubusercontent.com/110438268/211064247-3f37e2dd-9950-4f3c-a2ed-23384bc9c533.png)
![2](https://user-images.githubusercontent.com/110438268/211064282-a47dd86a-507b-464e-a37c-c8358d3acd3a.png)
- 프로필

![3](https://user-images.githubusercontent.com/110438268/211064288-0544e69a-f3c5-4209-86eb-f810284328b7.png)
![4](https://user-images.githubusercontent.com/110438268/211064289-62500b57-c631-493b-b722-c1e4a46b6731.png)
- 로그인

![5](https://user-images.githubusercontent.com/110438268/211064295-364b5be8-3b28-45a6-87c6-9fc005d9bf90.png)
- 게시판

![6](https://user-images.githubusercontent.com/110438268/211064299-ad9a6d4a-ccdb-4335-b5ec-9978781bba2e.png)
- 친구

![7](https://user-images.githubusercontent.com/110438268/211064303-15c9eeaf-44e9-482d-8752-e2eb49dcaf8b.png)
![8](https://user-images.githubusercontent.com/110438268/211064306-a5dca80d-7e0b-476c-9dbf-dc2ecc60eff6.png)
- 채팅

![9](https://user-images.githubusercontent.com/110438268/211064308-bd722fe1-9997-47e1-bf5e-be027b10a434.png)
![10](https://user-images.githubusercontent.com/110438268/211064310-79cceed1-0bb1-4fa6-8eb8-c543f7cc2e20.png)

## ERD
![image](https://user-images.githubusercontent.com/110438268/211223680-b95b1005-6626-43c7-93d0-b396a3cdb9bf.png)

## commit
```
type: subject

body
```
type
- feat      : 새로운 기능 추가
- fix       : 버그
- style     : UI/UX 관련
- refactor  : 코드 리팩토링
- test      : 테스트 관련
- chore     : 그 외 기타사항
