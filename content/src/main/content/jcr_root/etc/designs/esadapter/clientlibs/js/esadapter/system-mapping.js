jQuery(function($) {
    var mappingListTable;
    init();

    function loadMappingList() {
        var source = $("#mapping-list-template").html();
        var template = Handlebars.compile(source);
        $.ajax({
            type: "GET",
            url: "/bin/esadapter/systemmapping?index=aem",
            contentType: 'application/json',
            success: function (e) {
                $('#mapping-contain').empty();
                $('#mapping-contain').append(template(JSON.parse(e)));
                initMappingListTable();
            }
        });
    }

    function init() {
        loadMappingList();
        initMappingContent();
    }

    function initMappingListTable() {
        mappingListTable = $('#mapping-list-table').dataTable( {
            "aoColumns": [
                { "bSortable": false },
                null, null,null, null,
                { "bSortable": false }
            ] } );

        $('table th input:checkbox').on('click' , function(){
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                .each(function(){
                    this.checked = that.checked;
                    $(this).closest('tr').toggleClass('selected');
                });

        });


        $('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
        function tooltip_placement(context, source) {
            var $source = $(source);
            var $parent = $source.closest('table')
            var off1 = $parent.offset();
            var w1 = $parent.width();

            var off2 = $source.offset();
            var w2 = $source.width();

            if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
            return 'left';
        }
    }

    function initMappingContent() {
        var mappingContent = new JSONEditor(document.getElementById("mapping-content"), {'mode':'view'});
        $('#mapping-content-modal').on('show.bs.modal', function (e) {
            var selectedType = $(e.relatedTarget).data('typename')
            $.ajax({
                type: "GET",
                url: "/bin/esadapter/systemmapping?index=aem&type=" + selectedType,
                contentType: 'application/json',
                success: function (e) {
                    mappingContent.set(JSON.parse(e)[0].mappingContent);
                }
            });
        })
    }
})