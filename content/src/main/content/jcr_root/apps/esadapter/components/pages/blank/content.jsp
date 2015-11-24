<%@include file="/apps/esadapter/global/global.jsp" %>
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <div class="sidebar" id="sidebar">
            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'fixed')
                } catch (e) {
                }
            </script>

            <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                    <a class="btn btn-success" href="/etc/esadapter/dashboard.html">
                        <i class="icon-signal"></i>
                    </a>

                    <a class="btn btn-info" href="/etc/esadapter/pushdata.html">
                        <i class="icon-pencil"></i>
                    </a>

                    <a class="btn btn-warning">
                        <i class="icon-group"></i>
                    </a>

                    <a class="btn btn-danger" href="/etc/esadapter/config.html">
                        <i class="icon-cogs"></i>
                    </a>
                </div>

                <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                    <span class="btn btn-success"></span>

                    <span class="btn btn-info"></span>

                    <span class="btn btn-warning"></span>

                    <span class="btn btn-danger"></span>
                </div>
            </div>
            <!-- #sidebar-shortcuts -->

            <ul class="nav nav-list">
                <li>
                    <a href="/etc/esadapter/dashboard.html">
                        <i class="icon-dashboard"></i>
                        <span class="menu-text"> Dashboard </span>
                    </a>
                </li>

                <li>
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-desktop"></i>
                        <span class="menu-text"> Field Mapping </span>

                        <b class="arrow icon-angle-down"></b>
                    </a>
                    <ul class="submenu">
                        <li>
                            <a href="/etc/esadapter/systemmapping.html">
                                <i class="icon-double-angle-right"></i>
                                System Mapping
                            </a>
                        </li>

                        <li>
                            <a href="/etc/esadapter/fieldmapping.html">
                                <i class="icon-double-angle-right"></i>
                                Customer Mapping
                            </a>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="/etc/esadapter/pushdata.html">
                        <i class="icon-text-width"></i>
                        <span class="menu-text"> Push Data </span>
                    </a>
                </li>

                <li>
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-list-alt"></i>
                        <span class="menu-text"> Query Helper </span>

                        <b class="arrow icon-angle-down"></b>
                    </a>

                    <ul class="submenu">
                        <li>
                            <a href="/etc/esadapter/queryhelper.html">
                                <i class="icon-double-angle-right"></i>
                                Query Builder
                            </a>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="/etc/esadapter/config.html">
                        <i class="icon-list"></i>
                        <span class="menu-text"> Configuration </span>
                    </a>
                </li>

                <li>
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-list-alt"></i>
                        <span class="menu-text"> Help </span>

                        <b class="arrow icon-angle-down"></b>
                    </a>

                    <ul class="submenu">
                        <li>
                            <a href="/etc/esadapter/aclqueryexample.html">
                                <i class="icon-double-angle-right"></i>
                                ACL Query Example
                            </a>
                        </li>

                        <li>
                            <a href="/etc/esadapter/filequeryexample.html">
                                <i class="icon-double-angle-right"></i>
                                File Query Example
                            </a>
                        </li>
                    </ul>
                </li>

            </ul>
            <!-- /.nav-list -->

            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
                   data-icon2="icon-double-angle-right"></i>
            </div>

            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'collapsed')
                } catch (e) {
                }
            </script>
        </div>

        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>

                <cq:include script="breadcrumbs.jsp"/>
                <!-- .breadcrumb -->

                <div class="nav-search" id="nav-search">
                    <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="icon-search nav-search-icon"></i>
								</span>
                    </form>
                </div>
                <!-- #nav-search -->
            </div>

            <div class="page-content">
                <cq:include script="pageContent.jsp"/>
            </div>
            <!-- /.page-content -->
        </div>
        <!-- /.main-content -->

    </div>
    <!-- /.main-container-inner -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div>
<!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

<!--[if !IE]> -->

<script type="text/javascript">
    window.jQuery || document.write("<script src='/etc/designs/esadapter/clientlibs/js/jquery-2.0.3.min.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
window.jQuery || document.write("<script src='/etc/designs/esadapter/clientlibs/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='/etc/designs/esadapter/clientlibs/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<script src="/etc/designs/esadapter/clientlibs/js/bootstrap.min.js"></script>
<script src="/etc/designs/esadapter/clientlibs/js/typeahead-bs2.min.js"></script>
<script src="/etc/designs/esadapter/clientlibs/js/bootstrap-tag.min.js"></script>

<!-- page specific plugin scripts -->

<!-- ace scripts -->

<script src="/etc/designs/esadapter/clientlibs/js/ace-elements.min.js"></script>
<script src="/etc/designs/esadapter/clientlibs/js/ace.min.js"></script>

<script src="/etc/designs/esadapter/clientlibs/js/jquery.dataTables.min.js"></script>
<script src="/etc/designs/esadapter/clientlibs/js/jquery.dataTables.bootstrap.js"></script>
<script src="/etc/designs/esadapter/clientlibs/js/jquery.gritter.min.js"></script>

<script src="/etc/designs/esadapter/clientlibs/js/handlebars-v4.0.2.js"></script>
<script src="/etc/designs/esadapter/clientlibs/js/jquery.nestable.min.js"></script>
<script src="/etc/designs/esadapter/clientlibs/js/jsoneditor.min.js"></script>
<script src="/etc/designs/esadapter/clientlibs/js/esadapter/esadapter.js"></script>