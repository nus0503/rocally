// JavaScript를 사용하여 메뉴 버튼 클릭 시 드롭다운 토글 구현
    document
      .querySelector(".menu-button")
      .addEventListener("click", function () {
        document.querySelector(".menu-dropdown").classList.toggle("show");
      });
    document
      .querySelector(".globe-button")
      .addEventListener("click", function () {
        document.querySelector(".globe-dropdown").classList.toggle("show");
      });
    // 로고 버튼 클릭 시 홈페이지로 이동
    document.querySelector(".logo").addEventListener("click", function () {
      window.location.href = "home.html"; // 홈페이지 주소로 변경
    });