Handlebars.registerHelper('resultDetails', function(context) {
    return JSON.stringify(context);
});

jQuery(document).ready(function () {
    var container = document.getElementById("result-contain");
    var resultWindowConfig = {'mode':'view'};
    var editor = new JSONEditor(container,resultWindowConfig);
    var queryContent = new JSONEditor(document.getElementById("queryContent"), {'mode':'text'});
    $("#search").click(function () {
        $.ajax({
            type: "POST",
            url: "/bin/esadapter/queryexplain",
            data: JSON.stringify(queryContent.get()),
            contentType: 'application/json',
            success: function (e) {
                var json = JSON.parse(e);
                editor.set(json.hits);
                $("#time-took").val(json.took);
                $("#total-result").val(json._shards.total)
                $.gritter.add({
                    title: 'Query Success!',
                    text: 'Query Success',
                    class_name: 'gritter-info gritter-center'
                })
            },
            error: function(e) {
                $.gritter.add({
                    title: 'Query Error',
                    text: 'Can NOT query.',
                    class_name: 'gritter-error gritter-center'
                })
            }
        });
    });
});