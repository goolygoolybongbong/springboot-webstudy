const index = {
    init: function () {
        const _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function (){
            _this.delete();
        });
    },
    save: function () {
        const data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록됐습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },
    update: function () {
        const data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        const id = $('#id').val();

        $.ajax({
            type: 'PATCH',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정됐습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        const id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8'
        }).done(function () {
            alert('글이 삭제됐습니다.');
            window.location.href = '/';
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    }
};

index.init()