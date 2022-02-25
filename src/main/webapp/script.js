function sendGreeting() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8081/job4j_todo/greet',
        data: JSON.stringify({
            description: $('#description').val()
        }),
        dataType: 'json'
    }).done(location.reload()
    ).fail(function (err) {
        console.log(err);
    });
}

function changeStatus(id) {
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:8081/job4j_todo/changeStatus',
        data: JSON.stringify({
            id: id
        }),
        dataType: 'json'
    }).done(this.reloadGridData())
        .fail(function (err) {
        console.log(err);
    });
}

$(document).ready(function() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8081/job4j_todo/greet',
        dataType: 'json'
    }).done(function (data) {
        for (var item of data) {
            if (item.done !== true) {
                $('#descriptionList table:last').append(
                    `<tr>
                    <td>${item.description}</td>
                     <td>
                     <input type="checkbox" onchange="changeStatus(${item.id})">
                     </td>
                     </tr>`)
            }
        }
    }).fail(function (err) {
        console.log(err);
    });
});

function filter() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8081/job4j_todo/greet',
        dataType: 'json'
    }).done(function (data) {
        if ($("#check").prop('checked')) {
            for (var item of data) {
                if (item.done === true) {
                    $('#descriptionList table:last').append(
                        `<tr>
                    <td>${item.description}</td>
                     <td><input type="checkbox" checked onchange="changeStatus(${item.id})"></td>
                     </tr>`)
                }
            }
        } else {
            location.reload();
        }
    }).fail(function (err) {
        console.log(err);
    });
}