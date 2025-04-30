// 显示退出登录弹窗
function showLogoutModal() {
    document.getElementById('logoutModal').classList.add('active');
}

// 隐藏退出登录弹窗
function hideLogoutModal() {
    document.getElementById('logoutModal').classList.remove('active');
}

// 当前选中的题目ID和标题
let currentQuestionId = null;
let currentQuestionTitle = null;

// 显示添加到试卷弹窗
function showAddToExamModal(questionId, questionTitle) {
    currentQuestionId = questionId;
    currentQuestionTitle = questionTitle;
    document.getElementById('modalQuestionTitle').textContent = questionTitle;
    document.getElementById('addToExamModal').classList.add('active');
}

// 隐藏添加到试卷弹窗
function hideAddToExamModal() {
    document.getElementById('addToExamModal').classList.remove('active');
}

// 将题目添加到试卷
function addQuestionToExam() {
    const examId = document.getElementById('examSelect').value;
    const score = document.getElementById('questionScore').value;

    if (!examId) {
        alert('请选择试卷');
        return;
    }

    if (!score || score <= 0) {
        alert('请输入有效的分值');
        return;
    }

    // 这里可以使用fetch API提交表单
    fetch('/teacherAddQuestionToExam', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
        },
        body: `examId=${examId}&questionId=${currentQuestionId}&score=${score}`
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('题目已成功添加到试卷');
                hideAddToExamModal();
            } else {
                alert(data.message || '添加题目失败');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('添加题目时发生错误');
        });
}

// 搜索功能
document.getElementById('searchInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        const keyword = this.value;
        window.location.href = `/teacherQuestionBank?keyword=${encodeURIComponent(keyword)}`;
    }
});

// 初始化时检查URL中的搜索关键词
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const keyword = urlParams.get('keyword');
    if (keyword) {
        document.getElementById('searchInput').value = keyword;
    }
});