// 显示删除题目弹窗
function showDeleteModal(event, element) {
    event.stopPropagation();
    const questionItem = element.closest('.question-item');
    currentExamQuestionId = questionItem.getAttribute('data-id');
    document.getElementById('deleteQuestionModal').classList.add('active');
}

function hideDeleteModal() {
    document.getElementById('deleteQuestionModal').classList.remove('active');
}

// 删除题目
function deleteAllQuestions() {
    fetch('/deleteAllItems', {
        method: 'POST'
    }).then(r => {
        // 检查响应是否成功
        if (!r.ok) {
            throw new Error('Network response was not ok');
        }
        // 刷新页面
        location.reload();
    }).catch(error => {
        // 处理可能的错误
        console.error('There was a problem with the fetch operation:', error);
    });
}

// 显示/隐藏删除全部题目弹窗
function showDeleteAllModal() {
    document.getElementById('deleteAllQuestionsModal').classList.add('active');
}
function hideDeleteAllModal() {
    document.getElementById('deleteAllQuestionsModal').classList.remove('active');
}

// 显示/隐藏发布考试弹窗
function showPublishExamModal() {
    document.getElementById('publishExamModal').classList.add('active');
}
function hidePublishExamModal() {
    document.getElementById('publishExamModal').classList.remove('active');
}

// 发布考试
function publishExam() {
    fetch('/teacherPublishExam', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
        },
        body: `examId=${examId}&startTime=${startTime}&endTime=${endTime}&duration=${duration}`
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('考试已成功发布');
                window.location.reload();
            } else {
                alert(data.message || '发布考试失败');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('发布考试时发生错误');
        });
}

