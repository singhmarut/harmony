/* YUI 3.8.1 (build 5795) Copyright 2013 Yahoo! Inc. http://yuilibrary.com/license/ */
YUI.add('tabview-plugin', function (Y, NAME) {

function TabviewPlugin() {
    TabviewPlugin.superclass.constructor.apply(this, arguments);
}

TabviewPlugin.NAME = 'tabviewPlugin';
TabviewPlugin.NS = 'tabs';

Y.extend(TabviewPlugin, Y.TabviewBase);

Y.namespace('Plugin');
Y.Plugin.Tabview = TabviewPlugin;


}, '3.8.1', {"requires": ["tabview-base"]});
