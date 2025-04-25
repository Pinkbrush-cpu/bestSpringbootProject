// 表单验证
document.getElementById('profileForm').addEventListener('submit', function(e) {
    e.preventDefault();

    // 简单验证示例
    let isValid = true;
    const email = document.getElementById('email');
    const phone = document.getElementById('phone');

    // 验证邮箱
    if (!email.value.includes('@')) {
        email.classList.add('is-invalid');
        isValid = false;
    } else {
        email.classList.remove('is-invalid');
    }

    // 验证手机号
    if (phone.value.length < 11) {
        phone.classList.add('is-invalid');
        isValid = false;
    } else {
        phone.classList.remove('is-invalid');
    }

    // 如果验证通过
    if (isValid) {
        alert('个人资料更新成功！');
        // 这里可以添加实际的表单提交逻辑
    }
});