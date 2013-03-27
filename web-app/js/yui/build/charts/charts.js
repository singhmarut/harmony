/* YUI 3.8.1 (build 5795) Copyright 2013 Yahoo! Inc. http://yuilibrary.com/license/ */
YUI.add('charts', function (Y, NAME) {

/**
 * The Chart class is the basic application used to create a chart.
 *
 * @module charts
 * @class Chart
 * @constructor
 */
function Chart(cfg)
{
    if(cfg.type != "pie")
    {
        return new Y.CartesianChart(cfg);
    }
    else
    {
        return new Y.PieChart(cfg);
    }
}
Y.Chart = Chart;


}, '3.8.1', {"requires": ["charts-base"]});
