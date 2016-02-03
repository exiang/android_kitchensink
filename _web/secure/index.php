<?php

echo json_encode(array('status'=>'success', 'message'=>'secure with https: '.$_SERVER['HTTPS']));

?>