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
            data: JSON.stringify(JSON.parse(queryContent.get())),
            contentType: 'application/json',
            success: function (e) {
                var json = JSON.parse(e);
                editor.set(json.hits);
                $("#time-took").val(json.took);
                $("#total-result").val(json._shards.total);
            }
        });
    });
});