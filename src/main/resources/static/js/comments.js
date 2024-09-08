$(document).ready(function() {
  const $commentText = $('#comment-text');
  const $commentButton = $('#comment-button');
  const $commentDivider = $('.comment-divider');
  const $commentsSection = $('#comments-section');

  $commentText.on('focus', function() {
      $commentDivider.css('border-top-width', '2px');
  });

  $commentText.on('blur', function() {
      if ($(this).val().trim() === '') {
          $commentDivider.css('border-top-width', '1px');
      }
  });

  $commentText.on('input', function() {
      if ($(this).val().trim() !== '') {
          $commentButton.prop('disabled', false).css({
              'background-color': '#065fd4',
              'color': '#ffffff'
          });
      } else {
          $commentButton.prop('disabled', true).css({
              'background-color': 'transparent',
              'color': '#909090'
          });
      }
  });
  function submitComment(data, parentId = null) {
    $.ajax({
        url : '/api/comments',
        method : 'post',
        contentType : 'application/json',
        data : JSON.stringify(data),
        success : function(response) {
            loadComments();
            console.log("성공");
        },
        error : function(xhr, status, error) {
            console.log("실패", error);
        }
    });
  }

  function loadComments() {
    $.ajax({
        url : `/api/comments?travelId=${$('#travel-id').val()}`,
        method : 'get',
        success : function(comments) {
            renderComments(comments);
            console.log("성공");
        },
        error : function(xhr, status, error) {
            console.log("댓글 불러오기 실패", error);
        }
    });
  }

  function renderComments(comments) {
      $commentsSection.empty(); // 기존 댓글을 모두 지웁니다.

      if (comments.length === 0) {
          $commentsSection.html('<div class="no-comments">등록된 댓글이 없습니다.</div>');
      } else {
          comments.forEach(comment => {
              const commentElement = $(`
                  <div class="comment">
                      <div class="comment-header">
                          <input id="comment-id" type="hidden" value=${comment.id}>
                          <span class="comment-author">${comment.author}</span>
                          <span class="comment-date">${comment.modifiedDate}</span>
                      </div>
                      <div class="comment-content">${comment.content}</div>
                      <div class="comment-actions">
                          <button class="like-button">👍</button>
                          <button class="dislike-button">👎</button>
                      </div>
                  </div>
              `);
              $commentsSection.append(commentElement);
          });
      }
    }

  $('#comment-button').on('click', function () {
      const data = {
          travelId : $('#travel-id').val(),
          content : $('#comment-text').val(),
          parentId : null,
          ratingValue : 5
      }
      submitComment(data);
      $commentText.val('');
  });
  // 여기에 댓글 로드 및 제출 로직을 추가할 수 있습니다.
});


loadComments();