document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 阻止表单默认提交行为

    const formData = new FormData(this);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    fetch('/doregister', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            if (data.message) {
                alert(data.message); // 注册成功
                window.location.href = "/"; // 跳转到主页
            } else if (data.errorMessage) {
                const errorDiv = document.getElementById('errorMessage');
                const errorText = document.getElementById('errorText');
                errorText.textContent = data.errorMessage; // 设置错误信息
                errorDiv.style.display = 'block'; // 显示错误框
            }
        })
        .catch(error => {
            console.error('注册失败:', error);
            alert('注册失败，请稍后再试！');
        });
});