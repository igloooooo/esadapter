<%@include file="/apps/esadapter/global/global.jsp" %>
<div class="page-header">
    <h1>
        Push Data into ElasticSearch
        <small>
            <i class="icon-double-angle-right"></i>
            Push Data into ElasticSearch
        </small>
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->

        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"> Path </label>

                <div class="col-sm-9">
                    <div class="input-group col-xs-10 col-sm-5">
                        <input type="text" id="path-text" class="form-control search-query"
                               placeholder="Type your query">
																	<span class="input-group-btn">
																		<button type="button"
                                                                                class="btn btn-purple btn-sm" data-toggle="modal"
                                                                                data-target="#path-select-modal">
                                                                            Path
                                                                            <i class="icon-search icon-on-right bigger-110"></i>
                                                                        </button>
																	</span>
                    </div>
                </div>
            </div>

            <div class="space-4"></div>

            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button class="btn btn-info" type="button" id="pushDataBtn">
                        <i class="icon-ok bigger-110"></i>
                        Push
                    </button>

                    &nbsp; &nbsp; &nbsp;
                    <button class="btn" type="button" id="dryRunBtn">
                        <i class="icon-undo bigger-110"></i>
                        Dry run
                    </button>
                </div>
            </div>
        </form>


    </div>
    <!-- /.col -->
    <div class="col-xs-12">
        <div class="col-xs-12" id="export-result">

        </div>
    </div>
</div>
<!-- /.row -->
<script id="asset-list-template" type="text/x-handlebars-template">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="smaller">
                Export List
                <small>({{count}})</small>
            </h4>
        </div>

        <div class="widget-body">
            <div class="widget-main">
                {{#each exportedPathList}}
                <p>{{this}}</p>
                {{/each}}
            </div>
        </div>
    </div>
</script>

<div id="path-select-modal" class="modal fade" tabindex="-1" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header no-padding">
                <div class="table-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        <span class="white">x</span>
                    </button>
                    Select Path
                </div>
            </div>

            <div class="modal-body fuelux">
                <div id="tree1" class="tree"></div>
            </div>

            <div class="modal-footer no-margin-top">
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="selectedBtn">OK</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>