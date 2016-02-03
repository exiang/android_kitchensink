<?php
session_start();
// already login
if($_SESSION['login'])
{
	session_unset();
	session_destroy();
	if(isset($_SESSION['login']))
	{
		echo json_encode(array("status"=>'logout failed'));
	}
	else
	{
		echo json_encode(array("status"=>'logout success'));
	}
}
// already logout
else
{
	echo json_encode(array("status"=>'already logout'));
}