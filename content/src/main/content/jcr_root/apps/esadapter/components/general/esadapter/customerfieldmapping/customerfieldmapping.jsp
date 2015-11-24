<%@include file="/apps/esadapter/global/global.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="profile-user-info profile-user-info-striped">
            <div class="profile-info-row">
                <div class="profile-info-name"> CQ Type</div>

                <div class="profile-info-value">
                    <span class="editable editable-click" id="cqType"></span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name"> Index Type</div>

                <div class="profile-info-value">
                    <span class="editable editable-click" id="esType"></span>
                </div>
            </div>

        </div>
    </div>
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">Customer Field Mapping</h3>

        <div class="dd" id="nestable">

        </div>

        <div>
            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button class="btn btn-info" type="button" id="updateMapBtn">
                        <i class="icon-ok bigger-110"></i>
                        Save
                    </button>

                    &nbsp; &nbsp; &nbsp;
                    <button class="btn" type="cancel">
                        <i class="icon-undo bigger-110"></i>
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="map-item-template" type="text/x-handlebars-template">
    {{#each children}}
    <li class="dd-item" data-id="{{id}}" data-name="{{name}}" data-xpath="{{xpath}}" data-type="{{type}}">
        <div class="dd-handle">
            {{name}}
            <div class="pull-right action-buttons">
                <a class="blue" href="#">
                    <i class="icon-pencil bigger-130"></i>
                </a>

                <a class="red" href="#">
                    <i class="icon-trash bigger-130"></i>
                </a>

                <a class="green" href="#">
                    <i class="icon-plus bigger-130"></i>
                </a>
            </div>
        </div>
        {{#if children}}
        <ol class="dd-list">
            {{> map-item-template}}
        </ol>
        {{/if}}
    </li>

    {{/each}}
</script>

<script id="map-template" type="text/html">
    <ol class="dd-list">
        {{> map-item-template}}
    </ol>
</script>

<script id="item-template" type="text/html">
    <li class="dd-item" data-id="{{id}}" data-name="{{name}}" data-xpath="{{xpath}}" data-type="{{type}}">
        <div class="dd-handle">
            {{name}}
            <div class="pull-right action-buttons">
                <a class="blue" href="#">
                    <i class="icon-pencil bigger-130"></i>
                </a>

                <a class="red" href="#">
                    <i class="icon-trash bigger-130"></i>
                </a>

                <a class="green" href="#">
                    <i class="icon-plus bigger-130"></i>
                </a>
            </div>
        </div>
    </li>
</script>

<div id="mapping-content-modal" class="modal fade" tabindex="-1" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header no-padding">
                <div class="table-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        <span class="white">x</span>
                    </button>
                    Mapping Content
                </div>
            </div>

            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="fieldName"> Name </label>

                        <div class="col-sm-9">
                            <input type="text" id="fieldName" placeholder="Name" class="col-xs-10 col-sm-10"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="fieldXpath"> Path </label>

                        <div class="col-sm-9">
                            <input type="text" id="fieldXpath" placeholder="path" class="col-xs-10 col-sm-10"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="fieldType"> Type </label>

                        <div class="col-sm-9">
                            <select id="fieldType" class="col-xs-10 col-sm-10">
                                <option value="1">STRING</option>
                                <option value="2">BINARY</option>
                                <option value="3">LONG</option>
                                <option value="4">DOUBLE</option>
                                <option value="5">DATE</option>
                                <option value="6">BOOLEAN</option>
                                <option value="7">NAME</option>
                                <option value="8">PATH</option>
                                <option value="12">DECIMAL</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer no-margin-top">
                <button type="button" id="saveBtn" class="btn btn-primary" data-dismiss="modal">Save</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div id="delete-mapping-modal" class="modal fade" tabindex="-1" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header no-padding">
                <div class="table-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        <span class="white">x</span>
                    </button>
                    Delete Mapping Item
                </div>
            </div>

            <div class="modal-body">
                Do you want to delete this map item?
            </div>

            <div class="modal-footer no-margin-top">
                <button type="button" id="deleteBtn" class="btn btn-primary" data-dismiss="modal">Delete</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>