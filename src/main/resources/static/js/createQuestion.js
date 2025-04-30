// 根据题目类型显示不同的部分
document.getElementById('questionType').addEventListener('change', function() {
    const type = this.value;
    const optionsSection = document.getElementById('optionsSection');
    const correctAnswerGroup = document.getElementById('correctAnswerGroup');

    // 重置显示状态
    optionsSection.style.display = 'block';

    // 根据题目类型调整
    if (type === "简答题") {
        optionsSection.style.display = 'none';
        correctAnswerGroup.style.display = 'none';
    } else {
        correctAnswerGroup.style.display = 'block';
    }
});

// 添加选项
function addOption() {
    const optionsContainer = document.getElementById('optionsContainer');
    const optionCount = optionsContainer.children.length;
    const nextChar = String.fromCharCode(65 + optionCount); // A, B, C...

    const optionItem = document.createElement('div');
    optionItem.className = 'option-item';
    optionItem.innerHTML = `
            <input type="text" name="options" class="form-control option-input" placeholder="选项${nextChar}">
            <div class="option-actions">
                <button type="button" class="btn btn-danger" onclick="removeOption(this)"><i class="fas fa-trash"></i></button>
            </div>
        `;

    optionsContainer.appendChild(optionItem);

}

// 删除选项
function removeOption(button) {
    const optionItem = button.closest('.option-item');
    if (document.getElementById('optionsContainer').children.length > 2) {
        optionItem.remove();
    } else {
        alert('至少需要两个选项');
    }
}

// 在DOMContentLoaded事件监听器中添加
document.addEventListener('DOMContentLoaded', function() {
    // 分数验证
    document.getElementById('questionScore').addEventListener('change', function() {
        if (this.value < 1) {
            this.value = 1;
            alert('题目分值不能小于1');
        }
    });
});