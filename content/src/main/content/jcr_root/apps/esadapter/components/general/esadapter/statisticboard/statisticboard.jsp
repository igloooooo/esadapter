<%@include file="/apps/esadapter/global/global.jsp" %>
<div class="col-sm-12 infobox-container">
    <div class="infobox infobox-green  ">
        <div class="infobox-icon">
            <i class="icon-comments"></i>
        </div>

        <div class="infobox-data">
            <span class="infobox-data-number" id="slowestQuery">32</span>

            <div class="infobox-content">Longest Query Time</div>
        </div>
        <div class="stat stat-success">8%</div>
    </div>

    <div class="infobox infobox-blue  ">
        <div class="infobox-icon">
            <i class="icon-twitter"></i>
        </div>

        <div class="infobox-data">
            <span class="infobox-data-number" id="averageQuery">11</span>

            <div class="infobox-content">Average Query Time</div>
        </div>

        <div class="badge badge-success">
            +32%
            <i class="icon-arrow-up"></i>
        </div>
    </div>

    <div class="infobox infobox-red  ">
        <div class="infobox-icon">
            <i class="icon-beaker"></i>
        </div>

        <div class="infobox-data">
            <span class="infobox-data-number" id="yesterdayIndex">7</span>

            <div class="infobox-content">New Index Last Day</div>
        </div>
    </div>

    <div class="infobox infobox-orange2  ">
        <div class="infobox-chart">
            <span class="sparkline" data-values="196,128,202,177,154,94,100,170,224"><canvas width="44" height="33"
                                                                                             style="display: inline-block; width: 44px; height: 33px; vertical-align: top;"></canvas></span>
        </div>

        <div class="infobox-data">
            <span class="infobox-data-number" id="thisWeekIndex">6,251</span>

            <div class="infobox-content">Last Index This Week</div>
        </div>

        <div class="badge badge-success">
            7.2%
            <i class="icon-arrow-up"></i>
        </div>
    </div>

    <div class="space-6"></div>

    <div class="infobox infobox-green infobox-small infobox-dark">

        <div class="infobox-icon">
            <i class="icon-archive"></i>
        </div>

        <div class="infobox-data">
            <div class="infobox-content">Index</div>
            <div class="infobox-content" id="indexCount">4</div>
        </div>
    </div>

    <div class="infobox infobox-blue infobox-small infobox-dark">
        <div class="infobox-icon">
            <i class="icon-double-angle-up"></i>
        </div>

        <div class="infobox-data">
            <div class="infobox-content">Node</div>
            <div class="infobox-content" id="nodeCount">200K</div>
        </div>
    </div>

    <div class="infobox infobox-grey infobox-small infobox-dark">
        <div class="infobox-icon">
            <i class="icon-download-alt"></i>
        </div>

        <div class="infobox-data">
            <div class="infobox-content">Query</div>
            <div class="infobox-content" id="queryCount">1,205</div>
        </div>
    </div>
</div>