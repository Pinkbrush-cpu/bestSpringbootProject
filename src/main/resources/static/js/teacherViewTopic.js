
// 搜索功能
document.getElementById('searchInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        const keyword = this.value;
        window.location.href = `/teacherQuestionBank?keyword=${encodeURIComponent(keyword)}`;
    }
});

// 初始化时检查URL中的搜索关键词
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const keyword = urlParams.get('keyword');
    if (keyword) {
        document.getElementById('searchInput').value = keyword;
    }
});