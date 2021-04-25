# LDCC 정보기술연구소 과제테스트 전형_Back-end (이문혁)
## 주제 : 영화 예매 서비스 API 개발

### 실행 환경
gradle - 6.8.3   
java - 11   
springboot - 2.4.5   
os - windows
database - h2

### 프로젝트 실행 방법
1. 프로젝트의 루트 디렉토리에서 아래의 명령어를 실행한다.   
`java -jar ./build/libs/demo-0.0.1.jar`

2. jar 파일이 없을 시 프로젝트의 루트 디렉토리에서 아래의 명령어를 실행한 후 아래의 명령어를 다시 실행한다.   
windows `.\gradlew "-Dfile.encoding=UTF-8" build`   
linux or mac `./gradlew "-Dfile.encoding=UTF-8" build`

### 테스트 실행 방법
windows `.\gradlew "-Dfile.encoding=UTF-8" test`   
linux or mac `./gradlew "-Dfile.encoding=UTF-8" test` 

### API 문서
프로젝트를 실행한 상태에서 다음 주소를 크롬 브라우저에서 입력한다.   
http://localhost:8080/docs/index.html
