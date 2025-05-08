// 当前选中的题目ID
let currentQuestionId = null;
let currentExamQuestionId = null;
const examId = [[${exam.id}]];

// 显示题目详情
function showQuestionDetail(event, element) {
    event.stopPropagation();
    const questionItem = element.closest('.question-item');
    currentQuestionId = questionItem.getAttribute('data-question-id');
    currentExamQuestionId = questionItem.getAttribute('data-id');

    fetch('/teacherGetQuestionDetail?id=' + currentQuestionId)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                renderQuestionDetail(data.question);
                document.getElementById('questionDetailModal').classList.add('active');
            } else {
                alert(data.message || '获取题目详情失败');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('获取题目详情时发生错误');
        });
}

function hideQuestionDetailModal() {
    document.getElementById('questionDetailModal').classList.remove('active');
}

// 渲染题目详情
function renderQuestionDetail(question) {
    const detailContent = document.getElementById('questionDetailContent');
    let html = `
            <div class="question-detail-section">
                <div class="detail-title">
                    <i class="fas fa-question-circle icon"></i>
                    题目内容
                </div>
                <div class="detail-content">${question.content}</div>
            </div>
        `;

    if (question.type === 'single_choice' || question.type === 'multiple_choice') {
        html += `
                <div class="question-detail-section">
                    <div class="detail-title">
                        <i class="fas fa-list-ol icon"></i>
                        题目选项
                    </div>
                    <div class="options-list">
            `;

        question.options.forEach((option, index) => {
            const letter = String.fromCharCode(65 + index);
            const isCorrect = question.correctAnswers.includes(letter);
            html += `
                    <div class="option-item ${isCorrect ? 'correct-option' : ''}">
                        <div class="option-letter">${letter}.</div>
                        <div class="option-content">${option}</div>
                    </div>
                `;
        });

        html += `</div></div>`;
    }

    if (question.type === 'programming') {
        html += `
                <div class="question-detail-section">
                    <div class="detail-title">
                        <i class="fas fa-code icon"></i>
                        初始代码
                    </div>
                    <div class="code-block">${question.templateCode || '无'}</div>
                </div>
                <div class="question-detail-section">
                    <div class="detail-title">
                        <i class="fas fa-vial icon"></i>
                        测试用例
                    </div>
                    <div class="detail-content">${question.testCases || '无'}</div>
                </div>
            `;
    }

    if (question.analysis) {
        html += `
                <div class="question-detail-section">
                    <div class="detail-title">
                        <i class="fas fa-lightbulb icon"></i>
                        题目解析
                    </div>
                    <div class="detail-content">${question.analysis}</div>
                </div>
            `;
    }

    detailContent.innerHTML = html;
    document.getElementById('detailModalTitle').textContent = question.title;
}

// 选择题目
function selectQuestion(element) {
    // 移除之前选中的样式
    document.querySelectorAll('#questionSearchResults .question-item').forEach(item => {
        item.style.backgroundColor = '';
    });

    // 添加选中样式
    element.style.backgroundColor = 'rgba(9, 132, 227, 0.1)';
    currentQuestionId = element.getAttribute('data-id');
}

// 添加题目到试卷
function addQuestionToExam() {
    if (!currentQuestionId) {
        alert('请先选择题目');
        return;
    }

    const score = document.getElementById('questionScoreInput').value;
    if (!score || score <= 0) {
        alert('请输入有效的分值');
        return;
    }

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
                window.location.reload();
            } else {
                alert(data.message || '添加题目失败');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('添加题目时发生错误');
        });
}

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

// 删除全部题目
function deleteAllQuestions() {
    // 获取 CSRF Token（如果后端启用了 CSRF 保护）
    const csrfToken = document.querySelector('input[name="_csrf"]').value;

    // 发送 AJAX 请求
    $.ajax({
        url: '/deleteAllItems', // 后端处理删除的接口路径
        type: 'POST',
        data: {
            _csrf: csrfToken // 如果后端启用了 CSRF 保护，需要传递 CSRF Token
        },
        success: function(response) {
            // 处理后端返回的结果
            if (response === 1) {
                // 删除成功
                alert('所有项目已成功删除');
                window.location.reload(); // 刷新页面（可选）
            } else {
                // 删除失败
                alert('删除失败，返回值为：' + response);
            }
        },
        error: function(xhr, status, error) {
            // 处理请求失败的情况
            console.error('Error:', error);
            alert('删除过程中发生错误');
        }
    });
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