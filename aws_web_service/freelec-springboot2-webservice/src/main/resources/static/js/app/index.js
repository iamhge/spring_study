// p141, p153
// var main = {...} 형태의 선언
// * var index란 객체를 만들어 해당 객체에서 필요한 모든 function을 선언한다.
// * 이렇게 하면 index 객체 안에서만 function이 유효하기 때문에 다른 JS와 겹칠 위험이 사라진다.
var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        // p154 - 1
        // $('#btn-update').on('click') : btn-update란 id를 가진 HTML element에 click 이벤트가 발생할 때 update function 실행
        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            // 글 등록이 성공하면 메인페이지(/)로 이동한다.
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT', // PostsApiController에 있는 API에서 @PutMapping으로 선언했기 때문에 PUT 사용
            url: '/api/v1/posts/'+id, // 어느 게시글을 수정할지 URL Path로 구분하기 위해 Path에 id 추가
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();