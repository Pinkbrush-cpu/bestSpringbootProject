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
function deleteQuestion() {
    fetch('/teacherRemoveQuestionFromExam', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
        },
        body: `examQuestionId=${currentExamQuestionId}`
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('题目已从试卷中删除');
                window.location.reload();
            } else {
                alert(data.message || '删除题目失败');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('删除题目时发生错误');
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

// 搜索题目功能
document.getElementById('questionSearch').addEventListener('input', function() {
    const keyword = this.value.toLowerCase();
    document.querySelectorAll('#questionSearchResults .question-item').forEach(item => {
        const title = item.getAttribute('data-title').toLowerCase();
        const content = item.getAttribute('data-content').toLowerCase();
        if (title.includes(keyword) || content.includes(keyword)) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });
});