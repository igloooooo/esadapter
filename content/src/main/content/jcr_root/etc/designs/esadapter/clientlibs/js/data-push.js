jQuery(document).ready(function () {
    var source = $("#asset-list-template").html();
    var template = Handlebars.compile(source);

    var folders = [
        {
            "name": "Aquire",
            "type": "folder"
        },
        {
            "name": "Onboard",
            "type": "folder"
        },
        {
            "name": "Engage",
            "type": "folder",
            "dataAttributes": {
                "id": "engage-folder"
            },
            "children": [
                {
                    "name": "Abandoned Cart",
                    "type": "folder",
                    "children": [
                        {
                            "name": "Archive",
                            "type": "folder"
                        }
                    ]
                },
                {
                    "name": "Birthday",
                    "type": "folder"
                },
                {
                    "name": "Browse Retargeting",
                    "type": "folder"
                },
                {
                    "name": "Loyalty",
                    "type": "folder"
                },
                {
                    "name": "Newsletter",
                    "type": "folder"
                },
                {
                    "name": "Post-Purchase",
                    "type": "folder"
                },
                {
                    "name": "Promotional",
                    "type": "folder"
                },
                {
                    "name": "Transactional",
                    "type": "folder",
                    "children": [
                        {
                            "name": "Archive",
                            "type": "folder"
                        }
                    ]
                },
                {
                    "name": "Wish List",
                    "type": "folder"
                }
            ]
        },
        {
            "name": "Retain",
            "type": "folder"
        }
    ];

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

    $("#selectedBtn").click(function () {
        var selectedItem = $('#pathTree').tree('selectedItems');
        if (selectedItem != null) {
            $("#path-text").val(selectedItem[0].path);
        }

    });

    var DataSourceTree = function (options) {
        this._data = options.data;
        this._delay = options.delay;
    }

    DataSourceTree.prototype.data = function (options, callback) {
        var self = this;
        var $data = null;
        var path = '/content';

        if (!("name" in options) && !("type" in options)) {
            $data = null;
        }
        else if ("type" in options && options.type == "folder") {
            if ("additionalParameters" in options && "children" in options.additionalParameters)
                $data = options.additionalParameters.children;
            else {
                $data = null;
                path = options.path;
            }//no data
        }

        if ($data != null)//this setTimeout is only for mimicking some random delay
            setTimeout(function () {
                callback({ data: $data });
            }, parseInt(Math.random() * 500) + 200);
        if ($data == null) {
            $.ajax({
                url: 'http://localhost:4502/bin/tree/ext.json',
                data: 'path=' + path + "&predicate=siteadmin",
                type: 'GET',
                dataType: 'json',
                success: function (response) {
                    callback({ data: parsePathData(response, path) })
                },
                error: function (response) {
                    //console.log(response);
                }
            });

        }
    };

    var treeDataSource = new DataSourceTree({data: {}});

    $('#pathTree').ace_tree({
        dataSource: treeDataSource,
        multiSelect: false,
        folderSelect: true,
        loadingHTML: '<div class="tree-loading"><i class="icon-refresh icon-spin blue"></i></div>',
        'open-icon': 'icon-folder-open',
        'close-icon': 'icon-folder-close',
        'selectable': true,
        'selected-icon': null,
        'unselected-icon': null
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

    function parsePathData(data, parentPath) {
        var pathData = [];
        for (var i = 0; i < data.length; i++) {
            pathData.push(parseProperty(data[i], parentPath));
        }
        return pathData;
    }

    function parseProperty(data, parentPath) {
        if (data != null) {
            var item = {};
            item.name = data.name;
            item.type = parseType(data.cls);
            item.path = parentPath + "/" + data.name;
            if (data.children != null) {
                item.additionalParameters = {};
                item.additionalParameters.children = parsePathData(data.children, item.path);
            }
            return item;
        }
        return {};
    }

    function parseType(type) {
        if (type != 'folder') {
            return 'item';
        }
        return type;
    }
});