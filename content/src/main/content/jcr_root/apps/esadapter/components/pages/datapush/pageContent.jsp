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
                                                                                class="btn btn-purple btn-sm">
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
    <div>
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