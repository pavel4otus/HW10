var ajaxBooksGet    = '/rest/books/get';
var ajaxBooksDelete = '/rest/books/delete';
var ajaxBooksSave   = '/rest/books/save';
var ajaxBooksList   = '/rest/books/list';

var ajaxAuthorsGet    = '/rest/authors/get';
var ajaxAuthorsSave   = '/rest/authors/save';
var ajaxAuthorsDelete = '/rest/authors/delete/';
var ajaxAuthorsList   = '/rest/authors/list';

var ajaxGenresGet    = '/rest/genres/get';
var ajaxGenresSave   = '/rest/genres/save';
var ajaxGenresDelete = '/rest/genres/delete';
var ajaxGenresList   = '/rest/genres/list';

var ajaxCommentsList = '/rest/comments/list';
var ajaxCommentsSave = '/rest/comments/save';


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
// общая функция удаления
//
function commonDeleteRow( theUrl, theId){
    if (confirm( 'Удалить строку ?')) {
        $.ajax({ url: theUrl,
            type: "DELETE",
            data: { "id" : theId}
        })
            .done(function ( data) {
                // context.updateTable();
                // successNoty("common.deleted");
            })
            .fail( failMessage)
    }
}

function addCommomRow( theType) {
    $( '#edit' + theType + 'Row').find(":input").val("");
    $( '#edit' + theType + 'Row').modal();
}



//
// обновление для авторов и жанров
//
function commonUpdateRow( theUrl, theId, theType) {
    $.get( theUrl, { "id": theId}
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


function commonSaveRow( theUrl, aData, theType){
    $.ajax({
        url:theUrl,
        type:"POST",
        data: JSON.stringify( aData),
        contentType:"application/json; charset=utf-8",
        dataType:"jsonp"})
        .done(function () {
            table = $( '#' + theType + '-table').DataTable();
            table.ajax.reload();
            $( '#edit' + theType +  'Row').modal( "hide");
        })
        .fail( failMessage);
}

//
// авторы
//
function updateAuthorRow( authorId){
    commonUpdateRow( ajaxAuthorsGet, authorId, 'Author');
}

function deleteAuthorRow( authorId){
    commonDeleteRow( ajaxAuthorsDelete, authorId);
}

function saveAuthorRow(){
    commonSaveRow( ajaxAuthorsSave(),
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
    commonUpdateRow( ajaxGenresGet, genreId, 'Genre');
}

function saveGenreRow(){
    commonSaveRow( ajaxGenresSave, { 'id': $( '#idGenre').val(), 'name': $( '#nameGenre').val()}, 'Genre');
}


function deleteGenreRow( authorId){
    commonDeleteRow( ajaxGenresDelete, authorId);
}

function addGenreRow() {
    addCommomRow( 'Genre');
}

//
// книги
//
function updateBookRow( bookId){
    commonUpdateRow( ajaxBooksGet, bookId, 'Book');
}

function deleteBookRow( authorId){
    commonDeleteRow( ajaxBooksDelete, authorId);
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

    commonSaveRow( ajaxBooksSave, book, 'Book');
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

        $.get( ajaxCommentsList, { "id": bookId} )
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
    commonSaveRow( ajaxCommentsSave,
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
            url    : ajaxBooksList,
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
            url    : ajaxGenresList,
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
            url    : ajaxAuthorsList,
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

