jQuery(function ($) {

    // The main template.
    var main = Handlebars.compile($("#map-template").html());
    var mappingItem = Handlebars.compile($("#item-template").html());
    var selectedItem = null;
    var bCreate = false;

// Register the list partial that "main" uses.
    Handlebars.registerPartial("map-item-template", $("#map-item-template").html());


    var json = {};

    loadFieldMappingItem();


    function loadFieldMappingItem() {
        var mappingType = ESAdapter.urlParam('type');
        $.ajax({
            type: "GET",
            url: "/bin/esadapter/fieldmapping?type=" + mappingType,
            contentType: 'application/json',
            success: function (e) {
                var result = JSON.parse(e);
                $('#cqType').text(result.cqType);
                $('#esType').text(result.esType);
                json = result.children;
                init();
            }
        });
        $('#updateMapBtn').click(function () {
            var mappingContent = $('.dd').nestable('serialize');
            var mapping = {'index': 'aem_customer'};
            mapping.cqType = $('#cqType').text();
            mapping.esType = $('#esType').text();
            mapping.children = mappingContent;
            $.ajax({
                type: "POST",
                url: "/bin/esadapter/fieldmapping",
                data: JSON.stringify(mapping),
                contentType: 'application/json',
                success: function (e) {
                    $.gritter.add({
                        title: 'Updated Saved',
                        text: 'You changes has been saved.',
                        class_name: 'gritter-info gritter-center'
                    });
                }
            });
        });

        $('#delete-mapping-modal').one('click', '#deleteBtn', function () {
                selectedItem.remove();
                json = $('.dd').nestable('serialize');
                init();
            });

        $('#mapping-content-modal').on('show.bs.modal', function (event) {
                if (!bCreate) {
                    $('#fieldName').val(selectedItem.data("name"));
                    $('#fieldXpath').val(selectedItem.data("xpath"));
                    $('#fieldType').val(selectedItem.data("type"));
                } else {
                    $('#fieldName').val("");
                    $('#fieldXpath').val("");
                    $('#fieldType').val("");
                }
            })
            .one('click', '#saveBtn', function () {
                var itemContent = {};
                if (bCreate) {
                    itemContent.name = $('#fieldName').val();
                    itemContent.xpath = $('#fieldXpath').val();
                    itemContent.type = $('#fieldType').val();
                    if (selectedItem.has('ol').length == 0) {
                        selectedItem.append($('<ol class="dd-list"></ol>'));
                    }
                    var ol = selectedItem.find('ol').first();
                    ol.append($(mappingItem(itemContent)));
                    json = $('.dd').nestable('serialize');
                    init();
                } else {
                    selectedItem.data("name", $('#fieldName').val());
                    selectedItem.data("xpath", $('#fieldXpath').val());
                    selectedItem.data("type", $('#fieldType').val());

                    json = $('.dd').nestable('serialize');
                    init();
                }

            });
    }

    function refreshList() {
        $('#nestable').empty();
        $("#nestable").html(main({ children: json }));
        $('#nestable').nestable('init');
    }

    function init() {
        refreshList();

        $('.dd-handle a').on('mousedown', function (e) {
            e.stopPropagation();
        });

        $("#nestable").off("click", "**");

        $('#nestable').on('click', 'a.red', function (e) {
            selectedItem = $(e.target).closest('.dd-item');
            $('#delete-mapping-modal').modal({ backdrop: 'static', keyboard: false });

        });
        $('#nestable').on('click', 'a.green', function (e) {
            selectedItem = $(e.target).closest('.dd-item');
            bCreate = true;
            $('#mapping-content-modal').modal({ backdrop: 'static', keyboard: false });
        });

        $('#nestable').on('click', 'a.blue', function (e) {
            selectedItem = $(e.target).closest('.dd-item');
            bCreate = false;
            $('#mapping-content-modal').modal({ backdrop: 'static', keyboard: false })
        });

    }

});