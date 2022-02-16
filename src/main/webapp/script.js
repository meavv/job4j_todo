function sendGreeting() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_todo/greet',
        data: JSON.stringify({
            description: $('#description').val()
        }),
        dataType: 'json'
    }).done(function (data) {
        $('#descriptionList table:last').append(`<tr>
                    <td>${item.description}</td>
                     <td><input type="checkbox"></td>
                     </tr>`)
    }).fail(function (err) {
        console.log(err);
    });
}

function changeStatus() {
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:8080/job4j_todo/changeStatus',
        data: JSON.stringify({
            description: $('#ids').val()
        }),
        dataType: 'json'
    }).fail(function (err) {
        console.log(err);
    });
}

$(document).ready(function() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_todo/greet',
        dataType: 'json'
    }).done(function (data) {
        for (var item of data) {
            if (item.done !== true) {
                $('#descriptionList table:last').append(
                    `<tr>
                    <td id="ids">${item.description}</td>
                     <td><input type="checkbox" onclick="changeStatus()"></td>
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
        url: 'http://localhost:8080/job4j_todo/greet',
        dataType: 'json'
    }).done(function (data) {
        if ($("#check").prop('checked')) {
            for (var item of data) {
                if (item.done === true) {
                    $('#descriptionList table:last').append(
                        `<tr>
                    <td>${item.description}</td>
                     <td><input type="checkbox" checked onclick="changeStatus()"></td>
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