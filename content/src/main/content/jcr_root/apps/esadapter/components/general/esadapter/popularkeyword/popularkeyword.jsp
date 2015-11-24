<%@include file="/apps/esadapter/global/global.jsp" %>
<div class="col-sm-12">
    <div class="widget-box transparent">
        <div class="widget-header widget-header-flat">
            <h4 class="lighter">
                <i class="icon-star orange"></i>
                Popular Keywords
            </h4>

            <div class="widget-toolbar">
                <a href="#" data-action="collapse">
                    <i class="icon-chevron-up"></i>
                </a>
            </div>
        </div>

        <div class="widget-body"><div class="widget-body-inner" style="display: block;">
            <div class="widget-main no-padding">
                <table class="table table-bordered table-striped">
                    <thead class="thin-border-bottom">
                    <tr>
                        <th>
                            <i class="icon-caret-right blue"></i>
                            Keyword
                        </th>

                        <th>
                            <i class="icon-caret-right blue"></i>
                            Count
                        </th>

                    </tr>
                    </thead>

                    <tbody id="keywords-contain">
                    </tbody>
                </table>
            </div><!-- /widget-main -->
        </div></div><!-- /widget-body -->
    </div><!-- /widget-box -->
</div>

<script id="keywords-list-template" type="text/x-handlebars-template">
    {{# each this}}
    <tr>
        <td>{{keyword}}</td>

        <td>
            <b class="green">{{count}}</b>
        </td>
    </tr>
    {{/each}}

</script>