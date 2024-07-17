$(document).ready(function() {
  const $commentText = $('#comment-text');
  const $commentButton = $('#comment-button');
  const $commentDivider = $('.comment-divider');

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

  // 여기에 댓글 로드 및 제출 로직을 추가할 수 있습니다.
});

function loadComments() {
  // AJAX를 사용하여 서버에서 댓글을 로드하는 로직
  // 예시 데이터
  const comments = [
      { author: "사용자1", date: "1일 전", content: "좋은 내용이네요!", likes: 35 },
      { author: "사용자2", date: "2일 전", content: "감사합니다.", likes: 12 }
  ];

  let commentsHtml = '';
  comments.forEach(comment => {
      commentsHtml += `
          <div class="comment">
              <div class="comment-header">
                  <span class="comment-author">${comment.author}</span>
                  <span class="comment-date">${comment.date}</span>
              </div>
              <div class="comment-content">${comment.content}</div>
              <div class="comment-actions">
                  <button class="like-button">👍 ${comment.likes}</button>
                  <button class="dislike-button">👎</button>
              </div>
          </div>
      `;
  });

  $('#comments-section').html(commentsHtml);
}

// 페이지 로드 시 댓글 불러오기
loadComments();