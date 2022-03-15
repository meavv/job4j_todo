function sendGreeting() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8081/job4j_todo/greet',
        data: JSON.stringify({
            description: $('#description').val(),
            categories: $('#cIds').val()
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
    }).done()
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
                let cat = "";
                let arrayLength = item.categories.length;
                if (arrayLength !== 0) {
                    for (let i = 0; i < arrayLength; i++) {
                        cat = cat + item.categories[i].name
                        if (i !== arrayLength - 1) {
                            cat = cat + " / "
                        }
                    }
                }
                $('#descriptionList table:last').append(
                    `<tr>
                    <td>${item.description}</td>
                     <td>
                     <input type="checkbox" onchange="changeStatus(${item.id})">
                     </td>
                     <td>${item.user.name}</td>
                     <td>${cat}</td>
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
                     <td>${item.user.name}</td>
                     <td>${item.categories[0].name}</td>
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
