<?php
session_start();

$_SESSION['counter']++;
if($_SESSION['login'])
{
	echo json_encode(array("status"=>'already login', 'counter'=>$_SESSION['counter']));
}
else
{
	echo json_encode(array("status"=>'notyet login'));
}