<!DOCTYPE html>
<html>
  <head>
  		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta content="IE=edge" http-equiv="X-UA-Compatible">
		<meta content="width=device-width, initial-scale=1" name="viewport">
		<meta content="templateApp" name="description">
		<meta content="templateApp" name="keywords">
		<meta content="Ing. Alejandro Daniel Curci (acurci@gmail.com.ar) " name="author">
    <meta http-equiv="Content-Security-Policy" content="default-src 'self' http:">
    <meta charset="utf-8" />
    <title>templateApp</title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
    <meta name="apple-mobile-web-app-status-bar-style" content="yes" />
    <link rel="stylesheet" href="../resources/ng/css/app.min.css" />
    <link rel="stylesheet" href="../resources/ng/css/responsive.min.css" />

    <script src="../resources/ng/js/app.min.js"></script>
  </head>
  <body ng-app="Consejonews" ng-controller="MainController">

    <!-- Sidebars -->
    <div ng-include="'sidebar.html'"
            ui-track-as-search-param='true'
            class="sidebar sidebar-left"></div>

    <div class="app">

      <!-- Navbars -->

      <div class="navbar navbar-app navbar-absolute-top">
        <div class="navbar-brand navbar-brand-center" ui-yield-to="title">
        </div>
        <div class="btn-group pull-left">
          <div ui-toggle="uiSidebarLeft" class="btn sidebar-toggle">
            <i class="fa fa-bars"></i>
          </div>
        </div>
        <div class="btn-group pull-right" ui-yield-to="navbarAction">
          <div ui-toggle="uiSidebarRight" class="btn">
          </div>
        </div>
      </div>

      <div class="navbar navbar-app navbar-absolute-bottom">
        <div class="btn-group justified">
        </div>
      </div>

      <!-- App Body -->
      <div class="app-body">
        <div class="app-content">
          <ng-view></ng-view>
        </div>
      </div>

    </div><!-- ~ .app -->

    <div ui-yield-to="modals"></div>
  </body>
</html>
