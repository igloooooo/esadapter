<%@include file="/apps/esadapter/global/global.jsp" %>
<div class="page-header">
    <h1>
        Configuration
        <small>
            <i class="icon-double-angle-right"></i>
            ES Adapter Configuration
        </small>
    </h1>
</div>
<!-- /.page-header -->
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
                <li class="active">
                    <a data-toggle="tab" href="#generalCfg">General</a>
                </li>

                <li>
                    <a data-toggle="tab" href="#searchCfg">ElasticSearch</a>
                </li>

                <li>
                    <a data-toggle="tab" href="#listenerCfg">Listener</a>
                </li>

                <li>
                    <a data-toggle="tab" href="#othersCfg">Others</a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="generalCfg" class="tab-pane in active">
                    <div class="row">
                        <div class="col-xs-12">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="esAddress">
                                        ElasticSearch Server Address</label>

                                    <div class="col-sm-9">
                                        <input type="text" id="esAddress" placeholder="ElasticSearch Server Address"
                                               class="col-xs-10 col-sm-5">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="esPort"> ElasticSearch
                                        Server Port</label>

                                    <div class="col-sm-9">
                                        <input type="text" id="esPort" placeholder="ElasticSearch Server Port"
                                               class="col-xs-10 col-sm-5">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div id="searchCfg" class="tab-pane">
                    <div class="row">
                        <div class="col-xs-12">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="connectTimeout"> Connect
                                        Timeout </label>

                                    <div class="col-sm-9">
                                        <input type="text" id="connectTimeout" placeholder="Connect Timeout"
                                               class="col-xs-10 col-sm-5">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="nodesSamplerInterval">
                                        Nodes Sampler Interval </label>

                                    <div class="col-sm-9">
                                        <input type="text" id="nodesSamplerInterval"
                                               placeholder="Nodes Sampler Interval " class="col-xs-10 col-sm-5">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="sniff"> Sniff </label>

                                    <div class="col-sm-9">
                                        <input type="checkbox" id="sniff" placeholder="Sniff" class="ace">
                                        <span class="lbl"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="threadpoolSearchType">
                                        Thread Pool Search Type </label>

                                    <div class="col-sm-9">
                                        <input type="text" id="threadpoolSearchType"
                                               placeholder="Thread Pool Search Type" class="col-xs-10 col-sm-5">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="threadpoolSearchSize">
                                        Thread Pool Search Size </label>

                                    <div class="col-sm-9">
                                        <input type="text" id="threadpoolSearchSize"
                                               placeholder="Thread Pool Search Size" class="col-xs-10 col-sm-5">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right"
                                           for="threadpoolSearchQueueSize"> Thread pool Search Queue Size </label>

                                    <div class="col-sm-9">
                                        <input type="text" id="threadpoolSearchQueueSize"
                                               placeholder="Thread pool Search Queue Size " class="col-xs-10 col-sm-5">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div id="listenerCfg" class="tab-pane">
                    <div class="row">
                        <div class="col-xs-12">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="startListener"> Start Listener </label>

                                    <div class="col-sm-9">
                                        <input type="checkbox" id="startListener" placeholder="Start Listener" class="ace">
                                        <span class="lbl"></span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-field-tags">Listener Path</label>

                                    <div class="col-sm-9">
                                        <input type="text" id="form-field-tags" placeholder="Enter tags ..." />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div id="othersCfg" class="tab-pane">
                    <div class="row">
                        <div class="col-xs-12">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="defaultMapping"> Enable
                                        Default Mapping </label>

                                    <div class="col-sm-9">
                                        <input type="checkbox" id="defaultMapping" placeholder="Enable Default Mapping"
                                               class="ace">
                                        <span class="lbl"></span>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="clearfix form-actions">
            <div class="col-md-offset-3 col-md-9">
                <button class="btn btn-info" type="button" id="saveBtn">
                    <i class="icon-ok bigger-110"></i>
                    Save
                </button>

                &nbsp; &nbsp; &nbsp;
                <button class="btn" id="cancelBtn" type="button">
                    <i class="icon-undo bigger-110"></i>
                    Cancel
                </button>

                <button class="btn" type="reset" id="resetBtn">
                    <i class="icon-undo bigger-110"></i>
                    Reset
                </button>
            </div>
        </div>
    </div>
</div>

