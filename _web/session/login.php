<?php
session_start();
$username = $_GET['username'];
$password = $_GET['password'];

// already login
if($_SESSION['login'])
{
	echo json_encode(array("status"=>'already login'));
}
// notyet login
else
{
	// login success
	if($username == "admin" && $password == "123456")
	{
		$_SESSION['login'] = true;
		echo json_encode(array("status"=>'login success'));
	}
	// login failed
	else
	{
		echo json_encode(array("status"=>'login failed'));
	}
}