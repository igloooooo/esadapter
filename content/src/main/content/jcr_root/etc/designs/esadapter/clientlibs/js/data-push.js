
jQuery(document).ready(function () {
    var source = $("#asset-list-template").html();
    var template = Handlebars.compile(source);

    $("#pushDataBtn").click(function () {
        cleanResult();
        $.ajax({
            type: "POST",
            url: "/bin/esadapter/export?type=run&path=" + $("#path-text").val(),
            contentType: 'application/json',
            success: function (e) {
                populateResultList(e);
            }
        });
    });

    $("#dryRunBtn").click(function () {
        cleanResult();
        $.ajax({
            type: "POST",
            url: "/bin/esadapter/export?type=dry&path=" + $("#path-text").val(),
            contentType: 'application/json',
            success: function (e) {
                populateResultList(e);
            }
        });
    });

    function populateResultList(e) {
        $('#export-result').append(template(e));
        $.gritter.add({
            title: 'Data Export Completed',
            text: 'Data Export Completed',
            class_name: 'gritter-info gritter-center'
        })
    }

    function cleanResult() {
        $('#export-result').empty();
    }
});