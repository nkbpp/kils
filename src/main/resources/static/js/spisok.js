$(document).ready(function () {

    $("body").on('click', 'a', function () {

        if ($(this).attr('id') == "add") {

            var param = "value=" + $("#addznach").val() +
                        "&type=" + $(this).attr('value');

            console.log(param);

            var url = "/kils/spisok/add" + "?" + param;
            location.href = url;

            /*$("#tablekonv").load("/kils/spisok/add", param, function () {
                $('select').formSelect();//селект с конвертами
            });*/
            return false;
        }

        if (this.getAttribute("name") == "del") {
            var id = $(this).attr('id');
            var type = $(this).attr('value');

            var param = "id=" + id +
                        "&type=" + type;

            console.log(param);
            if (confirm("Точно данные с id " +
                id + "?")) {
                var url = "/kils/spisok/del" + "?" + param;
                location.href = url;
            }
        }

    });

});