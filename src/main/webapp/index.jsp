<html>
<head>

<link rel="stylesheet"	href="bower_components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="css/note.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">



<script type="text/javascript"	src="bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript"	src="bower_components/angular/angular.min.js"></script>
<script type="text/javascript" src="bower_components/angular-sanitize/angular-sanitize.js"></script>
<script type="text/javascript"	src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.5.0/ui-bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.5.0/ui-bootstrap-tpls.min.js"></script>

</head>

<body ng-app="todo">

	<div>

		<ui-view> </ui-view>

	</div>
</body>

<script type="text/javascript" src="js/app.js"></script>
<script src="js/controller/loginController.js" charset="utf-8"></script>
<script src="js/service/loginService.js" charset="utf-8"></script>
<script src="js/controller/noteController.js"></script>
<script src="js/service/noteService.js"></script>
<script src="js/controller/regController.js" charset="utf-8"></script>
<script src="js/service/regService.js"></script>

</html>
