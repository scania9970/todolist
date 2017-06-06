(function (window) {
	'use strict';
function chkNumCompleted(){
                var numberChecked = 0;
                var $this = $('.toggle')
                var todolist = $this.parents('.todo-list');
                var lis = todolist.children();
                //var numberChecked =0;
                $.each(lis,function(idx,data){
                    if($(data).attr('class') != 'completed'){
                        numberChecked += 1;
                    }
        })
                        $(".todo-count strong").text(numberChecked);

            }

function loadTasks(data) {
                $.each(data, function (idx, val) {
                    //console.log(idx)
                    if(val.completed==0){
                        $(".todo-list").prepend('<li>' + '<div class="view">' + '<input class="toggle" type="checkbox">'
                        + '<label>' + val.todo + '</label>'
                        + '<button class="destroy">' + '</button>'
                        + '</div>' + '<input class="edit" value='
                        + val.id + '>' + '</li>');
                    }else{
                        $(".todo-list").prepend('<li class=' + 'completed' +'>' + '<div class="view">' + '<input class="toggle" type="checkbox" checked>'
                        + '<label>' + val.todo + '</label>'
                        + '<button class="destroy">' + '</button>'
                        + '</div>' + '<input class="edit" value='
                        + val.id + '>' + '</li>');
                    }
                   chkNumCompleted();

                });
            }


$(document).ready(function () {
        //console.log('load');
        $.ajax({
            type: 'GET',
            url: '/tasks',
            dataType: 'JSON',
            success: function(data){
                loadTasks(data);
            }
        });
    });


$(".new-todo").keydown(function(event){

    if(event.which === 13){
        if($(this).val() === ''){
            return;
        }
        var inputData = {
            'id' : '',
            'todo' : '',
            'completed' : '',
            'date' : ''
        };
        inputData.todo = $(this).val();
        inputData.completed = 0;
        inputData.date = new Date();
        //console.log(inputData.todo);
        $.ajax({
            type : 'POST',
            url : '/insert',
            data : JSON.stringify(inputData),
            contentType : "application/json",
            dataType : 'JSON',
            success : function (data) {
                    $(".todo-list").prepend('<li>' + '<div class="view">'
                        + '<input class="toggle" type="checkbox">'
                        + '<label>' + data.todo + '</label>'
                        + '<button class="destroy">' + '</button>'
                        + '</div>' + '<input class="edit" value=' + data.id + '>'
                        + '</li>');
                        chkNumCompleted();
            }
        })
    }
})

$(document).on('click','.destroy',function(){
    var $this=$(this);
    var id = $(this).parent().siblings('.edit').val();
    $.ajax({
        type: 'DELETE',
        url :'/task/'+id,
        success : function(){
            //console.log('delete');
            $this.parents('li').remove();
            chkNumCompleted();
        }

    })
})

$(document).on('click','.toggle', function(){
    var $this = $(this).parents('li');
    if($(this).is(':checked')){
        var data = {
            'id' : $(this).parent().siblings('.edit').val(),
            'completed' : 1
        }
        //console.log(data.id);
        $.ajax({
            type:'POST',
            url : '/completed',
            data: JSON.stringify(data),
            contentType : "application/json",

            success: function(){
                $this.addClass('completed');
                chkNumCompleted();
            }
        })
    }else{
        var data = {
            'id' : $(this).parent().siblings('.edit').val(),
            'completed' : 0
        }
        $.ajax({
            type:'POST',
            url : '/completed',
            data: JSON.stringify(data),
            contentType : "application/json",

            success: function(){
                $this.removeClass();
                chkNumCompleted();
            }
        })
    }
})

$(document).on('click', '.filters a', function(e){
    e.preventDefault();
    var hash = this.hash;
    $(".todo-list").children().remove();
    //console.log(hash);
    //console.log(hash.split('#/')[1]);
    hash = hash.split('#/')[1];
    if(hash === ''){
        $.ajax({
            type : 'GET',
            url : '/filter/'+'all',
            success : function(data){
                loadTasks(data);
            }
        })
    }else if(hash === 'active'){
        $.ajax({
            type : 'GET',
            url : '/filter/'+hash,
            success : function(data){
                loadTasks(data)
            }
        })
    }else{
        $.ajax({
            type : 'GET',
            url : '/filter/'+hash,
            success : function(data){
                loadTasks(data)
            }
        })

    }
})


$(document).on('click', '.clear-completed', function(){
     var lis = $(".todo-list").children();
     var deleteList=[];
     var liList =[];
    $.each(lis,function(idx, data){
        if($(data).attr('class') === 'completed'){
            deleteList.push($(data).children('.edit').val());
            liList.push($(data));
        }
    })

     $.ajax({
        type:'POST',
        url :'/deleteCompleted',
        data : JSON.stringify(deleteList),
        contentType : "application/json",
        success : function(){
            //console.log('test');
            $.each($(liList), function(idx, data){
                data.remove();
            })

        }

    })
})

})(window);

/*
$.each(lis,function(idx,data){
                    if($(data).attr('class') != 'completed'){
                        numberChecked += 1;
                    }
        })*/