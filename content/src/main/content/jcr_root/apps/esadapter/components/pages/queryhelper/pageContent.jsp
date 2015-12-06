<%@include file="/apps/esadapter/global/global.jsp" %>
<div class="page-header">
    <h1>
        Query Helper
        <small>
            <i class="icon-double-angle-right"></i>
            Query Helper
        </small>
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
    <div class="col-sm-6">
        <div class="widget-box">
            <div class="widget-header">
                <h4>Query Builder</h4>
            </div>

            <div class="widget-body">
                <div class="widget-main">
                    <cq:include path="queryexample" resourceType="/apps/esadapter/components/general/esadapter/queryexample"/>
                    <div>
                        <label for="queryContent">Query</label>
                        <div id="queryContent" style="height: 500px"></div>
                        <%--<textarea class="form-control" id="queryContent" placeholder="Default Text" rows="20"></textarea>--%>
                    </div>
                    <hr>
                    <div>
                        <button class="btn btn-danger btn-block" id="search">Search</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6">
        <div class="widget-box">
            <div class="widget-header">
                <h4>Query Result</h4>
            </div>

            <div class="widget-body">
                <div class="widget-main">
                    <div class="row">
                        <div class="col-xs-12">
                            <form class="form-horizontal" role="form" id="resultWindow">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="time-took"> Time Took: </label>

                                    <div class="col-sm-9">
                                        <input readonly="" type="text" class="col-xs-10 col-sm-5" id="time-took" value="">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="total-result"> Result size: </label>

                                    <div class="col-sm-9">
                                        <input readonly="" type="text" class="col-xs-10 col-sm-5" id="total-result" value="">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="result-contain"> Result: </label>

                                    <div class="col-sm-9">
                                        <div id="result-contain" style="height: 800px"></div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

