jQuery(document).ready(function () {


    function init() {
        getTopKeywords();
        getSlowQuery();
    }

    function getSlowQuery() {
        var source = $("#slowquery-list-template").html();
        var template = Handlebars.compile(source);
        $.ajax({
            type: "GET",
            url: "/bin/esadapter/history/slowquery",
            contentType: 'application/json',
            success: function (e) {
                $('#slow-query-contain').empty();
                $('#slow-query-contain').append(template(JSON.parse(e)));
            }
        });
    }

    function getTopKeywords() {
        var source = $("#keywords-list-template").html();
        var template = Handlebars.compile(source);
        $.ajax({
            type: "GET",
            url: "/bin/esadapter/history/keyword",
            contentType: 'application/json',
            success: function (e) {
                $('#keywords-contain').empty();
                $('#keywords-contain').append(template(JSON.parse(e)));
            }
        });
    }

    init();
});