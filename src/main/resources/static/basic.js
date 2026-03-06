function loadCustomer() {
    $.ajax({
        url: '/api/customers/'+cid,
        method: 'GET',

        success:function(c){
            //dto로 전달받은 값을 c로 받아서 c에 변수들을 각 id에 전달
            $('#headerCid').text(c.cid);
            $('#avatarChar').text(c.name?c.name.charAt(0):'?');
            $('#custName').text(c.name || '');
            const gradeClass = gradeColorMap[c.grade] || 'badge-normal';
            $('#gradeBadge').removeClass()
                .addClass('badge grade-badge rounded-pill '+gradeClass).text(c.grade || '');
            $('#custEmail').text(c.email || '');
            $('#custPhone').text(c.phone || '');
            $('#custCompany').text(c.company || '');
            $('#custAddress').text(c.address || '');
            $('#custRegDate').text(c.regDate || '');
            $('#editBtnTop').attr('href', '/customer/edit/'+c.cid);
            $('#editBtnBottom').attr('href', '/customer/edit/'+c.cid);
            $('#deleteModalName').text(c.name);
            if(c.memo) {
                $('#custMemo').text(c.memo);
                $('#memoSection').show();
            }
            $('#loadingArea').hide(); //읽기대기 숨김
            $('#customerCard').css('display', 'flex'); //개인정보 표시
            $('#bottomBtns').css('display', 'flex'); //하단버튼 영역 표시
        },
        error:function(xhs){
            $('#loadingArea').html('<p class="text-danger">고객 정보를 불러올 수 없습니다.</p>')
        }
    });
}

//삭제대화상자 출력
function showDeleteModal() {
    deleteModal.show();
}