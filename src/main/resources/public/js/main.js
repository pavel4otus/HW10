var ajaxBooks    = '/rest/books';
var ajaxAuthors  = '/rest/authors';
var ajaxGenres   = '/rest/genres';
var ajaxComments = '/rest/comments';


//
// общая функция сообщения об ошибке
//
function failMessage( xhr, status, errorThrown ) {
    alert( "Sorry, there was a problem!" );
    console.log( "Error: " + errorThrown );
    console.log( "Status: " + status );
    console.dir( xhr );
}

//
// обновление данных в таблице
//
function commonReloadTable( theType) {
    console.log(theType + " reloading");
    table = $( '#' + theType + '-table').DataTable();
    table.ajax.reload();
    console.log(theType + "reloaded");
}

//
// общая функция удаления
//
function commonDeleteRow( theUrl, theId, theType){
    if (confirm( 'Удалить строку ?')) {
        $.ajax({ url: theUrl,
            type: "DELETE",
            data: { "id" : theId}
        })
            .done(function ( data) {
                commonReloadTable( theType);
            })
            .fail( failMessage)
    }
}

//
// добавляем строку
//
function addCommomRow( theType) {
    $( '#edit' + theType + 'Row').find(":input").val("");
    $( '#edit' + theType + 'Row').modal();
}



//
// обновление для авторов и жанров
//
function commonUpdateRow( theUrl, theId, theType) {
    $.get( theUrl + "/" + theId
    )
        .done(function ( data) {
            // context.updateTable();
            // successNoty("common.deleted");
            form = $( '#details' + theType + 'Form');
            $.each(data, function (key, value) {
                form.find("input[name='" + key + theType + "']").val(value);
                if (theType == 'Book' && key == 'author'){
                    form.find("input[name='authoridBook']").val(value.id);
                }
                if (theType == 'Book' && key == 'genre'){
                    form.find("input[name='genreidBook']").val(value.id);
                }
            });
            $( "#edit" + theType + "Row").modal();
        })
        .fail( failMessage);

}

//
// запиь данных в СУБД и рефреш таблицы
//
function commonSaveRow( theUrl, aData, theType){
    $.ajax({
        url:theUrl,
        type:"POST",
        data: JSON.stringify( aData),
        contentType:"application/json; charset=utf-8",
        dataType:"jsonp"})
        .done(function () {
            $( '#edit' + theType +  'Row').modal( "hide");
            if (theType == 'Comment') {
                showComments(  $( '#idCommentBook').val());
            }
            else
            {
                commonReloadTable(theType);
            }
        })
        .fail( failMessage);
}

//
// авторы
//
function updateAuthorRow( authorId){
    commonUpdateRow( ajaxAuthors, authorId, 'Author');
}

function deleteAuthorRow( authorId){
    commonDeleteRow( ajaxAuthors, authorId, 'Author');
}

function saveAuthorRow(){
    commonSaveRow( ajaxAuthors,
        { 'id': $( '#idAuthor').val(),
            'name': $( '#nameAuthor').val(),
            'birthDate': $( '#birthDateAuthor').val(),
            'phone': $( '#phoneAuthor').val(),
            'email': $( '#emailAuthor').val(),
            'address': $( '#addressAuthor').val()
        }, 'Author');
}

function addAuthorRow() {
    addCommomRow( 'Author');
}

//
// жанры
//
function updateGenreRow( genreId){
    commonUpdateRow( ajaxGenres, genreId, 'Genre');
}

function saveGenreRow(){
    commonSaveRow( ajaxGenres, { 'id': $( '#idGenre').val(), 'name': $( '#nameGenre').val()}, 'Genre');
}

function deleteGenreRow( authorId){
    commonDeleteRow( ajaxGenres, authorId, 'Genre');
}

function addGenreRow() {
    addCommomRow( 'Genre');
}

//
// книги
//
function updateBookRow( bookId){
    commonUpdateRow( ajaxBooks, bookId, 'Book');
}

function deleteBookRow( bookId){
    commonDeleteRow( ajaxBooks, bookId, 'Book');
}

function saveBookRow(){
    book =
        { 'id'              : $( '#idBook').val(),
            'name'            : $( '#nameBook').val(),
            'genre'           : { 'id': $( '#genreidBook').val(),  'name' : '' },
            'author'          : { 'id': $( '#authoridBook').val(), 'name' : '', 'birthDate': '',
                'phone': '', 'email': '', 'address': '' },
            'isbn'            : $( '#isbnBook').val(),
            'publishingHouse' : $( '#publishingHouseBook').val(),
            'publicationYear' : $( '#publicationYearBook').val(),
            'publicationPlace': $( '#publicationPlaceBook').val()};

    commonSaveRow( ajaxBooks, book, 'Book');
}

function addBookRow() {
    addCommomRow( 'Book');
}

