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
                    {
                    "query": {
                    "filtered": {
                    "query": {
                    "term": {
                    "name": "test"
                    }
                    },
                    "filter": {
                    "or": [
                    {
                    "term": {
                    "categories": "/foo/bar"
                    }
                    },
                    {
                    "term": {
                    "categories": "/fool"
                    }
                    }
                    ]
                    }
                    }
                    }
                    }
                </div>
            </div>
        </div>
    </div>
</div>