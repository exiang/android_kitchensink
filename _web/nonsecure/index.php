<?php

echo json_encode(array('status'=>'success', 'message'=>'nonsecure with https: '.$_SERVER['HTTPS']));

?>