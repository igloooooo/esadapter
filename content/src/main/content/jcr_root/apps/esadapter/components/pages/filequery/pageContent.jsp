<%@include file="/apps/esadapter/global/global.jsp" %>
<div class="page-header">
    <h1>
        File Query Example
        <small>
            <i class="icon-double-angle-right"></i>
            File Query Example
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
                    <div id="faq-list-2" class="panel-group accordion-style1 accordion-style2">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a href="#faq-2-1" data-parent="#faq-list-2" data-toggle="collapse" class="accordion-toggle collapsed">
                                    <i class="smaller-80 icon-chevron-right" data-icon-hide="icon-chevron-down align-top" data-icon-show="icon-chevron-right"></i>
                                    &nbsp;
                                    Query Example:
                                </a>
                            </div>

                            <div class="panel-collapse collapse" id="faq-2-1" style="height: 0px;">
                                <div class="panel-body">
                                    <div class="well well-sm">
                                        Example: HillyBilly
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <label for="queryContent">Query</label>
                        <textarea class="form-control" id="queryContent" placeholder="Default Text" rows="3"></textarea>
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
                                        <div id="result-contain"></div>
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

