<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>AJAX</title>
</head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
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
                     <td><input type="checkbox" checked></td>
                     </tr>`)
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
                    <td>${item.description}</td>
                     <td><input type="checkbox"></td>
                     </tr>`)
                }
            }
        }).fail(function (err) {
            console.log(err);
        });
    });

    function ChangeValueProduct() {
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
                     <td><input type="checkbox" checked></td>
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

</script>
<body>


<div class="container">
    <form>
        <div class="form-group">
            <label for="description">Добавить дело</label>
            <input type="text" class="form-control" id="description" aria-describedby="descriptionHelp"
                   placeholder="Enter description">
        </div>
        <button type="button" class="btn btn-primary" onclick="sendGreeting()">Submit</button>
        <input type="checkbox" id="check" onclick="ChangeValueProduct()">Показать все</input>
    </form>

    <br>
    Список дел:
    <ul id="descriptionList">
        <form>
            <table class="table" frame="vsides">
                <tr>
                    <th scope="col">Описание</th>
                    <th scope="col">Статус</th>
                </tr>
            </table>
        </form>
    </ul>
</div>
</body>

</html>