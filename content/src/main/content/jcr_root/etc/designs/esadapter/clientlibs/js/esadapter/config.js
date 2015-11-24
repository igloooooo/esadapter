jQuery(function ($) {
    var configDTO;
    var tag_input;
    init();

    function init() {
        //we could just set the data-provide="tag" of the element inside HTML, but IE8 fails!
        tag_input = $('#form-field-tags');
        if(! ( /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase())) )
        {
            tag_input.tag(
                {
                    placeholder:tag_input.attr('placeholder'),
                    //enable typeahead by specifying the source array
                    source: ace.variable_US_STATES//defined in ace.js >> ace.enable_search_ahead
                }
            );
        }
        else {
            //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
            tag_input.after('<textarea id="'+tag_input.attr('id')+'" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
            //$('#form-field-tags').autosize({append: "\n"});
        }

        $.ajax({
            type: "GET",
            url: "/bin/esadapter/config",
            contentType: 'application/json',
            success: function (e) {
                configDTO = JSON.parse(e);
                populateConfig(configDTO);
            }
        });

        $('#saveBtn').on('click', function (e) {
            generateConfigDTO();
            $.ajax({
                type: "POST",
                url: "/bin/esadapter/config",
                data: JSON.stringify(configDTO),
                contentType: 'application/json',
                success: function (e) {
                    $.gritter.add({
                        title: 'Configuration Saved',
                        text: 'You changes has been saved.',
                        class_name: 'gritter-info gritter-center'
                    });
                }
            });
        });
        $('#resetBtn').on('click', function (e) {

        });



    };

    function populateConfig(dto) {
        $('#esAddress').val(dto.esAddress);
        $('#esPort').val(dto.esPort);
        if (dto.esConfigDTO) {
            $('#clusterName').val(dto.esConfigDTO.clusterName);
            $('#connectTimeout').val(dto.esConfigDTO.connectTimeout);
            $('#nodesSamplerInterval').val(dto.esConfigDTO.nodesSamplerInterval);
            $('#sniff').prop('checked', dto.esConfigDTO.sniff);
            $('#threadpoolSearchType').val(dto.esConfigDTO.threadpoolSearchType);
            $('#threadpoolSearchSize').val(dto.esConfigDTO.threadpoolSearchSize);
            $('#threadpoolSearchQueueSize').val(dto.esConfigDTO.threadpoolSearchQueueSize);
            $('#startListener').prop('checked', dto.esConfigDTO.startListener);
            popularListenerPath(dto.esConfigDTO.listenerPath);

        }
    };

    function generateConfigDTO() {
        configDTO.esAddress = $('#esAddress').val();
        configDTO.esPort = $('#esPort').val();
        configDTO.esConfigDTO.clusterName = $('#clusterName').val();
        configDTO.esConfigDTO.connectTimeout = $('#connectTimeout').val();
        configDTO.esConfigDTO.nodesSamplerInterval = $('#nodesSamplerInterval').val();
        configDTO.esConfigDTO.sniff = $('#sniff').prop('checked');
        configDTO.esConfigDTO.threadpoolSearchType = $('#threadpoolSearchType').val();
        configDTO.esConfigDTO.threadpoolSearchSize = $('#threadpoolSearchSize').val();
        configDTO.esConfigDTO.threadpoolSearchQueueSize = $('#threadpoolSearchQueueSize').val();
        configDTO.esConfigDTO.startListener = $('#startListener').prop('checked');
        configDTO.esConfigDTO.listenerPath = tag_input.val().split(",");
    };

    function popularListenerPath(paths) {
        if(paths != null) {
            for(var i=0; i< paths.length; i++) {
                addTagToElement(tag_input, paths[i]);
            }
        }

    }
    function addTagToElement(el, newTag) {
        var e = $.Event('keydown');
        e.keyCode = 13;
        el.next().val(newTag);
        el.next().trigger(e)
    }
});