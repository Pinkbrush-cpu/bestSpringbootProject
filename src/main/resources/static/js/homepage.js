// 搜索框聚焦效果
const searchInput = document.querySelector('.search-input');
const searchIcon = document.querySelector('.search-icon');

searchInput.addEventListener('focus', function() {
    searchIcon.style.color = 'var(--accent-color)';
});

searchInput.addEventListener('blur', function() {
    searchIcon.style.color = 'var(--secondary-color)';
});