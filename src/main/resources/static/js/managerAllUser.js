// 页面加载完成后调用
window.onload = function() {
    axios.get('/modifyPermission')
        .then(response => {
            const users = response.data;
            const userList = document.getElementById('user-list');
            userList.innerHTML = '';
            users.forEach(user => {

            });
        })
        .catch(error => {
            console.error('请求失败:', error);
        });
};