이 프로젝트는 JPA와 Spring Boot를 이용하여 구현한 게시판입니다.(미완성 프로젝트 계속 진행 중)

QueryDSL을 사용하여 JPA 쿼리를 작성할 때 자동으로 생성되는 Q클래스는 일반적으로 git에서 무시됩니다.(그래서 깃에 올리지 않고 관리합니다.)

Q클래스가 코드 생성 도구(QueryDSL)를 통해 생성되는 클래스이기 때문입니다. 따라서 이 클래스 파일은 개발자가 직접 수정하거나 관리할 필요가 없으며, 소스 코드 관리를 하면서도 자동으로 생성되는 이 파일이 변경될 수 있기 때문에 Git에서 무시되는 것입니다.


또한, TDD(Test-driven Development) 방식으로 테스트 코드를 작성하여 테스트를 수행하였습니다.

구현한 기능 중에는 회원 관리 기능이 포함되어 있습니다. 회원 가입 및 회원 목록 확인(회원 정보 수정 포함)이 가능합니다.

또한 상품 관리 기능이 있습니다. 상품 등록 및 상품 목록(상품 정보 수정 포함)이 가능합니다.(아직 구현 중!)

마지막으로는 주문 관리 기능이 있습니다. 이 기능을 이용하면 상품 주문 및 주문 내역 확인이 가능합니다. 주문 취소 기능도 함께 제공되며, 주문한 상품과 취소한 상품을 검색해서 확인할 수 있는 기능도 있습니다.
