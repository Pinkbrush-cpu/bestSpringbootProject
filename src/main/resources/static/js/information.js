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