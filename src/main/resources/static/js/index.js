var main = {
    init : function () {
        var _this = this;
        $('#btn-login-step1').on('click', function () {
                    _this.loginStep1();
                });

        $('#btn-login-step2').on('click', function () {
            _this.loginStep2();
        });

        $('#btn-update').on('click', () => {
            _this.update();
        });
    },

    update : function () {
        const data = {
            id : $('#id').val(),
            user : $('#username').val(),
            existingPassword : $('#existingPassword').val();
            newPassword : $('#newPassword').val(),
            email : $('#email').val(),
        };

        $.ajax({
            type : "PUT",
            url : "/user",
            data : JSON.stringify(data),
            contentType : "application/json; charset=utf-8",
            dataType : "json"
        }).done(function (data) {
            alert("회원수정이 완료되었습니다.");
            location.href="/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    loginStep1 : function () {
        var emailValue = $('#email').val();
            var data = {
                email: emailValue,
            };

                $.ajax({
                    type: 'POST',
                    url: '/login-step1',
//                    dataType: 'json',
                    contentType: 'application/json; charset=utf=8',
                    data: JSON.stringify(data)
                }).done(function (data) {
                    console.log(data);
                    if (data == 1) {
                        $('#login-container').fadeOut(300, function () {
                                                $(this).html(`
                                                    <div class="title">Log in</div>
                                                              <div class="subtitle">welcome Back, User!</div>

                                                              <form action="">


                                                                <div>
                                                                  <img src="./2.png" alt="">
                                                                  <div>user***@gm***.com</div>
                                                                </div>
                                                                <div class="continue-button">
                                                                  <button class="button">Continue</button>
                                                                </div>
                                                            </form>
                                                            <div>Not you? Use another account</div>
                                                `).fadeIn(300);
                                                $('#login-container').addClass("animate__animated animate__backInRight");
                                            });
                    } else {
                        $('#login-container').fadeOut(300, function () {
                                                $(this).html(`
                                                    <div class="title">Log in</div>
                                                              <div class="subtitle">welcome Back, User!</div>
                                                              <div class="email-label">Password</div>
                                                              <form action="/login" method="post">
                                                                <div class="input-container">
                                                                    <input type="hidden" id="hiddenEmail" name="email" value="${emailValue}"/>
                                                                  <input
                                                                      type="password"
                                                                      id="password"
                                                                      name="password"
                                                                      placeholder="🔒 Enter your password"
                                                                    />
                                                                </div>
                                                                <div class="forgot">password forget?</div>
                                                                <div class="continue-button">
                                                                  <button class="button">Log in</button>
                                                                </div>
                                                            </form>
                                                            <div class="social-login-container">
                                                              <span class="continue-with">or continue with</span>
                                                              <div class="social-login-buttons">
                                                                <!-- 소셜 로그인 이미지 -->
                                                                <button class="social-login-button">
                                                                  <img src="/images/social_image_1.png" alt="Social Login 1" />
                                                                </button>
                                                                <button class="social-login-button">
                                                                  <img src="/images/social_image_2.png" alt="Social Login 2" />
                                                                </button>
                                                                <button class="social-login-button">
                                                                  <img src="/images/social_image_3.png" alt="Social Login 3" />
                                                                </button>
                                                                <button class="social-login-button">
                                                                  <img src="/images/social_image_4.png" alt="Social Login 4" />
                                                                </button>
                                                              </div>
                                                            </div>
                                                `).fadeIn(300);
                                                $('#login-container').addClass("animate__animated animate__backInRight");
                                            });
                    }


//                    alert('글이 등록되었습니다');
//                    window.location.href = '/';
                }).fail(function (error) {
                    $('#login-container').fadeOut(300, function () {
                    window.location.href = '/signup';
//                                            $(this).html(`
//                                                <div class="title">회원가입 화면이다.</div>
//                                                          <div class="subtitle">welcome Back, User!</div>
//                                                          <div class="email-label">Password</div>
//                                                          <form action="">
//                                                            <div class="input-container">
//                                                              <input
//                                                                  type="password"
//                                                                  id="email"
//                                                                  name="email"
//                                                                  placeholder="🔒 Enter your password"
//                                                                />
//                                                            </div>
//                                                            <div class="forgot">password forget?</div>
//                                                            <div class="continue-button">
//                                                              <button class="button">Log in</button>
//                                                            </div>
//                                                        </form>
//                                                        <div class="social-login-container">
//                                                          <span class="continue-with">or continue with</span>
//                                                          <div class="social-login-buttons">
//                                                            <!-- 소셜 로그인 이미지 -->
//                                                            <button class="social-login-button">
//                                                              <img src="social_image_1.png" alt="Social Login 1" />
//                                                            </button>
//                                                            <button class="social-login-button">
//                                                              <img src="social_image_2.png" alt="Social Login 2" />
//                                                            </button>
//                                                            <button class="social-login-button">
//                                                              <img src="social_image_3.png" alt="Social Login 3" />
//                                                            </button>
//                                                            <button class="social-login-button">
//                                                              <img src="social_image_4.png" alt="Social Login 4" />
//                                                            </button>
//                                                          </div>
//                                                        </div>
//                                            `).fadeIn(300);
//                                            $('#login-container').addClass("animate__animated animate__backInRight");
                                        });
                    alert(JSON.stringify(error));
                });
    }
}
main.init();