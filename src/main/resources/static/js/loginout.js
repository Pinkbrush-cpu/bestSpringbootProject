// 显示弹窗
function showLogoutModal() {
    document.getElementById('logoutModal').classList.add('active');
}

// 隐藏弹窗
function hideLogoutModal() {
    document.getElementById('logoutModal').classList.remove('active');
}

// 点击弹窗外部关闭
document.getElementById('logoutModal').addEventListener('click', function(e) {
    if(e.target === this) {
        hideLogoutModal();
    }
});