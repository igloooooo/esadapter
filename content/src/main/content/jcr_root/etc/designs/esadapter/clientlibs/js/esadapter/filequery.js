Handlebars.registerHelper('resultDetails', function(context) {
    return JSON.stringify(context);
});

jQuery(document).ready(function () {
    var container = document.getElementById("result-contain");
    var resultWindowConfig = {'mode':'view'};
    var editor = new JSONEditor(container,resultWindowConfig);
    $("#search").click(function () {
        $.ajax({
            type: "GET",
            url: "/bin/esadapter/example/attachmentsearch?q=" + $('#queryContent').val(),
            contentType: 'application/json',
            success: function (e) {
                var json = e;
                editor.set(json.hits);
                $("#time-took").val(json.took);
                $("#total-result").val(json._shards.total);
                $.gritter.add({
                    title: 'Query Completed',
                    text: 'Your query took ' + json.took + "ms.",
                    class_name: 'gritter-info gritter-center'
                });
            }
        });
    });
});