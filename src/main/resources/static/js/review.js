function toggleCommentForm() {
    const commentForm = document.querySelector('.comment-form');
    commentForm.style.display = commentForm.style.display === 'none' ? 'block' : 'none';
}

function submitComment() {
    const textarea = document.querySelector('.comment-form textarea');
    const commentText = textarea.value;

    if (commentText.trim() === '') {
        alert('댓글을 입력하세요.');
        return;
    }

    const newComment = document.createElement('div');
    newComment.className = 'review';
    newComment.innerHTML = `
        <div class="review-main">
            <div class="content">${commentText}</div>
        </div>
        <div class="review-info">
            <div class="reviewer">익명</div>
            <div class="date">${new Date().toISOString().split('T')[0]}</div>
        </div>
    `;

    const reviewContainer = document.querySelector('.review-container');
    reviewContainer.insertBefore(newComment, reviewContainer.firstChild.nextSibling); // 첫 번째 리뷰 바로 위에 추가

    textarea.value = '';
    toggleCommentForm();
}