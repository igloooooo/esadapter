<%@include file="/apps/esadapter/global/global.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">Customer Field Mapping</h3>

        <div class="table-header">
            Customer Field Mapping
        </div>

        <div class="table-responsive">
            <table id="mapping-list-table" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="center">
                        <label>
                            <input type="checkbox" class="ace"/>
                            <span class="lbl"></span>
                        </label>
                    </th>
                    <th>Index</th>
                    <th>Type</th>
                    <th class="hidden-480">CQ Type</th>

                    <th>
                        <i class="icon-time bigger-110 hidden-480"></i>
                        Update Time
                    </th>

                    <th></th>
                </tr>
                </thead>

                <tbody id="mapping-contain">


                </tbody>
            </table>
        </div>
        <button class="btn btn-success" id="add-new-mapping" data-toggle="modal"
                data-target="#add-mapping-content-modal">Add New Mapping
        </button>
    </div>
</div>

<script id="mapping-list-template" type="text/x-handlebars-template">
    {{# each this}}
    <tr>
        <td class="center">
            <label>
                <input type="checkbox" class="ace"/>
                <span class="lbl"></span>
            </label>
        </td>

        <td>
            <a href="#">{{index}}</a>
        </td>
        <td>{{type}}</td>
        <td class="hidden-480">{{cqType}}</td>
        <td>10</td>

        <td>
            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">

                <a class="green" href="/etc/esadapter/fieldmappingdetails.html?type={{type}}">
                    <i class="icon-pencil bigger-130"></i>
                </a>

                <a class="red" href="#add-mapping-content-modal" data-toggle="modal" data-typename="{{type}}">
                    <i class="icon-trash bigger-130"></i>
                </a>
            </div>

            <div class="visible-xs visible-sm hidden-md hidden-lg">
                <div class="inline position-relative">
                    <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-caret-down icon-only bigger-120"></i>
                    </button>

                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
                        <li>
                            <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="icon-zoom-in bigger-120"></i>
																				</span>
                            </a>
                        </li>

                        <li>
                            <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="icon-edit bigger-120"></i>
																				</span>
                            </a>
                        </li>

                        <li>
                            <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="icon-trash bigger-120"></i>
																				</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </td>
    </tr>
    {{/each}}

</script>

<div id="add-mapping-content-modal" class="modal fade" tabindex="-1" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header no-padding">
                <div class="table-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        <span class="white">x</span>
                    </button>
                    Crete New Mapping Item
                </div>
            </div>

            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="cq-type"> CQ Type </label>

                        <div class="col-sm-9">
                            <input type="text" id="cq-type" placeholder="Name" class="col-xs-10 col-sm-10"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="es-type"> ES Index Type </label>

                        <div class="col-sm-9">
                            <input type="text" id="es-type" placeholder="path" class="col-xs-10 col-sm-10"/>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer no-margin-top">
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="saveFieldMapping">Create</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>