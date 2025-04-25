
// 密码强度检测
document.getElementById('newPassword').addEventListener('input', function() {
    const password = this.value;
    const strengthMeter = document.getElementById('strengthMeter');
    let strength = 0;

    // 长度检测
    if (password.length >= 8) strength += 1;
    if (password.length >= 12) strength += 1;

    // 复杂度检测
    if (/[A-Z]/.test(password)) strength += 1;
    if (/\d/.test(password)) strength += 1;
    if (/[^A-Za-z0-9]/.test(password)) strength += 1;

    // 更新强度条
    const width = strength * 20;
    strengthMeter.style.width = width + '%';

    // 更新颜色
    if (strength <= 2) {
        strengthMeter.style.backgroundColor = '#e63946'; // 弱
    } else if (strength <= 4) {
        strengthMeter.style.backgroundColor = '#f4a261'; // 中
    } else {
        strengthMeter.style.backgroundColor = '#2a9d8f'; // 强
    }
});

// 表单验证
document.getElementById('passwordForm').addEventListener('submit', function(e) {
    e.preventDefault();

    let isValid = true;
    const currentPassword = document.getElementById('currentPassword');
    const newPassword = document.getElementById('newPassword');
    const confirmPassword = document.getElementById('confirmPassword');

    // 验证当前密码
    if (currentPassword.value.trim() === '') {
        currentPassword.classList.add('is-invalid');
        isValid = false;
    } else {
        currentPassword.classList.remove('is-invalid');
    }

    // 验证新密码
    if (newPassword.value.length < 8) {
        newPassword.classList.add('is-invalid');
        isValid = false;
    } else {
        newPassword.classList.remove('is-invalid');
    }

    // 验证确认密码
    if (confirmPassword.value !== newPassword.value) {
        confirmPassword.classList.add('is-invalid');
        isValid = false;
    } else {
        confirmPassword.classList.remove('is-invalid');
    }

    // 如果所有验证通过
    if (isValid) {
        // 提交表单
        document.getElementById('passwordForm').submit();
    }
});