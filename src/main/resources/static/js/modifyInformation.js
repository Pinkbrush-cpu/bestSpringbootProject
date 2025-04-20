// 下拉菜单功能
document.querySelectorAll('.menu-item').forEach(item => {
    const link = item.querySelector('.menu-link');

    link.addEventListener('click', function(e) {
        e.preventDefault();

        // 如果点击的是当前激活的菜单项，则关闭它
        if (item.classList.contains('active')) {
            item.classList.remove('active');
        } else {
            // 否则，先关闭所有菜单项，再打开当前点击的
            document.querySelectorAll('.menu-item').forEach(i => {
                i.classList.remove('active');
            });
            item.classList.add('active');
        }
    });
});

// 移动端菜单切换
const menuToggle = document.querySelector('.menu-toggle');
const menu = document.querySelector('.menu');

menuToggle.addEventListener('click', function() {
    menu.classList.toggle('active');
});

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