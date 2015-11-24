<%@include file="/apps/esadapter/global/global.jsp" %>
<div class="page-header">
    <h1>
        Dashboard
        <small>
            <i class="icon-double-angle-right"></i>
            Dashboard
        </small>
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
    <div class="col-xs-12">
        <div class="row">
            <div class="space-6"></div>
            <cq:include path="statisticboard" resourceType="/apps/esadapter/components/general/esadapter/statisticboard"/>
        </div>
        <div class="hr hr32 hr-dotted"></div>
        <div class="row">
            <cq:include path="popularkeyword" resourceType="/apps/esadapter/components/general/esadapter/popularkeyword"/>
        </div>
        <div class="hr hr32 hr-dotted"></div>
        <div class="row">
            <cq:include path="slowquery" resourceType="/apps/esadapter/components/general/esadapter/slowquery"/>
        </div>
    </div>
</div>
<!-- /.row -->