//
// комментарии
//
function showComments( bookId){
    console.log( 'showComments.id' + bookId);
    comments = $( '#comments');
    comments.empty();
    $( '#idCommentBook').val( bookId);

        $.get( ajaxComments + "/" + bookId )
        .done(function ( data) {
            console.log( 'получено' + JSON.stringify( data));
            $.each(data, function (key, value) {
                comments.append(
                    '<a href="#" class="list-group-item list-group-item-action">' +
                    '<div class="d-flex w-100 justify-content-between">' +
                    '<h5 class="mb-1">' + value.name + '</h5>' +
                    '<small>' + value.dateTime + '</small>' +
                    '</div>' +
                    '<p class="mb-1">' + value.comment + '</p>' +
                    '</a>');
            });
        })
        .fail( failMessage);
}

function addCommentRow() {
    $( "#editCommentRow").modal();
}

function saveCommentRow() {
    commonSaveRow( ajaxComments,
        { 'idBook' : $( '#idCommentBook').val(),
          'name'   : $( '#nameComment').val(),
          'comment': $( '#commentComment').val()
        }, 'Comment');
}


//
// инициализация таблиц
//
function booksInit(){

    $('#Book-table').DataTable({
        processing: true,
        serverSide: true,
        searching : true,
        ordering  : true,
        info      : false,
        retrieve: true,
        language  : {
            lengthMenu  : "Показывать _MENU_ записей на странице",
            zeroRecords : "Все уж показали",
            info        : "Страница _PAGE_ из _PAGES_",
            infoEmpty   : "Нет записей",
            infoFiltered: "(выбрано _MAX_ записей)"
        },
        ajax: {
            url    : ajaxBooks,
            type   : 'GET',
            dataSrc: ''
        },
        columns:[
            { data: 'id'},
            { data: 'name'},
            { data: 'author.name'},
            { data: 'genre.name'},
            { data: 'isbn'},
            { data: 'publishingHouse'},
            { data: 'publicationYear'},
            { data: 'publicationPlace'},
            { render        : function (data, type, row, meta) {    // edit
                    return "<a onclick='updateBookRow(" + row.id + ");'><span class='far fa-edit'></span></a>";
                },
                defaultContent: "",
                orderable     : false
            },
            {
                render        : function (data, type, row, meta) {  // delete
                    return "<a onclick='deleteBookRow(" + row.id + ");'><span class='fas fa-trash'></span></a>";
                },
                defaultContent: "",
                orderable     : false
            },
            {
                render        : function (data, type, row, meta) {  // delete
                    return "<a data-toggle=\"collapse\" href=\"#collapseComments\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseComments\" onclick='showComments(" + row.id + ");'><span class='far fa-comment-dots'></span></a>";
                },
                defaultContent: "",
                orderable     : false
            }
        ]
    }) ;
}

function genresInit(){
    $('#Genre-table').DataTable({
        processing: true,
        serverSide: true,
        info      : false,
        searching : false,
        paging    : false,
        retrieve: true,
        language  : {
            lengthMenu  : "Показывать _MENU_ записей на странице",
            zeroRecords : "Все уж показали",
            info        : "Страница _PAGE_ из _PAGES_",
            infoEmpty   : "Нет записей",
            infoFiltered: "(выбрано _MAX_ записей)"
        },
        ajax: {
            url    : ajaxGenres,
            type   : 'GET',
            dataSrc: ''
        },
        columns:[
            { data: 'id'},
            { data: 'name'},
            { render        : function (data, type, row, meta) {    // edit
                    return "<a onclick='updateGenreRow(" + row.id + ");'><span class='far fa-edit'></span></a>";
                },
                defaultContent: "",
                orderable     : false
            },
            {
                render        : function (data, type, row, meta) {  // delete
                    return "<a onclick='deleteGenreRow(" + row.id + ");'><span class='fas fa-trash'></span></a>";
                },
                defaultContent: "",
                orderable     : false
            }

        ]
    });
}
function authorsInit(){

    $('#Author-table').DataTable({
        processing: true,
        serverSide: true,
        info      : false,
        retrieve  : true,
        language  : {
            lengthMenu  : "Показывать _MENU_ записей на странице",
            zeroRecords : "Все уж показали",
            info        : "Страница _PAGE_ из _PAGES_",
            infoEmpty   : "Нет записей",
            infoFiltered: "(выбрано _MAX_ записей)"
        },
        ajax: {
            url    : ajaxAuthors,
            type   : 'GET',
            dataSrc: ''
        },
        columns:[
            { data: 'id'},
            { data: 'name'},
            { data: 'birthDate'},
            { data: 'phone'},
            { data: 'email'},
            { data: 'address'},
            { render        : function (data, type, row, meta) {    // edit
                    return "<a onclick='updateAuthorRow(" + row.id + ");'><span class='far fa-edit'></span></a>";
                },
                defaultContent: "",
                orderable     : false
            },
            {
                render        : function (data, type, row, meta) {  // delete
                    return "<a onclick='deleteAuthorRow(" + row.id + ");'><span class='fas fa-trash'></span></a>";
                },
                defaultContent: "",
                orderable     : false
            }
        ]
    });
}

//
// действия при загрузке документа
//
$(document).ready(function() {

    genresInit();
    authorsInit();
    booksInit();

    $('#myTab li:last-child a').tab('show');

} );

